package ir.hirkancorp.user.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.hirkancorp.presenter.R

val YekanX = FontFamily(
    Font(R.font.iran_yekan_x_regular),
    Font(R.font.iran_yekan_x_medium),
    Font(R.font.iran_yekan_x_bold)
)

val Typography = Typography(
    body2 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    body1 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.25.sp
    ),
    h4 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.25.sp
    ),
    h6 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    button = TextStyle(
        fontFamily = YekanX,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
    caption = TextStyle().copy(
        fontFamily = YekanX,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)
