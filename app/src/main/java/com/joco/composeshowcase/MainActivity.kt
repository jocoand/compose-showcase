package com.joco.composeshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joco.compose_showcaseview.ShowcaseAlignment
import com.joco.composeshowcase.ui.theme.ComposeShowcaseTheme
import com.joco.showcase.sequence.SequenceShowcase
import com.joco.showcase.sequence.rememberSequenceShowcaseState


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
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
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
                                        .align(Alignment.End)
                                        .sequenceShowcaseTarget(
                                            index = 0,
                                            content = {
                                                MyShowcaseDialog(
                                                    text = "Hey, this is Greetings showcase",
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
                                            content = {
                                                MyShowcaseDialog(
                                                    text = "Hey, this is Article show case",
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
                                                alignment = ShowcaseAlignment.CenterHorizontal,
                                                content = {
                                                    MyShowcaseDialog(
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
                                                alignment = ShowcaseAlignment.CenterHorizontal,
                                                content = {
                                                    MyShowcaseDialog(
                                                        text = "You also can share the article!",
                                                        onClick = { sequenceShowcaseState.next() }
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
                }
            }
        }
    }

    @Composable
    fun Greeting(modifier: Modifier = Modifier, name: String, onClick: () -> Unit) {
        Text(
            text = "Hello, $name!",
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
}