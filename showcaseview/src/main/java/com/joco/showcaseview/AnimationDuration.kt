package com.joco.showcaseview

/**
 * Class that represents the duration of animations in the `ShowcaseView`.
 *
 * @property enterMillis The duration of the enter animation in milliseconds.
 * @property exitMillis The duration of the exit animation in milliseconds.
 */
class AnimationDuration private constructor(val enterMillis: Int, val exitMillis: Int) {

    companion object {
        private const val DEFAULT_MILLIS = 700

        /**
         * Creates a `AnimationDuration` with the default duration for both enter and exit animations.
         */
        val Default = AnimationDuration(DEFAULT_MILLIS, DEFAULT_MILLIS)

        /**
         * Creates a `AnimationDuration` with the same duration for both enter and exit animations.
         *
         * @param durationMillis Animations duration in milliseconds.
         */
        fun create(durationMillis: Int) = AnimationDuration(durationMillis, durationMillis)

        /**
         * Creates a `AnimationDuration` with different durations for enter and exit animations.
         *
         * @param enterMillis The enter animation duration in milliseconds.
         * @param exitMillis The exit animation duration in milliseconds.
         */
        fun create(enterMillis: Int, exitMillis: Int) = AnimationDuration(enterMillis, exitMillis)
    }
}