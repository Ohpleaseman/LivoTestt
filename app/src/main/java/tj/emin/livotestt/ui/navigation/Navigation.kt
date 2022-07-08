package tj.emin.livotestt.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun Navigation(activity: FragmentActivity, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Graph.Auth.route
    ) {
        authGraph(activity, navController)
    }
}

sealed class Graph(val route: String) {
    object Home : Graph("HomeGraph")
    object Auth : Graph("AuthGraph")
}

sealed class Screen(val route: String) {
    // BottomNavParts
    object Home : Screen("HomeScreen")

    // Login
    object SignInScreen : Screen("SignInScreen")
    object SignUpScreen : Screen("SignUpScreen")
    object ResetPasswordScreen : Screen("ResetPasswordScreen")
}
