package tj.emin.livotestt.ui.navigation

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import tj.emin.livotestt.ui.screens.auth.ResetPasswordScreen
import tj.emin.livotestt.ui.screens.auth.SignInScreen
import tj.emin.livotestt.ui.screens.auth.SignUpScreen

fun NavGraphBuilder.authGraph(
    activity: Activity,
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.SignInScreen.route,
        route = Graph.Auth.route
    ) {
        composable(route = Screen.SignInScreen.route) { SignInScreen(activity, navController) }
        composable(route = Screen.SignUpScreen.route) { SignUpScreen(activity, navController) }
        composable(route = Screen.ResetPasswordScreen.route) { ResetPasswordScreen(activity, navController) }
    }
}