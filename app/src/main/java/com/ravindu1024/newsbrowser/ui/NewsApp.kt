package com.ravindu1024.newsbrowser.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ravindu1024.newsbrowser.ui.components.BottomNavBar
import com.ravindu1024.newsbrowser.ui.components.TopBar
import com.ravindu1024.newsbrowser.ui.screens.HeadLineScreen
import com.ravindu1024.newsbrowser.ui.screens.NewsSourcesScreen
import com.ravindu1024.newsbrowser.ui.screens.SavedHeadLinesScreen
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Headlines : BottomNavItem("headlines", Icons.Default.List, "Home")
    object Sources : BottomNavItem("sources", Icons.Default.Add, "Sources")
    object Saved : BottomNavItem("saved", Icons.Default.Favorite, "Saved")
}

@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController()
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    val navItems = listOf(BottomNavItem.Headlines, BottomNavItem.Sources, BottomNavItem.Saved)
    val currentScreen = navItems.firstOrNull { it.route == currentRoute }

    // Back button is disabled for the Bottom Nav screens
    val canGoBack = navItems.firstOrNull { it.route == currentRoute } == null &&
            navController.previousBackStackEntry != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                canNavigateBack = canGoBack,
                title = currentScreen?.label,
                navigateUp = {
                    navController.navigateUp()
                })
        },
        bottomBar = {
            BottomNavBar(
                navItems = navItems,
                currentRoute = currentRoute ?: "",
                navController = navController
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Headlines.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavItem.Headlines.route) {
                HeadLineScreen()
            }

            composable(route = BottomNavItem.Sources.route) {
                NewsSourcesScreen()
            }

            composable(route = BottomNavItem.Saved.route) {
                SavedHeadLinesScreen()
            }
        }
    }
}


@Preview
@Composable
fun NewsAppPreview() {
    NewsBrowserTheme {
        NewsApp()
    }
}
