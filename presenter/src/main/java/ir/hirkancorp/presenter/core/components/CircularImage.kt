package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import ir.hirkancorp.presenter.R

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.all_person),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f))

    )
}