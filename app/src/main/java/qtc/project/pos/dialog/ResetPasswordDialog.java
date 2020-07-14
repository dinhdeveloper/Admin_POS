package qtc.project.pos.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

import qtc.project.pos.R;

public class ResetPasswordDialog extends AppCompatDialog {

    private final Context context;
    private EditText edtNewPassword, edtNewPasswordConfirm;
    private View btnSubmitResetPassword, btnCancelResetPassword;
    private ResetPasswordDialogListener listener;
    private boolean isVisiblePassword = false;
    private boolean isVisibleConfirmPassword = false;

    public ResetPasswordDialog(Context context, ResetPasswordDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        setCancelable(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.layout_reset_password);

        edtNewPassword = findViewById(R.id.edtEmployeeNewPasswordLayoutReset);
        edtNewPasswordConfirm = findViewById(R.id.edtEmployeeConfirmNewPasswordLayoutReset);
        btnSubmitResetPassword = findViewById(R.id.btnSubmitResetPassword);
        btnCancelResetPassword = findViewById(R.id.btnCancelResetPassword);

        ImageView btnVisiblePassword = findViewById(R.id.btnVisibleNewPasswordLayoutReset);
        ImageView btnVisibleConfirmNewPasswordLayoutReset = findViewById(R.id.btnVisibleConfirmNewPasswordLayoutReset);

        btnVisiblePassword.setOnClickListener(v -> {
            if (!isVisiblePassword) {
                isVisiblePassword = true;
                edtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (!"".contentEquals(edtNewPassword.getText()))
                    edtNewPassword.setSelection(edtNewPassword.getText().length());
                btnVisiblePassword.setBackgroundResource(R.drawable.eyes_selected_64);
            } else {
                isVisiblePassword = false;
                edtNewPassword.setInputType(129);
                if (!"".contentEquals(edtNewPassword.getText()))
                    edtNewPassword.setSelection(edtNewPassword.getText().length());
                btnVisiblePassword.setBackgroundResource(R.drawable.ic_hide_eys);
            }
        });

        btnVisibleConfirmNewPasswordLayoutReset.setOnClickListener(v -> {
            if (!isVisibleConfirmPassword) {
                isVisibleConfirmPassword = true;
                edtNewPasswordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (!"".contentEquals(edtNewPasswordConfirm.getText()))
                    edtNewPasswordConfirm.setSelection(edtNewPasswordConfirm.getText().length());
                btnVisibleConfirmNewPasswordLayoutReset.setBackgroundResource(R.drawable.eyes_selected_64);
            } else {
                isVisibleConfirmPassword = false;
                edtNewPasswordConfirm.setInputType(129);
                if (!"".contentEquals(edtNewPasswordConfirm.getText()))
                    edtNewPasswordConfirm.setSelection(edtNewPasswordConfirm.getText().length());
                btnVisibleConfirmNewPasswordLayoutReset.setBackgroundResource(R.drawable.ic_hide_eys);
            }
        });

        btnSubmitResetPassword.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtNewPassword.getText())) {
                edtNewPassword.setError("Nhập mật khẩu mới");
                return;
            }

            if (TextUtils.isEmpty(edtNewPasswordConfirm.getText())) {
                edtNewPasswordConfirm.setError("Xác nhận mật khẩu mới");
                return;
            }
            String new_password = edtNewPassword.getText().toString().trim();
            String new_password_confirm = edtNewPasswordConfirm.getText().toString().trim();

            if (new_password.equalsIgnoreCase(new_password_confirm)) {
                if (listener != null) {
                    dismiss();
                    listener.onSubmitResetPassword(new_password);
                }
            } else {
                Toast.makeText(context, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelResetPassword.setOnClickListener(view -> {
            dismiss();
        });
    }

    public interface ResetPasswordDialogListener {
        void onSubmitResetPassword(String new_password);
    }

}
