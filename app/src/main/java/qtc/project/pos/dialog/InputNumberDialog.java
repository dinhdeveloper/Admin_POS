package qtc.project.pos.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import b.laixuantam.myaarlibrary.helper.NumericFormater;
import b.laixuantam.myaarlibrary.widgets.dialog.AppDialog;
import qtc.project.pos.R;

import static b.laixuantam.myaarlibrary.helper.NumericFormater.parseCurrency;
import static b.laixuantam.myaarlibrary.helper.NumericFormater.parseNumber;

public class InputNumberDialog extends AppDialog<InputNumberDialog.DialogChoseNumberListener> implements OnClickListener {
    private static final String EXTRA_NOTE = "EXTRA_NOTE";
    private static final String EXTRA_DEFAULT_VALUE = "EXTRA_DEFAULT_VALUE";
    private static final String EXTRA_ACCEPT_ZERO = "EXTRA_ACCEPT_ZERO";

    private final StringBuilder sValue = new StringBuilder();
    private View buttonRemove;
    private TextView textValueNumber;
    private boolean firstDefaultValue = false;
    private double defaultValue = 0;
    private boolean acceptZero = false;

    public InputNumberDialog() {
        setCancelable(true);
    }

    public static InputNumberDialog newInstance(DialogChoseNumberListener listener, String note, double defaultValue, boolean acceptZero) {
        InputNumberDialog dialog = new InputNumberDialog();
        dialog.setListener(listener);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NOTE, note);
        bundle.putDouble(EXTRA_DEFAULT_VALUE, defaultValue);
        bundle.putBoolean(EXTRA_ACCEPT_ZERO, acceptZero);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected boolean isListenerOptional() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(getResources().getDimensionPixelSize(R.dimen.keypad_dialog_width), LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String note = "";
        firstDefaultValue = true;
        defaultValue = 0;

        if (bundle != null) {
            defaultValue = bundle.getDouble(EXTRA_DEFAULT_VALUE, 0);
            note = bundle.getString(EXTRA_NOTE, "");
            acceptZero = bundle.getBoolean(EXTRA_ACCEPT_ZERO, false);
        }

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View dialogLayout = inflater.inflate(R.layout.dialog_choose_number, null);
        GridLayout keypadView = dialogLayout.findViewById(R.id.grid_keypad);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout);

        String num[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "000", "0", "."};
        for (String sValue : num) {
            @SuppressLint("InflateParams") LinearLayout keypadLayout = (LinearLayout) inflater.inflate(R.layout.view_button_keypad, null);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED);
            lp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
            keypadLayout.setLayoutParams(lp);

            TextView textValue = keypadLayout.findViewById(R.id.text_value);
            textValue.setText(sValue);

            View button = keypadLayout.findViewById(R.id.button);
            button.setTag(sValue);
            button.setOnClickListener(this);

            keypadView.addView(keypadLayout);
        }

        buttonRemove = dialogLayout.findViewById(R.id.button_remove);
        textValueNumber = dialogLayout.findViewById(R.id.text_value);
        sValue.append(NumericFormater.formatNumber(defaultValue));
        textValueNumber.setText(NumericFormater.formatCurrency(defaultValue));
        textValueNumber.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.gray_light_opacity));

        buttonRemove.setOnClickListener(v -> {
            if (sValue.length() > 0) {
                boolean parseSuccess = false;
                double number = 0;
                while (!parseSuccess && sValue.length() > 0) {
                    sValue.deleteCharAt(sValue.length() - 1);
                    try {
                        number = parseNumber(sValue.toString());
                        parseSuccess = '.' != (sValue.charAt(sValue.length() - 1));
                    } catch (Exception e) {
                        parseSuccess = false;
                    }
                }
                String s[] = sValue.toString().split("\\.");
                boolean hasSign = s.length == 2;
                if (sValue.length() > 0) {
                    if (hasSign) {
                        double number1 = 0;
                        try {
                            number1 = parseNumber(s[0]);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        textValueNumber.setText(NumericFormater.formatCurrency(number1) + "." + s[1]);
                    } else {
                        textValueNumber.setText(NumericFormater.formatCurrency(number));
                    }
                } else {
                    String sDefault = NumericFormater.formatNumber(defaultValue);
                    sValue.append(sDefault);
                    textValueNumber.setText(sDefault);
                    firstDefaultValue = true;
                    buttonRemove.setVisibility(View.INVISIBLE);
                    textValueNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                }
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            double value = 0;
            try {
                value = parseNumber(sValue.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            listener.onInputNumberReturn(value, "");
            dialog.dismiss();
        });

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        String value = (String) view.getTag();
        // kiểm tra số lượng sau thập phân
        String s[] = sValue.toString().split("\\.");
        boolean hasSign = s.length == 2;
        if ((hasSign && (s[1].length() >= 3 || (s[1].length() == 2 && "0".equals(value)))) || (sValue
                .toString().contains(".") && ".".equals(value))) return;
        // Kiểm tra value = 0
        if ((parseCurrency(sValue.toString()) > 0 || ".".equals(value) || sValue.toString().contains(".")) && !firstDefaultValue) {
            sValue.append(value);
        } else {
            sValue.setLength(0);
            sValue.append(value);
        }

        // kiểm tra số lượng sau thập phân 1 lần nữa
        s = sValue.toString().split("\\.");
        hasSign = sValue.toString().contains(".");

        double number = 0;
        try {
            number = parseNumber(sValue.toString());
            if (hasSign) {
                textValueNumber.setText(NumericFormater.formatCurrency(parseNumber(s[0])) + "." + (s.length == 2 ? s[1] : ""));
            } else {
                textValueNumber.setText(NumericFormater.formatCurrency(number));
            }
            buttonRemove.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            sValue.deleteCharAt(sValue.length() - 1);
        }
        textValueNumber.setTextColor(Color.WHITE);
        firstDefaultValue = false;

        if (number == 0 && !acceptZero) {
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(false);
        } else {
            ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(true);
        }
    }

    public interface DialogChoseNumberListener {
        void onInputNumberReturn(double quantity, String note);
    }


}
