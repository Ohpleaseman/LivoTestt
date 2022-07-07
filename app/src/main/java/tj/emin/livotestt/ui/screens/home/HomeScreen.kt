package tj.emin.livotestt.ui.screens.home

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import tj.emin.livotestt.Constants
import tj.emin.testapp.R
import tj.emin.livotestt.data.FirebaseRepository
import tj.emin.livotestt.ui.common.MediumSpacer
import tj.emin.livotestt.ui.common.SpacerBetweenObjects

@Composable
fun HomeScreen(activity: Activity) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(Constants.SCREEN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FirebaseRepository.auth.currentUser?.let {
            Text(text = stringResource(id = R.string.welcome), fontSize = 24.sp)
            SpacerBetweenObjects()
            Text(text = it.email ?: "")
        }
        MediumSpacer()

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                FirebaseRepository.logOut()
                activity.finish()
            }
        ) {
            Text(text = stringResource(id = R.string.signout))
        }
    }
}