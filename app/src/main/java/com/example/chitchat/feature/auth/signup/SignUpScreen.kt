package com.example.chitchat.feature.auth.signup

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chitchat.MainActivity
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField

@Composable
fun SignUpScreen(
    onAlternativeMethodPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val displayName by viewModel::displayName
    val isDisplayNameError by viewModel::isDisplayNameError
    val isDisplayNameEdited by viewModel::isDisplayNameEdited
    val displayNameSupportingText by viewModel::displayNameSupportingText

    val email by viewModel::email
    val isEmailError by viewModel::isEmailError
    val isEmailEdited by viewModel::isEmailEdited
    val emailSupportingText by viewModel::emailSupportingText

    val password by viewModel::password
    val isPasswordError by viewModel::isPasswordError
    val isPasswordEdited by viewModel::isPasswordEdited
    val passwordSupportingText by viewModel::passwordSupportingText

    val confirmedPassword by viewModel::confirmedPassword
    val isConfirmedPasswordError by viewModel::isConfirmedPasswordError
    val isConfirmedPasswordEdited by viewModel::isConfirmedPasswordEdited
    val confirmedPasswordSupportingText by viewModel::confirmedPasswordSupportingText

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }

    val activity = LocalActivity.current as MainActivity
    val auth = activity.auth
    val context = LocalContext.current

    AuthScreenWrapper(
        nextButtonTitle = "Sign Up",
        alternativeMethodTitle = "Already have an account? Sign In",
        onNextPress = {
            val isAllGood = viewModel.validateTextFields()
            if (isAllGood) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) { task ->
                        val msg = if (task.isSuccessful) "Sign up success." else "Sign up failed."
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
            }
        },
        onAlternativeMethodPress = onAlternativeMethodPress
    ) {
        AuthTextField(
            value = displayName,
            onValueChange = {
                viewModel.displayName = it
                viewModel.isDisplayNameEdited = true
            },
            isError = isDisplayNameError,
            supportingText = if (isDisplayNameError) displayNameSupportingText else null,
            label = "Display Name",
            focusRequester = focusRequester1,
            nextFocusRequester = focusRequester2,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isDisplayNameEdited) viewModel.validateDisplayName()
            }
        )
        AuthTextField(
            value = email,
            onValueChange = {
                viewModel.email = it
                viewModel.isEmailEdited = true
            },
            isError = isEmailError,
            supportingText = if (isEmailError) emailSupportingText else null,
            label = "Email",
            focusRequester = focusRequester2,
            nextFocusRequester = focusRequester3,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isEmailEdited) viewModel.validateEmail()
            }
        )
        AuthTextField(
            value = password,
            onValueChange = {
                viewModel.password = it
                viewModel.isPasswordEdited = true
            },
            isError = isPasswordError,
            supportingText = if (isPasswordError) passwordSupportingText else null,
            label = "ChitChat Password",
            visualTransformation = PasswordVisualTransformation(),
            focusRequester = focusRequester3,
            nextFocusRequester = focusRequester4,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isPasswordEdited) viewModel.validatePassword()
            }
        )
        AuthTextField(
            value = confirmedPassword,
            onValueChange = {
                viewModel.confirmedPassword = it
                viewModel.isConfirmedPasswordEdited = true
            },
            isError = isConfirmedPasswordError,
            supportingText = if (isConfirmedPasswordError) confirmedPasswordSupportingText else null,
            label = "Confirmed ChitChat Password",
            visualTransformation = PasswordVisualTransformation(),
            focusRequester = focusRequester4,
            isTheLastField = true,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isConfirmedPasswordEdited) viewModel.validateConfirmedPassword()
            }
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen({})
}
