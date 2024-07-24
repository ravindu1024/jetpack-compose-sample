package com.ravindu1024.newsbrowser

import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import com.ravindu1024.domain.model.NewsSource
import com.ravindu1024.newsbrowser.features.sources.NewsSourcesScreenContent
import com.ravindu1024.newsbrowser.features.sources.SourcesListUiState
import org.junit.Rule
import org.junit.Test

class NewsSourcesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifySources(){

        composeTestRule.setContent {
            val sources = listOf(
                NewsSource(id = "abc", "ABC News"),
                NewsSource(id = "afr", "AFR News")
            )
            val saved = listOf("afr")
            val uiState by remember {
                mutableStateOf(SourcesListUiState(sources = sources, savedSources = saved))
            }
            val pullRefreshState = rememberPullToRefreshState()
            pullRefreshState.endRefresh()
            NewsSourcesScreenContent(uiState = uiState, pullRefreshState = pullRefreshState, modifier = Modifier.testTag("list")) { _, _ ->
                
            }
        }


        composeTestRule
            .onNodeWithTag("list")  //Box
            .onChildAt(0)   //Lazy list
            .onChildAt(0)   //item 1
            .assert(hasText("ABC News"))

        composeTestRule
            .onNodeWithTag("list")  //Box
            .onChildAt(0)   //Lazy list
            .onChildAt(1)   //item 2
            .assert(hasText("AFR News"))

    }
}