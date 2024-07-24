package com.ravindu1024.newsbrowser

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ravindu1024.newsbrowser.ui.components.basic.TopBar
import com.ravindu1024.newsbrowser.ui.state.TopBarAction
import com.ravindu1024.newsbrowser.ui.state.TopBarUiState
import org.junit.Rule
import org.junit.Test

class TopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topBarTest(){
        composeTestRule.setContent {
            val uiState = TopBarUiState(title = "Title", actions = emptyList())
            TopBar(canNavigateBack = true, uiState = uiState, navigateUp = {  })
        }

        composeTestRule
            .onNodeWithText("Title")
            .assertExists()
    }

    @Test
    fun topBarActionTest(){
        var clicked = false
        composeTestRule.setContent {
            val actions = listOf(
                TopBarAction(icon = Icons.Default.FavoriteBorder, onClick = { clicked = true })
            )
            val uiState by remember {
                mutableStateOf(TopBarUiState(title = "Title", actions = actions))
            }

            TopBar(canNavigateBack = true, uiState = uiState, navigateUp = {  })
        }

        composeTestRule
            .onNodeWithText("Title")
            .assertExists()

        assert(!clicked)

        composeTestRule
            .onNodeWithTag(Icons.Default.FavoriteBorder.name)
            .assertExists()
            .performClick()


        assert(clicked)
    }
}