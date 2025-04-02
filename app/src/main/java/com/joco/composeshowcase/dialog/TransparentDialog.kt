package com.joco.composeshowcase.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TransparentDialog(title: String, text: String, alignment: Alignment.Horizontal = Alignment.Start) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = title, color = Color.White, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransparentDialog() {
    Box(Modifier.background(Color.Black)) {
        TransparentDialog(
            title = "Preview Title",
            text = "This is a preview of the TransparentDialog."
        )
    }
}