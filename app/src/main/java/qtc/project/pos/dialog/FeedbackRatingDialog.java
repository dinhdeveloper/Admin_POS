package qtc.project.pos.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import b.laixuantam.myaarlibrary.helper.AppUtils;
import qtc.project.pos.R;

public class FeedbackRatingDialog extends AppCompatDialog implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private final Context context;
    private final Builder builder;
    private TextView tvTitle, tvNegative, tvNever, tvPositive, tvFeedbackTitle;
    private RatingBar ratingBar;
    private ImageView ivIcon;
    private EditText etFeedback;
    private View layoutFeedback, layoutRootView, layoutEvaluate;

    private FeedbackRatingDialog(Context context, Builder builder) {
        super(context);
        this.context = context;
        this.builder = builder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_rating);

        layoutRootView = findViewById(R.id.layoutRootView);

        assert layoutRootView != null;
        layoutRootView.setOnTouchListener((v, event) -> {
            AppUtils.hideKeyBoard(layoutRootView);
            return false;
        });

        layoutFeedback = findViewById(R.id.layout_feedback);
        layoutEvaluate = findViewById(R.id.layoutEvaluate);

        ivIcon = findViewById(R.id.dialog_rating_icon);

        tvTitle = findViewById(R.id.dialog_rating_title);

        tvFeedbackTitle = findViewById(R.id.dialog_rating_feedback_title);
        etFeedback = findViewById(R.id.dialog_rating_edt_feedback);

        ratingBar = findViewById(R.id.dialog_rating_rating_bar);

        tvPositive = findViewById(R.id.dialog_rating_button_positive);
        tvNegative = findViewById(R.id.dialog_rating_button_negative);
        tvNever = findViewById(R.id.dialog_rating_button_nerver);

        setCancelable(builder.cancelable);

        init();
    }

    private void init() {

        if (builder.getTypeDialog() == Builder.Type.FEEDBACK) {
            layoutEvaluate.setVisibility(View.GONE);
        } else if (builder.getTypeDialog() == Builder.Type.EVALUATE) {
            layoutFeedback.setVisibility(View.GONE);
        } else {
            layoutEvaluate.setVisibility(View.VISIBLE);
            layoutFeedback.setVisibility(View.VISIBLE);
        }

        if (builder.drawableIcon != 0) {
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(builder.drawableIcon);
        } else {
            ivIcon.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(builder.title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(builder.title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(builder.feedback_title)) {
            tvFeedbackTitle.setVisibility(View.VISIBLE);
            tvFeedbackTitle.setText(builder.feedback_title);
        } else {
            tvFeedbackTitle.setVisibility(View.GONE);
        }

        etFeedback.setHint(builder.feedbackFormHint);

        tvPositive.setText(builder.positiveText);
        tvNegative.setText(builder.negativeText);
        tvNever.setText(builder.neverText);

        if (builder.visibleButtonNever) {
            tvNever.setVisibility(View.VISIBLE);
        } else {
            tvNever.setVisibility(View.GONE);
        }

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int color = typedValue.data;

        tvTitle.setTextColor(builder.titleTextColor != 0 ? ContextCompat.getColor(context, builder.titleTextColor) : ContextCompat.getColor(context, R.color.black));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, builder.ratingBarColor), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(ContextCompat.getColor(context, builder.ratingBarColor), PorterDuff.Mode.SRC_ATOP);
            int ratingBarBackgroundColor = builder.ratingBarBackgroundColor != 0 ? builder.ratingBarBackgroundColor : R.color.grey_200;
            stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, ratingBarBackgroundColor), PorterDuff.Mode.SRC_ATOP);
        } else {
            Drawable stars = ratingBar.getProgressDrawable();
            DrawableCompat.setTint(stars, ContextCompat.getColor(context, builder.ratingBarColor));
        }

        tvFeedbackTitle.setTextColor(builder.feedBackTextTitleColor != 0 ? ContextCompat.getColor(context, builder.feedBackTextTitleColor) : ContextCompat.getColor(context, R.color.black));
        etFeedback.setTextColor(builder.feedBackTextColor != 0 ? ContextCompat.getColor(context, builder.feedBackTextColor) : ContextCompat.getColor(context, R.color.black));

        tvPositive.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : ContextCompat.getColor(context, R.color.black));
        tvNegative.setTextColor(builder.negativeTextColor != 0 ? ContextCompat.getColor(context, builder.negativeTextColor) : color);
        tvNever.setTextColor(builder.neverTextColor != 0 ? ContextCompat.getColor(context, builder.neverTextColor) : ContextCompat.getColor(context, R.color.black));

        tvPositive.setBackgroundColor(builder.positiveBackgroundColor != 0 ? ContextCompat.getColor(context, builder.positiveBackgroundColor) : ContextCompat.getColor(context, R.color.white));
        tvNegative.setBackgroundColor(builder.negativeBackgroundColor != 0 ? ContextCompat.getColor(context, builder.negativeBackgroundColor) : ContextCompat.getColor(context, R.color.white));
        tvNever.setBackgroundColor(builder.neverBackgroundColor != 0 ? ContextCompat.getColor(context, builder.neverBackgroundColor) : ContextCompat.getColor(context, R.color.white));


        ratingBar.setOnRatingBarChangeListener(this);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnClickListener(this);
        tvNever.setOnClickListener(this);

        enablePositiveButton(false);

        etFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && !TextUtils.isEmpty(editable.toString().trim())) {
                    enablePositiveButton(true);
                } else {
                    if (ratingBar.getRating() == 0) {
                        enablePositiveButton(false);
                    }
                }
            }
        });


    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        if (layoutRootView != null)
            AppUtils.hideKeyBoard(layoutRootView);
        if (ratingBar.getRating() > 0) {
            enablePositiveButton(true);
        } else {
            if (TextUtils.isEmpty(etFeedback.getText())) {
                enablePositiveButton(false);
            }
        }
    }

    private void enablePositiveButton(boolean enable) {
        if (enable) {
            tvPositive.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : ContextCompat.getColor(context, R.color.black));
            tvPositive.setBackgroundColor(builder.positiveBackgroundColor != 0 ? ContextCompat.getColor(context, builder.positiveBackgroundColor) : ContextCompat.getColor(context, R.color.white));

        } else {
            tvPositive.setTextColor(builder.positiveBackgroundColor != 0 ? ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.gray_light_opacity));
            tvPositive.setBackgroundColor(builder.positiveBackgroundColor != 0 ? ContextCompat.getColor(context, R.color.gray_light_opacity) : ContextCompat.getColor(context, R.color.white));
        }

        tvPositive.setEnabled(enable);

    }

    @Override
    public void onClick(View view) {

        AppUtils.hideKeyBoard(layoutRootView);

        if (view.getId() == R.id.dialog_rating_button_positive) {
            if (builder.buttonPositiveListener != null) {
                String feedbackText = "";
                if (!TextUtils.isEmpty(etFeedback.getText())) {
                    feedbackText = etFeedback.getText().toString().trim();
                }
                builder.buttonPositiveListener.onClickButtonPositive((int) ratingBar.getRating(), feedbackText);
            }
            dismiss();

        } else if (view.getId() == R.id.dialog_rating_button_negative) {

            if (builder.buttonNegativeListener != null) {
                builder.buttonNegativeListener.onClickButtonNegative();
            }
            dismiss();

        } else if (view.getId() == R.id.dialog_rating_button_nerver) {

            if (builder.buttonNeverListener != null) {
                builder.buttonNeverListener.onClickButtonNever();
            }
            dismiss();
        }

    }

    private void openPlaystore(Context context) {
        final Uri marketUri = Uri.parse(builder.playstoreUrl);
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Couldn't find PlayStore on this device", Toast.LENGTH_SHORT).show();
        }
    }

    public static class Builder {

        private final Context context;
        private String title, positiveText, negativeText, neverText, playstoreUrl;
        private String feedback_title, feedbackFormHint;
        private int positiveTextColor, negativeTextColor, neverTextColor, titleTextColor, ratingBarColor, ratingBarBackgroundColor, feedBackTextColor, feedBackTextTitleColor;
        private int positiveBackgroundColor, negativeBackgroundColor, neverBackgroundColor;

        private ButtonPositiveListener buttonPositiveListener;
        private ButtonNegativeListener buttonNegativeListener;
        private ButtonNeverListener buttonNeverListener;

        private int drawableIcon;
        private boolean visibleButtonNever = false;
        private boolean cancelable = true;

        private Type typeDialog = Type.BOTH;

        public enum Type {
            FEEDBACK,
            EVALUATE,
            BOTH
        }

        Type getTypeDialog() {
            return typeDialog;
        }

        public Builder setTypeDialog(Type typeDialog) {
            this.typeDialog = typeDialog;
            return this;
        }

        public interface ButtonPositiveListener {
            void onClickButtonPositive(int rating, String feedback);
        }

        public interface ButtonNegativeListener {
            void onClickButtonNegative();
        }

        public interface ButtonNeverListener {
            void onClickButtonNever();
        }

        public Builder(Context context) {
            this.context = context;

            initText();
        }

        private void initText() {
            drawableIcon = 0;
            title = "";

            feedback_title = "";
            feedbackFormHint = "Viết phản hồi";

            positiveText = "Đồng ý";
            negativeText = "Hủy bỏ";
            neverText = "Never";
            playstoreUrl = "";

            ratingBarColor = R.color.yellow;

        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(@DrawableRes int drawable) {
            this.drawableIcon = drawable;
            return this;
        }

        public Builder positiveButtonText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder negativeButtonText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder neverButtonText(String neverText) {
            this.neverText = neverText;
            return this;
        }

        public Builder titleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder positiveButtonTextColor(int positiveTextColor) {
            this.positiveTextColor = positiveTextColor;
            return this;
        }

        public Builder negativeButtonTextColor(int negativeTextColor) {
            this.negativeTextColor = negativeTextColor;
            return this;
        }

        public Builder neverButtonTextColor(int neverTextColor) {
            this.neverTextColor = neverTextColor;
            return this;
        }

        public Builder positiveButtonBackgroundColor(int positiveBackgroundColor) {
            this.positiveBackgroundColor = positiveBackgroundColor;
            return this;
        }

        public Builder negativeButtonBackgroundColor(int negativeBackgroundColor) {
            this.negativeBackgroundColor = negativeBackgroundColor;
            return this;
        }

        public Builder neverButtonBackgroundColor(int neverBackgroundColor) {
            this.neverBackgroundColor = neverBackgroundColor;
            return this;
        }

        public Builder feedbackTextTitle(String feedbackTitle) {
            this.feedback_title = feedbackTitle;
            return this;
        }

        public Builder feedbackEditTextHint(String feedbackHint) {
            this.feedbackFormHint = feedbackHint;
            return this;
        }

        public Builder ratingBarColor(int ratingBarColor) {
            this.ratingBarColor = ratingBarColor;
            return this;
        }

        public Builder ratingBarBackgroundColor(int ratingBarBackgroundColor) {
            this.ratingBarBackgroundColor = ratingBarBackgroundColor;
            return this;
        }

        public Builder feedbackTextColor(int feedBackTextColor) {
            this.feedBackTextColor = feedBackTextColor;
            return this;
        }

        public Builder feedbackTextTitleColor(int feedBackTextTitleColor) {
            this.feedBackTextTitleColor = feedBackTextTitleColor;
            return this;
        }

        public Builder playstoreUrl(String playstoreUrl) {
            this.playstoreUrl = playstoreUrl;
            return this;
        }

        public Builder setVisibleNeverButton(boolean visible) {
            this.visibleButtonNever = visible;
            return this;
        }

        public Builder setPositiveButtonListener(ButtonPositiveListener buttonPositiveListener) {
            this.buttonPositiveListener = buttonPositiveListener;
            return this;
        }

        public Builder setNegativeButtonListener(ButtonNegativeListener negativeButtonListener) {
            this.buttonNegativeListener = negativeButtonListener;
            return this;
        }

        public Builder setNeverButtonListener(ButtonNeverListener neverButtonListener) {
            this.buttonNeverListener = neverButtonListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public FeedbackRatingDialog build() {
            return new FeedbackRatingDialog(context, this);
        }
    }

    /*
     private void showDialog() {
     final FeedbackRatingDialog ratingDialog = new FeedbackRatingDialog.Builder(DemoSingleActivity.this)
     .setIcon(R.mipmap.ic_launcher)
     .title("App chất không men?")
     .setVisibleLayoutFeedback(true)
     .setCancelable(false)
     .positiveButtonBackgroundColor(R.color.green)
     .setPositiveButtonListener(new FeedbackRatingDialog.Builder.ButtonPositiveListener() {
    @Override public void onClickButtonPositive(int rating, String feedback) {

    Toast.makeText(DemoSingleActivity.this, "Feedback: " + feedback + " | rating: " + rating, Toast.LENGTH_SHORT).show();
    }
    })
     .setNegativeButtonListener(new FeedbackRatingDialog.Builder.ButtonNegativeListener() {
    @Override public void onClickButtonNegative() {
    Toast.makeText(DemoSingleActivity.this, "ClickNegativeButton", Toast.LENGTH_SHORT).show();
    }
    })
     .setNeverButtonListener(new FeedbackRatingDialog.Builder.ButtonNeverListener() {
    @Override public void onClickButtonNever() {
    Toast.makeText(DemoSingleActivity.this, "ClickNeverButton", Toast.LENGTH_SHORT).show();
    }
    })
     .build();

     ratingDialog.show();
     }
     */
}
