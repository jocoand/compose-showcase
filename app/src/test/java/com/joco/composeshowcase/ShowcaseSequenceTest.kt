package com.joco.composeshowcase

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.joco.showcase.sequence.SequenceShowcase
import com.joco.showcase.sequence.rememberSequenceShowcaseState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(qualifiers = RobolectricDeviceQualifiers.PixelXL)
class ShowcaseSequenceTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_launch_showcaseSequence() {
        val buttonNice = composeRule.onNodeWithText(getString(R.string.button_nice))
        val buttonAwesome = composeRule.onNodeWithText(getString(R.string.button_awesome))
        val textGreetings = composeRule.onNodeWithText(getString(R.string.label_greetings, "Andy"))

        composeRule.mainClock.autoAdvance = false

        composeRule.setContent {
            val sequenceShowcaseState = rememberSequenceShowcaseState()
            SequenceShowcase(state = sequenceShowcaseState) {
                MainContent(
                    PaddingValues(16.dp),
                    rememberScrollState(),
                    sequenceShowcaseState
                )
            }
        }

        // Initial
        capture()

        // 1st showcase
        textGreetings.performClick()
        advanceTime()
        capture()

        // 2nd showcase
        buttonNice.performClick()
        advanceTime()
        capture()

        // 3rd showcase
        buttonNice.performClick()
        advanceTime()
        capture()

        // 4th showcase
        buttonNice.performClick()
        advanceTime()
        capture()

        // Close
        buttonAwesome.performClick()
        advanceTime()
        capture()
    }

    private fun advanceTime() {
        composeRule.mainClock.advanceTimeBy(MILLIS * 2)
    }

    private fun capture() {
        composeRule
            .onRoot()
            .captureRoboImage()
    }

    private fun getString(resId: Int, vararg formatArgs: Any): String {
        return composeRule.activity.getString(resId, *formatArgs)
    }

    companion object {
        const val MILLIS = 700L
    }
}
