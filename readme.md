<div align="center">
    <!-- Bintray -->
    <a href="https://bintray.com/mahmoudalyudeen/maven/com.mahmoudalyudeen.validatableform/_latestVersion">
        <img src="https://api.bintray.com/packages/mahmoudalyudeen/maven/com.mahmoudalyudeen.validatableform/images/download.svg" />
    </a>    
    <!--  API Level  -->
    <a href="https://android-arsenal.com/api?level=14">
        <img src="https://img.shields.io/badge/API-14%2B-orange.svg?style=flat"/>
    </a>
    <!--  PRs  -->
    <a href="https://github.com/MahmoudAlyuDeen/ValidatableForm/fork">
        <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg"/>
    </a>
</div>

# Read the story

<a href="https://medium.com/@MahmoudAlyuDeen/validate-forms-on-android-without-losing-your-mind-fcf3804ca57?sk=fc47dc0c7a3423afea355409a7264f2e">
Validate Forms on Android Without Losing Your Mind
</a>

# See it in action

#### Sample APK

[app-debug.apk](app-debug.apk)

#### Demo video

<https://www.youtube.com/watch?v=SMSCVwfZsc4>

# Add to your project

#### Kotlin

```kotlin
implementation("com.github.mahmoudalyudeen:validatableform:1.0.1")
```

#### Groovy
```
implementation 'com.github.mahmoudalyudeen:validatableform:1.0.1'
```

# Quick usage

## Field
To Define an input, use a `Field`

```kotlin
val username = Field<String>(initialValue = "", validator = { it.isNotBlank() })
```

Or you can skip the explicit type and argument names

```kotlin
val username = Field("") { it.isNotBlank() }
```

A field can have any type. It doesn't have to be a String.

To set the value of the field

```kotlin
username.value = "awesome user"
```

Or in a ViewModel, you can do something like this

```kotlin
fun onPasswordChanged(password: CharSequence) {
    _loginForm.password.value = password.toString()
}
```

To get the value of the field

```kotlin
val usernameStr: String = username.value
```

Or by databinding

```
<EditText
    ..
    android:text="@{loginViewModel.loginForm.username.toString()}" />
```
Don't forget adding `toString()`

To evaluate the validity of the field

```kotlin
val isUsernameValid: Boolean = username.valid
```

## Forms

To define a form, create a class extending ValidatableForm.

And override `fields` and pass a list of all the fields you want to validate.

```kotlin
class LoginForm : ValidatableForm() {
   val username = Field(initialValue = "") { it.isNotBlank() }
   val password = Field(initialValue = "") { it.length >= 6 }
   val agreeTos = Field(initialValue = false) { it }
   
   override val fields = listOf(username, password, agreeTos)
}
```

Then create an instance of that class in your ViewModel with a backing property to allow access and prevent modification
```kotlin
private val _loginForm = LoginForm()
val loginForm: LoginForm
    get() = _loginForm
```

To evaluate the validity of entire form

```kotlin
val isLoginValid: Boolean = loginForm.valid
```

Or by databinding
```
<Button
    ..
    android:enabled="@{loginViewModel.loginForm.valid}" />
```


## Validator

A field is created with a `validator`; a lambda function that takes the value of the field as its input and returns `true` when it's valid.

### Validator examples

#### String

`validator = { it.isNotBlank() }`

`validator = { it.length >= 6 }`

#### Boolean

`validator = { it }` or `validator = { !it }`

#### Int

`validator = { it > 5 }`

#### Custom type

```kotlin
data class Birthday(
    val day: Int? = null,
    val month: Int? = null,
    val year: Int? = null
) {
    val valid
        get() = day != null && month != null && year != null
}

val birthday = Field(Birthday()) { it.valid }
```

#### Referencing other fields

```kotlin
val password = Field("") { it.length >= 6 }

val passwordConfirmation = Field("") { it == password.value }
```

#### Field without validation (optional)

```kotlin
 val optionalField = Field("") { true } 
```

#### Usage with <a href="https://github.com/wajahatkarim3/EasyValidation/tree/master/easyvalidation-core">EasyValidation</a>

EasyValidation is a validation library with very helpful extension functions to check for valid emails, phone numbers and passwords.

```kotlin
val email = Field("") { it.validEmail() }
```

## LiveData and Transformations

If you want to use `Transformations.map()`/`.switchMap()`

```kotlin
val loginText = map(_loginForm.username.valueLive) { "Welcome back $it" }
```

## Databinding + TextInputLayout

Like the example app, you can create a binding adapter to set the helper text or error for a TextInputLayout

#### HelperText Example

[LoginBindingAdapters](https://github.com/MahmoudAlyuDeen/ValidatableForm/blob/master/app/src/main/java/com/mahmoudalyudeen/sample/ui/login/LoginBindingAdapters.kt)
