package org.example.project.theme.uiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import org.example.project.ext.shimmerEffect

@Composable
fun MaxiImage(
    url: String,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        loading = {
            Box(modifier.shimmerEffect())
        },
        model = url,
        contentDescription = contentDescription,
        modifier = modifier.background(Color.White),
        contentScale = contentScale,
        alpha = alpha,
        alignment = alignment,
        colorFilter = colorFilter
    )
}