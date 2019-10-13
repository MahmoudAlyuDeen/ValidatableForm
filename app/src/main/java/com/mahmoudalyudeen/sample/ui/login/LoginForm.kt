package com.mahmoudalyudeen.sample.ui.login

import com.mahmoudalyudeen.validatableform.ValidatableForm
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneNumber
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.wajahatkarim3.easyvalidation.core.view_ktx.noSpecialCharacters

class LoginForm : ValidatableForm() {

    val username = Field(initialValue = "", validator = { it.minLength(4) && it.noSpecialCharacters() })
    val password = Field(initialValue = "", validator = { it.atleastOneLowerCase() && it.atleastOneNumber() })
    val agreeTos = Field(initialValue = false, validator = { it })

    override val fields = listOf(username, password, agreeTos)
}
