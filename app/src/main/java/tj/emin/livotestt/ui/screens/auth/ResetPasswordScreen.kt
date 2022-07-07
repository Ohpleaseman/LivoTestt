package tj.emin.livotestt.ui.screens.auth

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import tj.emin.livotestt.Constants
import tj.emin.testapp.R
import tj.emin.livotestt.data.FirebaseRepository
import tj.emin.livotestt.ui.LoginTextField
import tj.emin.livotestt.ui.common.SpacerBetweenObjects
import tj.emin.livotestt.ui.navigation.Screen

@Composable
fun ResetPasswordScreen(activity: Activity, navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(Constants.SCREEN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current

        val userEmail = remember { mutableStateOf("") }

        LoginTextField(
            field = userEmail,
            label = stringResource(id = R.string.user_email_label),
            imeAction = ImeAction.Done,
            imeActionLambda = { focusManager.moveFocus(FocusDirection.Next) }
        )
        SpacerBetweenObjects()

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                FirebaseRepository.resetPassword(
                    activity,
                    userEmail.value,
                    onSuccess = { navController.navigate(Screen.SignInScreen.route) }
                )
            }
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}