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
import androidx.navigation.toRoute
import io.github.sinistance.vtubing.home.presentation.HomeScreen
import io.github.sinistance.vtubing.login.presentation.LoginScreen
import io.github.sinistance.vtubing.main.presentation.NavigationItem
import io.github.sinistance.vtubing.mypage.presentation.MyPageScreen
import io.github.sinistance.vtubing.people.domain.entity.Person
import io.github.sinistance.vtubing.stream.presentation.StreamScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Login
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Screen.Home> {
            HomeScreen(
                onItemClick = {
                    navController.navigate(
                        Screen.Stream(
                            name = it.name,
                            photoUrl = it.photoUrl,
                            streamUrl = it.streamUrl,
                        )
                    )
                }
            )
        }
        composable<Screen.Stream> {
            val stream = it.toRoute<Screen.Stream>()
            StreamScreen(
                name = stream.name,
                photoUrl = stream.photoUrl,
                streamUrl = stream.streamUrl,
            )
        }
        composable<Screen.Broadcast> {
            TODO()
        }
        composable<Screen.MyPage> {
            MyPageScreen()
        }
    }
}

val bottomNavItems = listOf(
    NavigationItem(Screen.Home, "Home", Icons.Filled.Home),
    NavigationItem(Screen.Broadcast, "Broadcast", Icons.Filled.AddCircle),
    NavigationItem(Screen.MyPage, "MyPage", Icons.Filled.Person),
)

val bottomNavScreens = listOf(
    Screen.Home,
    Screen.Broadcast,
    Screen.MyPage,
)