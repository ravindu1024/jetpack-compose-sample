package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ravindu1024.newsbrowser.model.domain.NewsHeadline
import com.ravindu1024.newsbrowser.ui.theme.CustomTypography
import com.ravindu1024.newsbrowser.ui.theme.Gray100
import com.ravindu1024.newsbrowser.utils.DateUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeadLineLazyList(
    items: List<NewsHeadline>,
    onItemClick: (NewsHeadline) -> Unit
){
    LazyColumn() {
        items(items){ headline ->
            ListItem(headlineContent = {
                Column(
                    modifier = Modifier
                        .background(Gray100, shape = RoundedCornerShape(6.dp))
                        .padding(bottom = 16.dp)
                        .clickable { onItemClick(headline) }
                ) {
                    headline.urlToImage?.let {
                        GlideImage(
                            model = it,
                            contentDescription = headline.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                .fillMaxWidth()
                                .aspectRatio(1.8f, true)
                        )
                    }

                    Text(
                        text = headline.title,
                        style = CustomTypography.textMediumBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    )
                    val author = if(headline.author != null){
                        "${headline.author}, ${headline.source ?: ""}"
                    }else{
                        headline.source ?: ""
                    }
                    Text(
                        text = author,
                        style = CustomTypography.textLight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = DateUtils.formatDate(DateUtils.serverTimeToDate(headline.publishedAt), "dd MMM yyyy"),
                        style = CustomTypography.textSmallLight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, start = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = headline.description,
                        maxLines = 3,
                        style = CustomTypography.textRegular,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                    )
                }
            })
        }
    }
}