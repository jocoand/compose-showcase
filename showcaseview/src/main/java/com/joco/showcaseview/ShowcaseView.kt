package com.joco.showcaseview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.joco.showcaseview.highlight.ShowcaseHighlight

/**
 * Displays a dialog with a background overlay.
 * *
 * @param visible determines if the Showcase is visible or not.
 * @param targetCoordinates the coordinates of the target element that the Showcase is highlighting.
 * @param position the position of the dialog relative to the target element.
 * @param alignment the alignment of the dialog relative to the target element.
 * @param animationDuration the duration of the fade in and fade out animation.
 * @param onDisplayStateChanged: callback function that is invoked when the display state of the Showcase changes.
 * @param highlight the highlight around the target element.
 * @param backgroundAlpha the alpha value of the background overlay.
 * @param dialog the content of the dialog.
 */
@Composable
fun ShowcaseView(
    visible: Boolean,
    targetCoordinates: LayoutCoordinates,
    position: ShowcasePosition = ShowcasePosition.Default,
    alignment: ShowcaseAlignment = ShowcaseAlignment.Default,
    animationDuration: AnimationDuration = AnimationDuration.Default,
    onDisplayStateChanged: (ShowcaseDisplayState) -> Unit = {},
    highlight: ShowcaseHighlight = ShowcaseHighlight.Rectangular(),
    backgroundAlpha: BackgroundAlpha = BackgroundAlpha.Normal,
    dialog: @Composable (Rect) -> Unit
) {
    val transition =  remember { MutableTransitionState(false) }
    val highlightDrawer = highlight.create(targetCoordinates = targetCoordinates)

    AnimatedVisibility(
        visibleState = transition,
        enter = fadeIn(tween(animationDuration.enterMillis)),
        exit = fadeOut(tween(animationDuration.exitMillis))
    ) {
        Box {
            ShowcaseBackground(
                coordinates = targetCoordinates,
                drawHighlight = highlightDrawer.drawHighlight,
                backgroundAlpha = backgroundAlpha
            )
            ShowcaseDialog(
                targetRect = targetCoordinates.boundsInRoot(),
                position = position,
                alignment = alignment,
                highlightBounds = highlightDrawer.highlightBounds,
                content = dialog
            )
        }
    }
    LaunchedEffect(key1 = visible) {
        transition.targetState = visible
    }
    LaunchedEffect(key1 = transition.isIdle) {
        if (transition.isIdle) {
            if (transition.targetState) {
                onDisplayStateChanged(ShowcaseDisplayState.Appeared)
            } else {
                onDisplayStateChanged(ShowcaseDisplayState.Disappeared)
            }
        }
    }
}

/**
 * Draws the background overlay and the highlight around the target element.
 *
 * @param coordinates the coordinates of the target element that the Showcase is highlighting.
 * @param drawHighlight draws the highlight around the target element.
 */
@Composable
private fun ShowcaseBackground(
    coordinates: LayoutCoordinates,
    backgroundAlpha: BackgroundAlpha,
    drawHighlight: DrawScope.(LayoutCoordinates) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = backgroundAlpha.value)
    ) {
        // Overlay
        drawRect(
            Color.Black.copy(alpha = backgroundAlpha.value),
            size = Size(size.width, size.height)
        )
        drawHighlight(coordinates)
    }
}

/**
 * A Composable function that positions and displays the dialog.
 *
 * @param targetRect te bounding rectangle of the target element.
 * @param position the position of the dialog relative to the target element.
 * @param alignment the alignment of the dialog relative to the target element.
 * @param highlightBounds the bounding rectangle of the highlight.
 * @param content the content of the dialog.
 */
@Composable
private fun ShowcaseDialog(
    targetRect: Rect,
    position: ShowcasePosition,
    alignment: ShowcaseAlignment,
    highlightBounds: Rect,
    content: @Composable (Rect) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenHeight = with(density) {
        configuration.screenHeightDp.dp.toPx()
    }
    val screenWidth = with(density) {
        configuration.screenWidthDp.dp.toPx()
    }

    val verticalSpacerPx = with(density) { 16.dp.toPx() }

    Box(
        modifier = Modifier
            .offset(x = offsetX.toDp(), y = offsetY.toDp())
            .onGloballyPositioned {
                val dialogHeight = it.size.height
                val dialogWidth = it.size.width
                val highlightCenterX = highlightBounds.center.x

                offsetX = when (alignment) {
                    ShowcaseAlignment.Start -> highlightBounds.left
                    ShowcaseAlignment.End -> highlightBounds.right - dialogWidth
                    ShowcaseAlignment.CenterHorizontal -> (highlightCenterX - dialogWidth / 2)
                    ShowcaseAlignment.Default -> {
                        if (highlightCenterX > screenWidth / 2) {
                            highlightBounds.right - dialogWidth
                        } else {
                            highlightBounds.left
                        }
                    }
                }

                offsetY = when (position) {
                    ShowcasePosition.Top -> highlightBounds.top - verticalSpacerPx - dialogHeight
                    ShowcasePosition.Bottom -> highlightBounds.bottom + verticalSpacerPx
                    ShowcasePosition.Default -> {
                        if (targetRect.center.y > screenHeight / 2 + verticalSpacerPx) {
                            highlightBounds.top - verticalSpacerPx - dialogHeight
                        } else {
                            highlightBounds.bottom + verticalSpacerPx
                        }
                    }
                }
            }
    ) {
        content(highlightBounds)
    }
}

@Composable
fun Float.toDp() = with(LocalDensity.current) {
    toDp()
}