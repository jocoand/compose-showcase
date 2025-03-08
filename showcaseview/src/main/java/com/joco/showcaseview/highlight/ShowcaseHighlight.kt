package com.joco.showcaseview.highlight

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface ShowcaseHighlight {
    /**
     * Create a HighlightProperties object which contains the details of the highlight.
     */
    @Composable
    fun create(targetCoordinates: LayoutCoordinates): HighlightProperties

    /**
     * Class to create a rectangular highlight around the target element.
     * @property cornerRadius The corner radius of the rounded rectangle.
     */
    data class Rectangular(val cornerRadius: Dp = 8.dp) : ShowcaseHighlight {

        @Composable
        override fun create(
            targetCoordinates: LayoutCoordinates
        ): HighlightProperties {
            val highlightBounds = createHighlightBounds(
                targetCoordinates.boundsInRoot(),
                with(LocalDensity.current) { 8.dp.toPx() }
            )
            return HighlightProperties(
                drawHighlight = { rectangularHighlight(cornerRadius.toPx(), highlightBounds) },
                highlightBounds = highlightBounds
            )
        }

        /**
         * Draws a rounded rectangle highlight around the target element.
         *
         * @param cornerRadius The corner radius of the rounded rectangle.
         * @param highlightBounds The bounds of the highlight around the target element.
         */
        private fun DrawScope.rectangularHighlight(
            cornerRadius: Float,
            highlightBounds: Rect
        ) {
            drawRoundRect(
                color = Color.White,
                size = highlightBounds.size,
                topLeft = highlightBounds.topLeft,
                blendMode = BlendMode.Clear,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }

        private fun createHighlightBounds(
            targetRect: Rect,
            targetMargin: Float
        ): Rect {
            return Rect(
                top = targetRect.top - targetMargin,
                bottom = targetRect.bottom + targetMargin,
                left = targetRect.left - targetMargin,
                right = targetRect.right + targetMargin
            )
        }
    }

    /**
     * Data object is used to create a circular highlight around the target element.
     */
    data class Circular(val targetMargin: Dp = 4.dp) : ShowcaseHighlight {

        @Composable
        override fun create(targetCoordinates: LayoutCoordinates): HighlightProperties {
            val targetMargin = with(LocalDensity.current) { targetMargin.toPx() }
            return HighlightProperties(
                drawHighlight = { circularHighlight(it, targetMargin) },
                highlightBounds = createHighlightBounds(
                    targetCoordinates.boundsInRoot(),
                    targetMargin = targetMargin
                )
            )
        }

        /**
         * Draws a circular highlight around the target element.
         *
         * @param coordinates The coordinates of the target element.
         * @param targetMargin The margin around the target element.
         */
        private fun DrawScope.circularHighlight(
            coordinates: LayoutCoordinates,
            targetMargin: Float
        ) {
            val targetRect = coordinates.boundsInRoot()
            val xOffset = targetRect.topLeft.x
            val yOffset = targetRect.topLeft.y
            val rectSize = coordinates.boundsInParent().size
            val radius = if (rectSize.width > rectSize.height) {
                rectSize.width / 2
            } else {
                rectSize.height / 2
            }
            drawCircle(
                color = Color.White,
                radius = radius + targetMargin,
                center = Offset(xOffset + rectSize.width / 2, yOffset + rectSize.height / 2),
                blendMode = BlendMode.Clear
            )
        }

        private fun createHighlightBounds(targetRect: Rect, targetMargin: Float): Rect {
            val radius = if (targetRect.width > targetRect.height) {
                targetRect.width / 2
            } else {
                targetRect.height / 2
            }

            return Rect(
                top = targetRect.center.y - radius - targetMargin,
                bottom = targetRect.center.y + radius + targetMargin,
                left = targetRect.center.x - radius - targetMargin,
                right = targetRect.center.x + radius + targetMargin
            )
        }
    }
}