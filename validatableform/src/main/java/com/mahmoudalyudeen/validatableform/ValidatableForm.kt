package com.mahmoudalyudeen.validatableform

import androidx.databinding.BaseObservable

/**
 * Represents a form filled by user input that has a live [valid] state.
 * Extends [BaseObservable] to allow databinding [fields] and [valid].
 *
 * Ex:
 *
 * class LoginForm : ValidatableForm() {
 *
 *     val username = Field(initialValue = "", validator = { it.minLength(2) && it.notContains(" ") })
 *     val password = Field(initialValue = "", validator = { it.minLength(6) && it.atleastOneNumber() })
 *     val agreeTos = Field(initialValue = false, validator = { it })
 *
 *     override val fields = listOf(username, password, agreeTos)
 * }
 */
abstract class ValidatableForm : BaseObservable() {

    /**
     * The list of [Field] objects that make up the form.
     * When all fields are valid: [Field.valid] = true, the form is valid: [valid] = true.
     */
    abstract val fields: List<Field<*>>

    private fun validateFields() {
        synchronized(this) {
            _valid = fields.all { it.valid }
        }
        notifyChange()
    }

    /** The validity of the entire form, and a private backing property to prevent modification */
    private var _valid: Boolean = false
    val valid: Boolean
        get() = _valid

    /**
     * Represents a single user input with its own live [valid] state.
     * A field can have any type: [T], as long as it can be validated.
     *
     * Examples:
     *
     * val username = Field("", { it.isNotEmpty()})
     * val password = Field("", { it.length > 5 })
     * val tosAgree = Field(false, { it })
     *
     * @param initialValue used to initialize [value], and also to infer the field's type: [T].
     * @param validator used to initialize and update [valid] when [value] is changed.
     */
    inner class Field<T>(initialValue: T, validator: (T) -> Boolean) {

        private val _validator = validator

        /**
         * The value of the field, with a private backing property to control modification.
         *
         * When value is updated, both the validity of the field [valid] and the
         * form [ValidatableForm.valid] are reevaluated in a thread safe manner.
         */
        private var _value: T = initialValue
        var value: T
            get() = _value
            set(value) {
                if (value == _value) return
                synchronized(this) {
                    _value = value
                    _valid = _validator(value)
                }
                this@ValidatableForm.validateFields()
            }

        /** The validity of the field, and a private backing property to prevent modification */
        private var _valid: Boolean = _validator(initialValue)
        val valid: Boolean
            get() = _valid

        override fun toString() = _value.toString()
    }
}
