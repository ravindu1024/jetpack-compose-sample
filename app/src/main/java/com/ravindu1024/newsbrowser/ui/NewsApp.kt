package com.ravindu1024.newsbrowser.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.BottomNavBar
import com.ravindu1024.newsbrowser.ui.components.TopBar
import com.ravindu1024.newsbrowser.ui.screens.HeadLineScreen
import com.ravindu1024.newsbrowser.ui.screens.NewsDetailScreen
import com.ravindu1024.newsbrowser.ui.screens.NewsSourcesScreen
import com.ravindu1024.newsbrowser.ui.screens.SavedHeadLinesScreen
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import com.ravindu1024.newsbrowser.utils.LocalDateTimeSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Base64
import kotlin.reflect.typeOf


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Headlines : BottomNavItem("headlines", Icons.Default.List, "Home")
    object Sources : BottomNavItem("sources", Icons.Default.Add, "Sources")
    object Saved : BottomNavItem("saved", Icons.Default.FavoriteBorder, "Saved")
}

sealed class NavItem(val route: String){
    object NewsDetail: NavItem("news_detail/{headline}")
}

@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController()
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    val topBarActionFlow = MutableStateFlow(TopBarUiState())
    val barActionState: TopBarUiState by topBarActionFlow.collectAsState()

    val navItems = listOf(BottomNavItem.Headlines, BottomNavItem.Sources, BottomNavItem.Saved)
    val currentBottomNavScreen = navItems.firstOrNull { it.route == currentRoute }

    // Back button is disabled for the Bottom Nav screens
    val canGoBack = navItems.firstOrNull { it.route == currentRoute } == null &&
            navController.previousBackStackEntry != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                canNavigateBack = canGoBack,
                uiState = barActionState,
                navigateUp = {
                    navController.navigateUp()
                })
        },
        bottomBar = {
            BottomNavBar(
                navItems = navItems,
                currentRoute = currentRoute ?: "",
                navController = navController,
                enabled = currentBottomNavScreen != null
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Headlines.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavItem.Headlines.route) {
                HeadLineScreen(topBarActionFlow, onItemCLicked = { headline ->
//                    val base64 = Base64.getUrlEncoder().encodeToString(Gson().toJson(headline).toByteArray())
//                    navController.navigate("news_detail/$base64")
                    navController.navigate(headline)
                })
            }

            composable(route = BottomNavItem.Sources.route) {
                NewsSourcesScreen(topBarActionFlow)
            }

            composable(route = BottomNavItem.Saved.route) {
                SavedHeadLinesScreen(topBarActionFlow, onItemCLicked = { headline ->
//                    val base64 = Base64.getUrlEncoder().encodeToString(Gson().toJson(headline).toByteArray())
//                    navController.navigate("news_detail/$base64")
                    navController.navigate(headline)
                })
            }

            composable<NewsHeadline>{

            }

            composable<NewsHeadline>{ backstackEntry ->
                val headline: NewsHeadline = backstackEntry.toRoute()
                NewsDetailScreen(newsHeadline = headline, barActions = topBarActionFlow)
            }

//            composable(
//                route = NavItem.NewsDetail.route,
//                arguments = listOf(navArgument("headline"){ type = NavType.SerializableType(NewsHeadline::class.java) })
//            ){
//                NewsDetailScreen(topBarActionFlow)
            //}
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
