package com.joco.showcase.sequence

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import com.joco.showcaseview.AnimationDuration
import com.joco.showcaseview.BackgroundAlpha
import com.joco.showcaseview.ShowcaseAlignment
import com.joco.showcaseview.ShowcaseDisplayState
import com.joco.showcaseview.ShowcasePosition
import com.joco.showcaseview.ShowcaseView
import com.joco.showcaseview.highlight.ShowcaseHighlight

@Composable
fun SequenceShowcase(
    state: SequenceShowcaseState = rememberSequenceShowcaseState(),
    content: @Composable SequenceShowcaseScope.() -> Unit
) {
    val scope = remember(state) { SequenceShowcaseScope(state) }

    Box(modifier = Modifier.fillMaxWidth()) {
        scope.content()

        state.currentTarget?.let { target ->
            ShowcaseView(
                visible = state.showCaseVisible,
                targetCoordinates = target.coordinates,
                position = target.position,
                alignment = target.alignment,
                highlight = target.highlight,
                animationDuration = target.duration,
                backgroundAlpha = target.backgroundAlpha,
                onDisplayStateChanged = { displayState ->
                    when(displayState) {
                        ShowcaseDisplayState.Appeared -> {
                            state.onShowcaseViewAppear()
                        }
                        ShowcaseDisplayState.Disappeared -> {
                            state.onShowcaseViewDisappear()
                        }
                    }
                }
            ) { targetRect ->
                target.content(targetRect)
            }
        }
    }
}

/**
 * Provides a function to create a Modifier that marks a Composable as a target for the SequenceShowcase.
 */
class SequenceShowcaseScope(
    private val state: SequenceShowcaseState,
) {
    /**
     * Creates a Modifier that marks a Composable as a target for the SequenceShowcase.
     *
     * @param index The index of the target in the sequence.
     * @param position The position of the dialog relative to the target element.
     * @param alignment The alignment of the dialog relative to the target element.
     * @param highlight The highlight around the target element.
     * @param animationDuration The duration of the fade enter and exit animation.
     * @param backgroundAlpha The alpha value of the background overlay.
     * @param content The content of the dialog.
     */
    fun Modifier.sequenceShowcaseTarget(
        index: Int,
        position: ShowcasePosition = ShowcasePosition.Default,
        alignment: ShowcaseAlignment = ShowcaseAlignment.Default,
        highlight: ShowcaseHighlight = ShowcaseHighlight.Rectangular(),
        animationDuration: AnimationDuration = AnimationDuration.Default,
        backgroundAlpha: BackgroundAlpha = BackgroundAlpha.Normal,
        content: @Composable (Rect) -> Unit,
    ): Modifier = onGloballyPositioned { coordinates ->
        state.targets[index] = SequenceShowcaseTarget(
            index = index,
            coordinates = coordinates,
            position = position,
            alignment = alignment,
            highlight = highlight,
            duration = animationDuration,
            backgroundAlpha = backgroundAlpha,
            content = content
        )
    }
}