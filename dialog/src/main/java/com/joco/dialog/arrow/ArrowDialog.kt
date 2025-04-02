package com.joco.dialog.arrow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable that displays a dialog with arrow.
 *
 * @param targetRect The rectangle of the target element that the arrow will point to.
 * @param modifier The modifier to be applied to the layout.
 * @param pointerColor The color of the pointer that points to the target element.
 * @param pointerSize The size of the pointer.
 * @param content The content to be displayed inside the dialog. A [Color] parameter
 * to allow the content to adapt to the pointer's color.
 *
 * Note currently support only vertical arrow.
 */
@Composable
fun ArrowDialog(
    targetRect: Rect,
    modifier: Modifier = Modifier,
    pointerColor: Color = ArrowDialogDefaults.pointerColor,
    pointerSize: Dp = ArrowDialogDefaults.pointerSize,
    content: @Composable (Color) -> Unit
) {
    val dialogOffsetX = remember { mutableIntStateOf(0) }
    val dialogOffsetY = remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInRoot().let { rect ->
                    dialogOffsetX.intValue = rect.left.toInt()
                    dialogOffsetY.intValue = rect.top.toInt()
                }
            }
    ) {
        val yAlignment = with(Alignment) {
            if (dialogOffsetY.intValue < targetRect.center.y) BottomStart else TopStart
        }
        val half = pointerSize / 2

        val pointerOffsetX = (targetRect.center.x - dialogOffsetX.intValue).toDp() - half
        Box(
            modifier = Modifier
                .align(yAlignment)
                .offset(x = pointerOffsetX)
                .size(pointerSize)
                .rotate(45f)
                .background(pointerColor)
        )

        val offsetY = if (yAlignment == Alignment.BottomStart) -half else half
        Box(
            modifier = Modifier
                .offset(y = offsetY)
        ) {
            content(pointerColor)
        }
    }
}

@Composable
private fun Float.toDp() = with(LocalDensity.current) {
    toDp()
}

object ArrowDialogDefaults {

    val pointerSize = 22.dp

    val pointerColor = Color.White
}