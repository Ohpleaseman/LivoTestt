package tj.emin.livotestt.ui.navigation

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import tj.emin.livotestt.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    activity: Activity
) {
    navigation(
        startDestination = Screen.Home.route,
        route = Graph.Home.route
    ) {
        composable(route = Screen.Home.route) { HomeScreen(activity) }
    }
}