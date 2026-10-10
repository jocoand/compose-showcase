package com.joco.showcaseview

/**
 * A sealed interface defines the alignment of the dialog relative to the target element in the Showcase.
 * It has four possible values:
 * - `Start`: Aligns the dialog to the start of the target element.
 * - `End`: Aligns the dialog to the end of the target element.
 * - `CenterHorizontal`: Centers the dialog horizontally relative to the target element.
 * - `Default`: Chooses the alignment based on the position of the target element on the screen.
 *    If the target element is in the right half of the screen, it aligns the dialog to the end of the target element,
 *    otherwise it aligns to the start.
 */
sealed interface ShowcaseAlignment {
    data object Start : ShowcaseAlignment
    data object End : ShowcaseAlignment
    data object CenterHorizontal : ShowcaseAlignment
    data object Default : ShowcaseAlignment
}