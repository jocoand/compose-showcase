package com.joco.showcaseview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.joco.showcaseview.highlight.HighlightProperties
import com.joco.showcaseview.highlight.ShowcaseHighlight
import kotlin.math.roundToInt

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
    // Prevent crash if coordinates are not attached
    if (!targetCoordinates.isAttached) {
        println("ShowcaseView: Target coordinates are not attached, skipping showcase")
        return
    }

    val transition =  remember { MutableTransitionState(false) }
    val highlightDrawer = highlight.create(targetCoordinates = targetCoordinates)

    AnimatedVisibility(
        visibleState = transition,
        enter = fadeIn(tween(animationDuration.enterMillis)),
        exit = fadeOut(tween(animationDuration.exitMillis))
    ) {
        Box {
            ShowcaseBackground(
                highlightProperties = highlightDrawer,
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
 * Draws the background overlay and cuts out the highlight around the target element using an EvenOdd Path.
 *
 * @param highlightProperties the properties of the highlight containing cutout geometry.
 * @param backgroundAlpha the alpha value of the background overlay.
 */
@Composable
private fun ShowcaseBackground(
    highlightProperties: HighlightProperties,
    backgroundAlpha: BackgroundAlpha
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                val path = Path().apply {
                    fillType = PathFillType.EvenOdd
                    addRect(Rect(Offset.Zero, size))
                    highlightProperties.addCutoutToPath(this)
                }
                onDrawBehind {
                    drawPath(
                        path = path,
                        color = Color.Black.copy(alpha = backgroundAlpha.value)
                    )
                }
            }
    )
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
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val screenHeight = windowInfo.containerSize.height.toFloat()
    val screenWidth = windowInfo.containerSize.width.toFloat()

    val verticalSpacerPx = with(density) { 16.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(minWidth = 0, minHeight = 0)
                )
                val dialogHeight = placeable.height
                val dialogWidth = placeable.width
                val highlightCenterX = highlightBounds.center.x

                val offsetX = when (alignment) {
                    ShowcaseAlignment.Start -> highlightBounds.left
                    ShowcaseAlignment.End -> highlightBounds.right - dialogWidth
                    ShowcaseAlignment.CenterHorizontal -> (highlightCenterX - dialogWidth / 2f)
                    ShowcaseAlignment.Default -> {
                        if (highlightCenterX > screenWidth / 2f) {
                            highlightBounds.right - dialogWidth
                        } else {
                            highlightBounds.left
                        }
                    }
                }

                val offsetY = when (position) {
                    ShowcasePosition.Top -> highlightBounds.top - verticalSpacerPx - dialogHeight
                    ShowcasePosition.Bottom -> highlightBounds.bottom + verticalSpacerPx
                    ShowcasePosition.Default -> {
                        if (targetRect.center.y > screenHeight / 2f + verticalSpacerPx) {
                            highlightBounds.top - verticalSpacerPx - dialogHeight
                        } else {
                            highlightBounds.bottom + verticalSpacerPx
                        }
                    }
                }

                val clampedX = offsetX.coerceIn(0f, (screenWidth - dialogWidth).coerceAtLeast(0f))
                val clampedY = offsetY.coerceIn(0f, (screenHeight - dialogHeight).coerceAtLeast(0f))

                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeable.place(clampedX.roundToInt(), clampedY.roundToInt())
                }
            }
    ) {
        content(highlightBounds)
    }
}
