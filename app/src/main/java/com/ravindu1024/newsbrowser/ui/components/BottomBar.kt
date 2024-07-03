package com.ravindu1024.newsbrowser.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.ravindu1024.newsbrowser.ui.BottomNavDestination
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme


@Composable
fun BottomNavBar(
    navController: NavController,
    navItems: List<BottomNavDestination>,
    currentRoute: String,
    enabled: Boolean
) {
    AnimatedVisibility(visible = enabled) {
        NavigationBar {
            navItems.forEach {
                NavigationBarItem(
                    selected = currentRoute == it.route,
                    onClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(it.icon, contentDescription = null) },
                    label = { Text(text = it.label) }
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    NewsBrowserTheme {
        BottomNavBar(
            navController = rememberNavController(),
            navItems = listOf(
                BottomNavDestination.Headlines,
                BottomNavDestination.Sources,
                BottomNavDestination.Saved
            ),
            currentRoute = "",
            enabled = true
        )
    }
}
