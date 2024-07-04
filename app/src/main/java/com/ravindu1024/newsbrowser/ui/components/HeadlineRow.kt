package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.newsbrowser.ui.theme.CustomColors.newsCardColor
import com.ravindu1024.newsbrowser.ui.theme.CustomTypography
import com.ravindu1024.newsbrowser.ui.theme.Dimensions
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme
import com.ravindu1024.newsbrowser.utils.DateUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeadlineRow(
    headline: NewsHeadline,
    onItemClick: (NewsHeadline) -> Unit
) {
    ListItem(headlineContent = {
        Column(
            modifier = Modifier
                .background(
                    colorScheme.newsCardColor,
                    shape = RoundedCornerShape(Dimensions.cornerRadiusNormal)
                )
                .padding(bottom = Dimensions.marginNormal)
                .clickable { onItemClick(headline) }
        ) {
            headline.urlToImage?.let {
                GlideImage(
                    model = it,
                    contentDescription = headline.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimensions.cornerRadiusNormal,
                                topEnd = Dimensions.cornerRadiusNormal
                            )
                        )
                        .fillMaxWidth()
                        .aspectRatio(1.8f, true)
                )
            }

            Text(
                text = headline.title,
                style = CustomTypography.textMediumBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimensions.marginSmall,
                        start = Dimensions.marginSmall,
                        end = Dimensions.marginSmall
                    )
            )
            val author = if (headline.author != null) {
                "${headline.author}, ${headline.source ?: ""}"
            } else {
                headline.source ?: ""
            }
            Text(
                text = author,
                style = CustomTypography.textLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp,
                        start = Dimensions.marginSmall,
                        end = Dimensions.marginSmall
                    )
            )
            Text(
                text = DateUtils.formatDate(
                    DateUtils.serverTimeToDate(headline.publishedAt),
                    "dd MMM yyyy"
                ),
                style = CustomTypography.textSmallLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 2.dp,
                        start = Dimensions.marginSmall,
                        end = Dimensions.marginSmall
                    )
            )
            Text(
                text = headline.description,
                maxLines = 3,
                style = CustomTypography.textRegular,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimensions.marginNormal,
                        start = Dimensions.marginSmall,
                        end = Dimensions.marginSmall
                    )
            )
        }
    })
}

@Preview
@Composable
fun HeadlineRowPreview() {
    NewsBrowserTheme {
        HeadlineRow(
            headline = NewsHeadline(
                author = "John Doe",
                description = "This is a test news item",
                urlToImage = "",
                url = "",
                publishedAt = "2024-03-10T10:00:00",
                source = "ABC News",
                title = "News Headline Title"
            )
        ) {}
    }
}