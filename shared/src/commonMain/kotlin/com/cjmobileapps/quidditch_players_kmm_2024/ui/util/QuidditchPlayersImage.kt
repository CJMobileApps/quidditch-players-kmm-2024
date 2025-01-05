package com.cjmobileapps.quidditch_players_kmm_2024.ui.util

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun QuidditchPlayersImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val showImageShimmer = remember { mutableStateOf(true) }

    KamelImage(
        modifier = modifier
            .background(shimmerBrush(showShimmer = showImageShimmer.value)),
        resource = { asyncPainterResource(imageUrl) },
        contentDescription = contentDescription,
        contentScale = contentScale,

    )

//    AsyncImage(
//        modifier =
//            modifier
//                .background(shimmerBrush(showShimmer = showImageShimmer.value)),
//        model = imageUrl,
//        contentScale = contentScale,
//        contentDescription = contentDescription,
//        onLoading = { showImageShimmer.value = true },
//        onSuccess = { showImageShimmer.value = false },
//        onError = { showImageShimmer.value = false },
//    )
}


//@Composable
//fun BirdImageCell(image: BirdImage) {
//    KamelImage(
//        resource = { asyncPainterResource("https://sebi.io/demo-image-api/${image.path}") },
//        contentDescription = "${image.category} by ${image.author}",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
//    )
//}
