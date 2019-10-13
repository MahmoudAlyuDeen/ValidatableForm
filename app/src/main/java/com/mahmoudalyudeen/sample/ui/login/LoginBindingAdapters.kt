package com.mahmoudalyudeen.sample.ui.login

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.mahmoudalyudeen.validatableform.ValidatableForm.Field

/**
 * Example binding adapter to bind [Field] to [TextInputLayout] to show validation state.
 *
 * Ex:
 *
 * <com.google.android.material.textfield.TextInputLayout
 *     ..
 *     app:field="@{loginViewModel.loginForm.username}"
 *     app:fieldHelper="@{@string/helper_login_username}">
 *
 *     <EditText .. />
 *
 * </com.google.android.material.textfield.TextInputLayout>
 */
@BindingAdapter("field", "fieldHelper")
fun bindFieldHelperText(inputLayout: TextInputLayout, field: Field<*>, fieldHelper: String?) {
    val showHelper = !field.valid && field.value.toString().isNotEmpty()
    inputLayout.isHelperTextEnabled = showHelper
    if (showHelper) {
        inputLayout.helperText = fieldHelper
    }
}
