package tj.emin.livotestt.ui.screens.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tj.emin.livotestt.AuthActivity
import tj.emin.livotestt.Constants
import tj.emin.livotestt.MainActivity
import tj.emin.livotestt.data.FirebaseRepository
import tj.emin.livotestt.data.NO_VALUE_STRING
import tj.emin.livotestt.data.dataStore
import tj.emin.livotestt.data.model.User
import tj.emin.livotestt.data.setUserEmail
import tj.emin.livotestt.ui.common.MediumSpacer
import tj.emin.livotestt.ui.common.SpacerBetweenObjects
import tj.emin.testapp.R

@Composable
fun HomeScreen(activity: Activity) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(Constants.SCREEN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val ourUser = remember { mutableStateOf(User()) }

        Text(text = stringResource(id = R.string.welcome), fontSize = 24.sp)
        if (ourUser.value.email.isNotEmpty()) {
            SpacerBetweenObjects()
            Text(text = ourUser.value.name)
            SpacerBetweenObjects()
            Text(text = ourUser.value.email)
            SpacerBetweenObjects()
            Text(text = ourUser.value.phone)
        }

        val uid = FirebaseRepository.auth.uid.toString()
        FirebaseRepository.userCollection.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val data = task.result
                try {
                    val user = data.toObject(User::class.java)
                    user?.let { ourUser.value = user }
                    Log.d("TAG", "getUserFromLogin: $user")
                } catch (e: Exception) {
                    Log.d("TAG", "getUserFromLogin: ${e.message}")
                }
            } else {
                Toast.makeText(
                    activity, task.exception?.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        MediumSpacer()

        // Sign out
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                FirebaseRepository.signOut()
                scope.launch { setUserEmail(context.dataStore, NO_VALUE_STRING) }
                context.startActivity(Intent(context, AuthActivity::class.java))
                activity.finish()
            }
        ) {
            Text(text = stringResource(id = R.string.signout))
        }
    }
}