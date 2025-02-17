package com.joco.showcase.sequence

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.LayoutCoordinates
import com.joco.compose_showcaseview.ShowcaseAlignment
import com.joco.compose_showcaseview.ShowcaseDuration
import com.joco.compose_showcaseview.ShowcasePosition
import com.joco.compose_showcaseview.highlight.ShowcaseHighlight

@Composable
fun rememberSequenceShowcaseState(): SequenceShowcaseState {
    return remember {
        SequenceShowcaseState()
    }
}

/**
 * Manages the targets of the showcase and the current target index.
 * It also controls the visibility of the showcase.
 */
class SequenceShowcaseState {
    internal var targets = mutableStateMapOf<Int, SequenceShowcaseTarget>()

    var currentTargetIndex by mutableIntStateOf(0)
        private set
    val currentTarget: SequenceShowcaseTarget?
        get() = targets[currentTargetIndex]

    var showCaseVisible by mutableStateOf(false)
        private set

    private var isTransitioning = false

    /**
     * Start the sequence showcase.
     *
     * @param index The index of the target to start with.
     */
    fun start(index: Int = 0) {
        currentTargetIndex = index
        showCaseVisible = true
    }

    /**
     * Move to the next target in the sequence showcase.
     */
    fun next() {
        isTransitioning = true
        showCaseVisible = false
    }

    /**
     * Dismiss the sequence showcase.
     */
    fun dismiss() {
        showCaseVisible = false
        isTransitioning = false
    }

    internal fun onShowcaseViewAppear() {
        if (isTransitioning) {
            isTransitioning = false
        }
    }

    internal fun onShowcaseViewDisappear() {
        if (isTransitioning) {
            currentTargetIndex++
            showCaseVisible = currentTarget != null
        }
    }
}

data class SequenceShowcaseTarget(
    val index: Int,
    val coordinates: LayoutCoordinates,
    val position: ShowcasePosition,
    val alignment: ShowcaseAlignment,
    val highlight: ShowcaseHighlight,
    val duration: ShowcaseDuration,
    val content: @Composable () -> Unit
)