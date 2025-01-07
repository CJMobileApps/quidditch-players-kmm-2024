package com.cjmobileapps.quidditch_players_kmm_2024.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource

@Composable
fun QuidditchPlayersImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    when (val imageResource = asyncPainterResource(imageUrl)) {
        is Resource.Loading -> {
            Box(
                modifier = modifier.background(shimmerBrush()),
            )
        }

        is Resource.Success -> {
            Image(
                modifier = modifier,
                painter = imageResource.value,
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        is Resource.Failure -> {
            Box(
                modifier = modifier.background(shimmerBrush()),
            )
        }
    }
}
