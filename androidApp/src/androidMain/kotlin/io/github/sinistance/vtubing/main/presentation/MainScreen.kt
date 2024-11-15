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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.serialization.generateHashCode
import io.github.sinistance.vtubing.navigation.AppNavigation
import io.github.sinistance.vtubing.navigation.Route
import io.github.sinistance.vtubing.navigation.bottomNavItems
import io.github.sinistance.vtubing.navigation.bottomNavRoutes
import kotlinx.serialization.serializer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentDestinationId = currentDestination?.id
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("VTubing")
                }
            )
        },
        bottomBar = {
            if (currentDestinationId in bottomNavRoutes.map { it.id }) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                            onClick = {
                                if (currentDestinationId != item.route.id) {
                                    navController.navigate(item.route) {
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

data class NavigationItem(val route: Route, val label: String, val icon: ImageVector)