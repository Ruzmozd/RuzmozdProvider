import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (checked: Boolean) -> Unit
) {

    var size by remember { mutableStateOf(IntSize.Zero) }
    val color = if (checked) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = .5f)

    val onOffset =  Offset((size.height / 2).toFloat(), 0f)
    val offOffset =  Offset(-(size.height / 2).toFloat(), 0f)

    val targetOffset = if(checked) onOffset else offOffset
    val animatedOffset by animateOffsetAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Box(
        modifier = modifier.onGloballyPositioned { layoutCoordinates -> size = layoutCoordinates.size }
            .clip(RoundedCornerShape(percent = 50))
            .clickable { onCheckedChange(!checked) }
            .border(2.dp, SolidColor(color),shape = RoundedCornerShape(percent = 50)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(((size.height / 3) - 4).dp)
                .offset { IntOffset(animatedOffset.x.roundToInt(), animatedOffset.y.roundToInt()) }
                .clip(RoundedCornerShape(percent = 50))
                .background(color)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StateSwitchPreview() {
    RuzmozdProviderTheme {
        AnimatedSwitch(
            modifier = Modifier.width(80.dp).height(50.dp),
        ) {}
    }
}