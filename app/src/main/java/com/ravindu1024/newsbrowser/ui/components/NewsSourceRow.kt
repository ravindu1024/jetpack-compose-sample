package com.ravindu1024.newsbrowser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ravindu1024.newsbrowser.model.domain.NewsSource
import com.ravindu1024.newsbrowser.ui.theme.CustomTypography
import com.ravindu1024.newsbrowser.ui.theme.NewsBrowserTheme

@Composable
fun NewsSourceRow(
    source: NewsSource,
    isChecked: Boolean,
    onSwitchClicked: (Boolean, NewsSource) -> Unit
){
    ListItem(modifier = Modifier
        .padding(0.dp)
        .background(Color.Blue), headlineContent = {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
            val (text, switch) = createRefs()
            Text(
                text = source.name,
                style = CustomTypography.textRegular,
                modifier = Modifier.constrainAs(text){
                    start.linkTo(parent.start, margin = 0.dp)
                    top.linkTo(parent.top, margin = 0.dp)
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                    end.linkTo(switch.start)
                    width = Dimension.fillToConstraints
                }
            )
            Switch(
                checked = isChecked,
                onCheckedChange = { onSwitchClicked(it, source) },
                modifier = Modifier.constrainAs(switch){
                    top.linkTo(parent.top, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)

                }
            )
        }
    })
}

@Preview
@Composable
fun NewsSourceRowPreview(){
    NewsBrowserTheme {
        NewsSourceRow(
            source = NewsSource(
                id = "abc",
                name = "ABC News",
                isSaved = true
            ),
            isChecked = true,
            onSwitchClicked = {_, _ -> }
        )
    }
}