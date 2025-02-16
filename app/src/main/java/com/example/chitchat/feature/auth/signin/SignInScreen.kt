package com.example.chitchat.feature.auth.signin

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chitchat.MainActivity
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField


@Composable
fun SignInScreen(
    onAlternativeMethodPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val email by viewModel::email
    val isEmailError by viewModel::isEmailError
    val isEmailEdited by viewModel::isEmailEdited
    val emailSupportingText by viewModel::emailSupportingText

    val password by viewModel::password
    val isPasswordError by viewModel::isPasswordError
    val isPasswordEdited by viewModel::isPasswordEdited
    val passwordSupportingText by viewModel::passwordSupportingText

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }

    val activity = LocalActivity.current as MainActivity
    val context = LocalContext.current
    val auth = activity.auth

    AuthScreenWrapper(
        nextButtonTitle = "Sign In",
        alternativeMethodTitle = "New to ChitChat? Sign Up",
        onNextPress = {
            val isAllGood = viewModel.validateTextFields()
            if (isAllGood) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) { task ->
                        val msg = if (task.isSuccessful) "Sign in success." else "Email or password is incorrect."
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

            }
        },
        onAlternativeMethodPress = onAlternativeMethodPress
    ) {
        AuthTextField(
            value = email,
            onValueChange = {
                viewModel.email = it
                viewModel.isEmailEdited = true
            },
            label = "Email",
            isError = isEmailError,
            supportingText = if (isEmailError) emailSupportingText else null,
            focusRequester = focusRequester1,
            nextFocusRequester = focusRequester2,
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isEmailEdited) viewModel.validateEmail()
                },
        )
        AuthTextField(
            value = password,
            onValueChange = {
                viewModel.password = it
                viewModel.isPasswordEdited = true
            },
            isError = isPasswordError,
            label = "ChitChat Password",
            supportingText = if (isPasswordError) passwordSupportingText else null,
            visualTransformation = PasswordVisualTransformation(),
            focusRequester = focusRequester2,
            isTheLastField = true,
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isPasswordEdited) viewModel.validatePassword()
                }
        )
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen({})
}