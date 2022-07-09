package tj.emin.livotestt.ui.screens.auth

import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import tj.emin.livotestt.Constants
import tj.emin.livotestt.MainActivity
import tj.emin.livotestt.data.FirebaseRepository
import tj.emin.livotestt.data.model.User
import tj.emin.livotestt.ui.common.TextField
import tj.emin.livotestt.ui.common.SpacerBetweenObjects
import tj.emin.livotestt.ui.navigation.Screen
import tj.emin.testapp.R

@Composable
fun SignUpScreen(activity: Activity, navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(Constants.SCREEN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current

        val userName = remember { mutableStateOf("") }
        val userEmail = remember { mutableStateOf("") }
        val userPhone = remember { mutableStateOf("") }
        val userPassword = remember { mutableStateOf("") }

        TextField(
            field = userName,
            label = stringResource(id = R.string.user_name_label),
            imeAction = ImeAction.Next,
            imeActionLambda = { focusManager.moveFocus(FocusDirection.Next) }
        )
        SpacerBetweenObjects()

        TextField(
            field = userEmail,
            label = stringResource(id = R.string.user_email_label),
            imeAction = ImeAction.Next,
            imeActionLambda = { focusManager.moveFocus(FocusDirection.Next) }
        )
        SpacerBetweenObjects()

        TextField(
            field = userPhone,
            label = stringResource(id = R.string.user_phone_label),
            imeAction = ImeAction.Next,
            imeActionLambda = { focusManager.moveFocus(FocusDirection.Next) }
        )
        SpacerBetweenObjects()

        TextField(
            field = userPassword,
            label = stringResource(id = R.string.user_password_label),
            imeAction = ImeAction.Done,
            imeActionLambda = {
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        )
        SpacerBetweenObjects()

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                FirebaseRepository.createUser(
                    activity,
                    userEmail.value,
                    userPassword.value,
                    User(userEmail.value, userPhone.value, userName.value),
                    onSuccess = { navController.navigate(Screen.SignInScreen.route) }
                )
            }
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}