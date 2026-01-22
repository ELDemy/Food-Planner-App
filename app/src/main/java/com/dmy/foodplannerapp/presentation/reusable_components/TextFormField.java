package com.dmy.foodplannerapp.presentation.reusable_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmy.foodplannerapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextFormField extends LinearLayout {

    public TextFormField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

        // Inflate your existing layout file
        LayoutInflater.from(context).inflate(R.layout.text_form_field_layout, this, true);

        TextView labelView = findViewById(R.id.label);
        TextInputEditText editText = findViewById(R.id.editText);
        TextInputLayout inputLayout = findViewById(R.id.textInputLayout);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextFormField);

            String labelText = a.getString(R.styleable.TextFormField_labelText);
            String hintText = a.getString(R.styleable.TextFormField_hintText);
            int iconRes = a.getResourceId(R.styleable.TextFormField_startIcon, 0);
            boolean isPassword = a.getBoolean(R.styleable.TextFormField_isPassword, false);

            if (labelText != null) labelView.setText(labelText);
            if (hintText != null) editText.setHint(hintText);
            if (iconRes != 0) {
                inputLayout.setStartIconDrawable(iconRes);
            } else {
                inputLayout.setStartIconDrawable(null); // Optional: remove default icon if none provided
            }

            if (isPassword) {
                inputLayout.setEndIconMode(com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

            a.recycle();
        }
    }

    public String getText() {
        clearError();
        TextInputEditText editText = findViewById(R.id.editText);
        return editText.getText().toString();
    }

    public void setError(String err) {
        TextInputLayout editText = findViewById(R.id.textInputLayout);
        editText.setError(err);
    }

    private void clearError() {
        TextInputLayout editText = findViewById(R.id.textInputLayout);
        editText.setError(null);
    }


}
