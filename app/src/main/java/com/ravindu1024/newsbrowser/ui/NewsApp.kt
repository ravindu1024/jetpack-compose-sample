package com.ravindu1024.newsbrowser.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.newsbrowser.ui.components.BottomNavBar
import com.ravindu1024.newsbrowser.ui.components.TopBar
import com.ravindu1024.newsbrowser.ui.screens.HeadLineScreen
import com.ravindu1024.newsbrowser.ui.screens.NewsDetailScreen
import com.ravindu1024.newsbrowser.ui.screens.NewsSourcesScreen
import com.ravindu1024.newsbrowser.ui.screens.SavedHeadLinesScreen
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable


sealed class BottomNavDestination(val route: String, val icon: ImageVector, val label: String) {
    object Headlines : BottomNavDestination("headlines", Icons.Default.List, "Home")
    object Sources : BottomNavDestination("sources", Icons.Default.Add, "Sources")
    object Saved : BottomNavDestination("saved", Icons.Default.FavoriteBorder, "Saved")
}

// Other Nav Destinations

@Serializable
data class NewsDetail(val encodedHeadline: String)


@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController()
) {
    val gson = Gson()

    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    val topBarActionFlow = MutableStateFlow(TopBarUiState())
    val barActionState: TopBarUiState by topBarActionFlow.collectAsState()

    val navItems = listOf(
        BottomNavDestination.Headlines,
        BottomNavDestination.Sources,
        BottomNavDestination.Saved
    )
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
                navController = navController,
                navItems = navItems,
                currentRoute = currentRoute.orEmpty(),
                enabled = currentBottomNavScreen != null
            )
        }
    ) { innerPadding ->

        SetupNavHost(
            gson = gson,
            navController = navController,
            innerPadding = innerPadding,
            barActionsCallBack = { title, actions ->
                topBarActionFlow.update {
                    it.copy(
                        title = title,
                        actions = actions
                    )
                }
            }
        )
    }
}

@Composable
private fun SetupNavHost(
    gson: Gson,
    navController: NavHostController,
    innerPadding: PaddingValues,
    barActionsCallBack: ((String, List<TopBarAction>) -> Unit)
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.Headlines.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = BottomNavDestination.Headlines.route) {
            HeadLineScreen(barActionsCallBack, onItemCLicked = { headline ->
                navController.navigate(NewsDetail(gson.toJson(headline)))
            })
        }

        composable(route = BottomNavDestination.Sources.route) {
            NewsSourcesScreen(barActionsCallBack)
        }

        composable(route = BottomNavDestination.Saved.route) {
            SavedHeadLinesScreen(barActionsCallBack, onItemCLicked = { headline ->
                navController.navigate(NewsDetail(gson.toJson(headline)))
            })
        }

        composable<NewsDetail> { backstackEntry ->
            val detail = backstackEntry.toRoute<NewsDetail>()
            val headline = gson.fromJson(detail.encodedHeadline, NewsHeadline::class.java)

            NewsDetailScreen(newsHeadline = headline, barActions = barActionsCallBack)
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
