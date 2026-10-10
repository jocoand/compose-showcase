package com.joco.showcaseview

/**
 * A sealed interface that represents the position of a showcase dialog relative to a target element.
 * It has three possible values:
 * - `Top`: The showcase dialog should be positioned above the target element.
 * - `Bottom`: The showcase dialog should be positioned below the target element.
 * - `Default`: The showcase dialog's position is determined based on the location of the target element on the screen.
 *    If the target element is in the upper half of the screen, the showcase dialog will be positioned below it, and vice versa.
 */
sealed interface ShowcasePosition {
    data object Top : ShowcasePosition
    data object Bottom : ShowcasePosition
    data object Default : ShowcasePosition
}