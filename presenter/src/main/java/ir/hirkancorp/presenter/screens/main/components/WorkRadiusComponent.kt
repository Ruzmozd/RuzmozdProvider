package ir.hirkancorp.presenter.screens.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R

@Composable
fun WorkRadius(
    modifier: Modifier = Modifier,
    radius: Int,
    setRadius: (radius: Int) -> Unit,
) {
    var isOpen by remember { mutableStateOf(false) }
    var currentRadius by remember { mutableIntStateOf(radius) }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(percent = 50),
                    clip = true
                )
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colors.primary)
                .clickable {
                    if (isOpen) setRadius(currentRadius)
                    isOpen = !isOpen
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isOpen) stringResource(id = R.string.all_submit) else  stringResource(id = R.string.main_screen_radius_text),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button,
                fontSize = TextUnit(9f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        AnimatedVisibility(visible = isOpen) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(percent = 50),
                        clip = true
                    )
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colors.surface)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }
                Slider(
                    modifier = Modifier.weight(1f),
                    value = currentRadius.toFloat(),
                    onValueChange = { currentRadius = it.toInt() },
                    valueRange = 1f..50f,
                    steps = 50
                )
                Text(text = stringResource(R.string.main_screen_provider_radius, currentRadius))
            }
        }
    }
}