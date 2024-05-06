package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ImageWithUploadButton() {
    RuzmozdProviderTheme(
        darkTheme = false
    ) {
        Box(
            modifier = Modifier.wrapContentSize(align = Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            CircularImage(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
                imageUrl = ""
            )
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomEnd)
                    .padding(2.dp),
                shape = RoundedCornerShape(percent = 50),
                border = BorderStroke(1.dp, MaterialTheme.colors.background),
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp
            ) {
                Icon(
                    modifier = Modifier.width(20.dp).height(20.dp).padding(4.dp),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit"
                )
            }
        }
    }
}