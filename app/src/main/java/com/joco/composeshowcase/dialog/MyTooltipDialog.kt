package com.joco.composeshowcase.dialog

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.joco.composeshowcase.R
import com.joco.dialog.tooltip.TooltipDialog

@Composable
fun MyTooltipDialog(targetRect: Rect, text: String, onClick: () -> Unit) {
    TooltipDialog(
        targetRect = targetRect,
        pointerColor = Color.White,
        content = { color ->
            InnerDialog(text, color, onClick)
        }
    )
}

@Composable
private fun InnerDialog(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            shape = RoundedCornerShape(8.dp),
            onClick = onClick
        ) {
            Text(stringResource(R.string.button_nice))
        }
    }
}