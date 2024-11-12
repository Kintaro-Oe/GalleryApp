package com.kintaro.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kintaro.gallery.ui.theme.GalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryTheme {
                GalleryApp(artworks)
            }
        }
    }
}

data class ArtworkIds(
    val imageResourceID: Int,
    val titleResourceID: Int,
    val artistResourceID: Int,
    val yearResourceID: Int
)

val artworks = listOf(
    ArtworkIds(
        imageResourceID = R.drawable.artwork_1,
        titleResourceID = R.string.artwork_1_title,
        artistResourceID = R.string.artwork_1_artist,
        yearResourceID = R.string.artwork_1_year,
    ),
    ArtworkIds(
        imageResourceID = R.drawable.artwork_2,
        titleResourceID = R.string.artwork_2_title,
        artistResourceID = R.string.artwork_2_artist,
        yearResourceID = R.string.artwork_2_year,
    )
)

@Composable
fun GalleryApp(artworks: List<ArtworkIds>, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Column {
            var index by remember { mutableIntStateOf(0) }

            Column(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.header),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    fontFamily = FontFamily.Cursive
                )
                Artwork(
                    imageResourceID = artworks[index].imageResourceID,
                    titleResourceID = artworks[index].titleResourceID,
                    artistResourceID = artworks[index].artistResourceID,
                    yearResourceID = artworks[index].yearResourceID,
                )
            }
            NavigateFooter(
                onPreviousClick = { index = 0},
                onNextClick = { index = 1},
                Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun Artwork(
    imageResourceID: Int,
    titleResourceID: Int,
    artistResourceID: Int,
    yearResourceID: Int,
) {
    ArtworkImage(
        imageResourceID = imageResourceID,
        modifier = Modifier.fillMaxWidth()
    )
    ArtworkDescription(
        titleResourceID = titleResourceID,
        artistResourceID = artistResourceID,
        yearResourceID = yearResourceID,
    )
}

@Composable
fun ArtworkImage(imageResourceID: Int, modifier: Modifier = Modifier) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.outline
            )
        ) {
            Image(
                painter = painterResource(imageResourceID),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .requiredWidth(360.dp)
                    .requiredHeight(516.dp)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun ArtworkDescription(
    titleResourceID: Int,
    artistResourceID: Int,
    yearResourceID: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(titleResourceID),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )
        Row {
            Text(
                text = stringResource(artistResourceID),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " (${stringResource(yearResourceID)})",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun NavigateFooter(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
    ) {
        NavigateButton(
            text = stringResource(R.string.previous),
            modifier = Modifier.weight(1f),
            onClick = onPreviousClick
        )
        NavigateButton(
            text = stringResource(R.string.next),
            modifier = Modifier.weight(1f),
            onClick = onNextClick
        )
    }
}

@Composable
fun NavigateButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
        ) {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryAppPreview() {
    GalleryTheme {
        GalleryApp(artworks)
    }
}