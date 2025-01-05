package com.cjmobileapps.quidditch_players_kmm_2024.ui.util

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import quidditch_players_kmm_2024.shared.generated.resources.Res
import quidditch_players_kmm_2024.shared.generated.resources.animation_transition
import quidditch_players_kmm_2024.shared.generated.resources.translateanimation

@Composable
fun shimmerBrush(showShimmer: Boolean = true): Brush {
    val widthOfShadowBrush = 500
    val angleOfAxisY = 270f
    val durationMillis = 1000f

    return if (showShimmer) {
        val shimmerColors =
            listOf(
                Color.LightGray.copy(alpha = 0.3f),
                Color.LightGray.copy(alpha = 0.5f),
                Color.LightGray.copy(alpha = 1.0f),
                Color.LightGray.copy(alpha = 0.5f),
                Color.LightGray.copy(alpha = 0.3f),
            )

        val transition =
            rememberInfiniteTransition(label = stringResource(Res.string.animation_transition))
        val translateAnimation =
            transition.animateFloat(
                initialValue = 0f,
                targetValue = durationMillis,
                animationSpec =
                    infiniteRepeatable(
                        animation = tween(durationMillis.toInt()),
                        repeatMode = RepeatMode.Reverse,
                    ),
                label = stringResource(Res.string.translateanimation),
            )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
            end = Offset(x = translateAnimation.value, y = angleOfAxisY),
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero,
        )
    }
}
