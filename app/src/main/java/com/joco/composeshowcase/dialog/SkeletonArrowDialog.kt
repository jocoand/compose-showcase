package com.joco.composeshowcase.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joco.composeshowcase.R
import com.joco.dialog.arrow.ArrowDialog

@Composable
fun SkeletonArrowDialog(targetRect: Rect, pointerSize: Dp, text: String, onClick: () -> Unit) {
    ArrowDialog(
        targetRect = targetRect,
        pointerSize = pointerSize,
        pointerColor = Color.White,
        content = {
            InnerDialog(text, onClick)
        }
    )
}

@Composable
private fun InnerDialog(
    text: String,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Red)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier,
            shape = RoundedCornerShape(24.dp),
            onClick = onClick
        ) {
            Text(
                stringResource(R.string.button_awesome),
                color = Color.White
            )
        }
    }
}