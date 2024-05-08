package ir.hirkancorp.presenter.screens.profile_image

import ir.hirkancorp.presenter.core.utils.UiText

data class ProfileImageState(
    val isLoading: Boolean = false,
    val image: List<Byte> = emptyList(),
    val imageUrl: String = "",
    val success: UiText = UiText.DynamicString(""),
    val error: UiText = UiText.DynamicString(""),
)