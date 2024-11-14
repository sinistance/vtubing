package io.github.sinistance.vtubing

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.sinistance.vtubing.home.presentation.HomeScreen
import io.github.sinistance.vtubing.login.presentation.LoginScreen
import org.koin.compose.KoinContext

@Composable
fun AppTheme() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            Surface {
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
    }
}