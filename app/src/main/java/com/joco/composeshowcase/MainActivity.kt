package com.joco.composeshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joco.composeshowcase.dialog.MyArrowDialog
import com.joco.composeshowcase.dialog.SkeletonArrowDialog
import com.joco.composeshowcase.dialog.TransparentDialog
import com.joco.composeshowcase.ui.theme.ComposeShowcaseTheme
import com.joco.showcase.sequence.SequenceShowcase
import com.joco.showcase.sequence.SequenceShowcaseScope
import com.joco.showcase.sequence.SequenceShowcaseState
import com.joco.showcase.sequence.rememberSequenceShowcaseState
import com.joco.showcaseview.AnimationDuration
import com.joco.showcaseview.BackgroundAlpha
import com.joco.showcaseview.ShowcaseAlignment
import com.joco.showcaseview.ShowcasePosition
import com.joco.showcaseview.highlight.ShowcaseHighlight


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShowcaseTheme {
                val sequenceShowcaseState = rememberSequenceShowcaseState()
                val columnScrollState = rememberScrollState()

                SequenceShowcase(state = sequenceShowcaseState) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Showcase App") },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = Color.White
                                ),
                            )
                        }
                    ) { innerPadding ->
                        MainContent(innerPadding, columnScrollState, sequenceShowcaseState)
                    }
                }
            }
        }
    }

    companion object {
        const val SHOWCASE_2_DURATION = 900
        const val SHOWCASE_3_DURATION = 300
    }
}

@Composable
fun SequenceShowcaseScope.MainContent(
    innerPadding: PaddingValues,
    columnScrollState: ScrollState,
    sequenceShowcaseState: SequenceShowcaseState
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(if (sequenceShowcaseState.currentTargetIndex == 3) {
                Modifier.clickable { sequenceShowcaseState.next() }
            } else {
                Modifier
            })
            .padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        val loremIpsumText = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
    """
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(columnScrollState)
        ) {
            Greeting(
                modifier = Modifier
                    .sequenceShowcaseTarget(
                        index = 0,
                        content = {
                            MyShowcaseDialog(
                                text = "Hello Andy, Welcome abroad! ",
                                onClick = { sequenceShowcaseState.next() }
                            )
                        }
                    ),
                name = "Andy",
                onClick = { sequenceShowcaseState.start() }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Article(
                modifier = Modifier
                    .align(Alignment.Start)
                    .sequenceShowcaseTarget(
                        index = 1,
                        highlight = ShowcaseHighlight.Rectangular(24.dp),
                        animationDuration = AnimationDuration.create(
                            MainActivity.SHOWCASE_2_DURATION,
                            MainActivity.SHOWCASE_2_DURATION
                        ),
                        content = {
                            MyArrowDialog(
                                targetRect = it,
                                text = "You can read cool articles here!",
                                onClick = { sequenceShowcaseState.next() }
                            )
                        }
                    ),
                subheading = "Today's Article",
                paragraph = loremIpsumText
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                LikeButton(
                    modifier = Modifier
                        .sequenceShowcaseTarget(
                            index = 2,
                            position = ShowcasePosition.Top,
                            highlight = ShowcaseHighlight.Circular(targetMargin = 12.dp),
                            animationDuration = AnimationDuration.create(
                                MainActivity.SHOWCASE_3_DURATION,
                                MainActivity.SHOWCASE_3_DURATION
                            ),
                            alignment = ShowcaseAlignment.CenterHorizontal,
                            content = {
                                SkeletonArrowDialog(
                                    targetRect = it,
                                    pointerSize = 28.dp,
                                    text = "Click here if you love the article!",
                                    onClick = { sequenceShowcaseState.next() }
                                )
                            }
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                ShareButton(
                    modifier = Modifier
                        .sequenceShowcaseTarget(
                            index = 3,
                            position = ShowcasePosition.Bottom,
                            highlight = ShowcaseHighlight.Rectangular(0.dp),
                            alignment = ShowcaseAlignment.CenterHorizontal,
                            backgroundAlpha = BackgroundAlpha.Dark,
                            content = {
                                TransparentDialog(
                                    title = "Share Article",
                                    text = "You also can share the article!",
                                    alignment = Alignment.End
                                )
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Article(
                modifier = Modifier
                    .align(Alignment.Start),
                subheading = "Today's Article 2",
                paragraph = loremIpsumText
            )
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, name: String, onClick: () -> Unit) {
    Text(
        text = stringResource(id = R.string.label_greetings, name),
        modifier = modifier.clickable { onClick() },
    )
}

@Composable
fun Article(
    modifier: Modifier = Modifier,
    subheading: String,
    paragraph: String
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = subheading,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.W500
        )
        Text(
            text = paragraph,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun LikeButton(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Like Icon",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Love It!",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ShareButton(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share Icon",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Share",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}