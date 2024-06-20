package com.ravindu1024.newsbrowser.ui.components


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ravindu1024.newsbrowser.ui.BottomNavItem


@Composable
fun BottomNavBar(
    navItems: List<BottomNavItem>,
    currentRoute: String,
    navController: NavController
){
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
