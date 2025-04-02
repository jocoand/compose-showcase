package com.joco.showcaseview

/**
 * Represent the alpha (transparency) level of a background.
 *
 * @property value The alpha value as a Float.
 */
@JvmInline
value class BackgroundAlpha(val value: Float) {
    companion object {
        /**
         * Normal alpha value for the background.
         */
        val Normal = BackgroundAlpha(0.6f)
        /**
         * Dark alpha value for the background.
         */
        val Dark = BackgroundAlpha(0.9f)
    }
}