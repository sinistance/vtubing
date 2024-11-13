package io.github.sinistance.vtubing

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.sinistance.vtubing.screen.HomeScreen
import io.github.sinistance.vtubing.screen.LoginScreen
import io.github.sinistance.vtubing.screen.Screen

@Composable
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold { contentPadding ->
            NavHost(
                modifier = Modifier.padding(contentPadding),
                navController = navController,
                startDestination = Screen.Login
            ) {
                composable<Screen.Login> {
                    LoginScreen()
                }
                composable<Screen.Home> {
                    HomeScreen()
                }
            }
        }
    }
}