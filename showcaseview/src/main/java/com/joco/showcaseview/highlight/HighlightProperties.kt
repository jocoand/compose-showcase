package com.joco.showcaseview.highlight

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.LayoutCoordinates

/**
 * Represents the highlight around the target element.
 *
 * @property drawHighlight A lambda function that draws the highlight around the target element.
 * @property highlightBounds The bounds of the highlight around the target element.
 */
class HighlightProperties internal constructor(
    val drawHighlight: DrawScope.(LayoutCoordinates) -> Unit,
    val highlightBounds: Rect
)