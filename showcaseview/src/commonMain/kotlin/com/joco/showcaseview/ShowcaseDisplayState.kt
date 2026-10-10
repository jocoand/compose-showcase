package com.joco.showcaseview

/**
 * Interface that represents the display state of the Showcase.

 * `Appeared` indicates that the Showcase has fully appeared on the screen.
 * `Disappeared` indicates that the Showcase has fully disappeared from the screen.
 */
sealed interface ShowcaseDisplayState {
    data object Appeared : ShowcaseDisplayState
    data object Disappeared : ShowcaseDisplayState
}