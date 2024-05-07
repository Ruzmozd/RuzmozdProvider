package ir.hirkancorp.data.profile_image.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUrlData(
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
)