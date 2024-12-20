package io.github.sinistance.vtubing.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.sinistance.vtubing.navigation.AppNavigation
import io.github.sinistance.vtubing.navigation.Screen
import io.github.sinistance.vtubing.navigation.bottomNavItems
import io.github.sinistance.vtubing.navigation.bottomNavScreens
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = koinViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentDestinationId = currentDestination?.id
    val uiState = viewModel.uiState.collectAsState()

    viewModel.showBottomNav(currentDestinationId in bottomNavScreens.map { it.id })

    Scaffold(
        topBar = {
            val title = uiState.value.title
            if (title != null) {
                TopAppBar(
                    title = {
                        Text(text = title)
                    }
                )
            }
        },
        bottomBar = {
            if (uiState.value.showBottomNav) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true,
                            onClick = {
                                if (currentDestinationId != item.screen.id) {
                                    navController.navigate(item.screen) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        AppNavigation(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
        )
    }
}

data class NavigationItem(val screen: Screen, val label: String, val icon: ImageVector)