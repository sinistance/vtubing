package io.github.sinistance.vtubing.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.sinistance.vtubing.home.presentation.HomeScreen
import io.github.sinistance.vtubing.login.presentation.LoginScreen
import io.github.sinistance.vtubing.main.presentation.NavigationItem
import io.github.sinistance.vtubing.mypage.presentation.MyPageScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.Login
    ) {
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.Home> {
            HomeScreen()
        }
        composable<Route.Broadcast> {
            TODO()
        }
        composable<Route.MyPage> {
            MyPageScreen()
        }
    }
}

val bottomNavItems = listOf(
    NavigationItem(Route.Home, "Home", Icons.Filled.Home),
    NavigationItem(Route.Broadcast, "Broadcast", Icons.Filled.AddCircle),
    NavigationItem(Route.MyPage, "MyPage", Icons.Filled.Person),
)

val bottomNavRoutes = listOf(
    Route.Home,
    Route.Broadcast,
    Route.MyPage,
)