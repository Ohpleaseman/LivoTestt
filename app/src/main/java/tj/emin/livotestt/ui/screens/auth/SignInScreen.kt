package tj.emin.livotestt.ui.screens.auth

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tj.emin.livotestt.BiometricPromptUtils
import tj.emin.livotestt.Constants
import tj.emin.livotestt.MainActivity
import tj.emin.livotestt.data.*
import tj.emin.livotestt.ui.common.TextField
import tj.emin.livotestt.ui.common.MediumSpacer
import tj.emin.livotestt.ui.common.SpacerBetweenObjects
import tj.emin.livotestt.ui.navigation.Screen
import tj.emin.testapp.R

@Composable
fun SignInScreen(activity: FragmentActivity, navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val emailPrefs = remember { mutableStateOf(NO_VALUE_STRING) }

    val canAuthenticateWithBiometric =
        BiometricManager.from(activity).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS

    fun toMainActivity() = context.startActivity(Intent(context, MainActivity::class.java))

    val biometricPrompt = BiometricPromptUtils.createBiometricPrompt(activity) {
        toMainActivity()
    }
    val promptInfo = BiometricPromptUtils.createPromptInfo(activity)

    LaunchedEffect(key1 = true) {
        if (canAuthenticateWithBiometric) biometricPrompt.authenticate(promptInfo)
        scope.launch {
            emailPrefs.value = getUserEmail(context.dataStore) ?: NO_VALUE_STRING
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(Constants.SCREEN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current

        val userEmail = remember { mutableStateOf("") }
        val userPassword = remember { mutableStateOf("") }

        fun tryLogin() {
            FirebaseRepository.login(
                activity,
                if(emailPrefs.value == NO_VALUE_STRING) userEmail.value else emailPrefs.value,
                userPassword.value,
                onSuccess = {
                    toMainActivity()
                    scope.launch {
                        if(emailPrefs.value == NO_VALUE_STRING)
                            setUserEmail(context.dataStore, userEmail.value)
                    }
                    activity.finish()
                }
            )
        }

        if(emailPrefs.value == NO_VALUE_STRING){
            TextField(
                field = userEmail,
                label = stringResource(id = R.string.user_email_label),
                imeAction = ImeAction.Next,
                imeActionLambda = { focusManager.moveFocus(FocusDirection.Next) }
            )
            SpacerBetweenObjects()
        }

        TextField(
            field = userPassword,
            label = stringResource(id = R.string.user_password_label),
            imeAction = ImeAction.Done,
            imeActionLambda = { tryLogin() }
        )
        SpacerBetweenObjects()

        // Forgot password?
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate(Screen.ResetPasswordScreen.route) }
        ) {
            Text(text = stringResource(id = R.string.forgot_password))
        }

        // Sign in
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                tryLogin()
            }
        ) {
            Text(text = stringResource(id = R.string.signin))
        }

        // Sign up
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.SignUpScreen.route)
            }
        ) {
            Text(text = stringResource(id = R.string.signup))
        }
        MediumSpacer()

        // Fingerprint
        if (canAuthenticateWithBiometric) {
            IconButton(
                modifier = Modifier.size(80.dp),
                onClick = {
                    biometricPrompt.authenticate(promptInfo)
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_baseline_fingerprint_24),
                    contentDescription = null
                )
            }
        }
    }
}