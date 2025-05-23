package com.joco.composeshowcase

import android.app.Application
import android.content.ComponentName
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ApplicationProvider
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.joco.showcase.sequence.SequenceShowcase
import com.joco.showcase.sequence.rememberSequenceShowcaseState
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(qualifiers = RobolectricDeviceQualifiers.PixelXL)
class ShowcaseSequenceTest {

    @get:Rule(order = 1)
    val addActivityToRobolectricRule = object : TestWatcher() {
        override fun starting(description: Description?) {
            super.starting(description)
            val appContext: Application = ApplicationProvider.getApplicationContext()
            Shadows.shadowOf(appContext.packageManager).addActivityIfNotPresent(
                ComponentName(
                    appContext.packageName,
                    ComponentActivity::class.java.name,
                )
            )
        }
    }

    @get:Rule(order = 2)
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
        advanceTime(DEFAULT_MILLIS)
        capture()

        // 2nd showcase
        buttonNice.performClick()
        advanceTime(DEFAULT_MILLIS + MainActivity.SHOWCASE_2_DURATION)
        capture()

        // 3rd showcase
        buttonNice.performClick()
        advanceTime(MainActivity.SHOWCASE_2_DURATION + MainActivity.SHOWCASE_3_DURATION.toLong())
        capture()

        // 4th showcase
        buttonAwesome.performClick()
        advanceTime(MainActivity.SHOWCASE_3_DURATION.toLong() + DEFAULT_MILLIS)
        capture()

        // Close
        composeRule.onRoot().performClick()
        advanceTime()
        capture()
    }

    private fun advanceTime(millis: Long = DEFAULT_MILLIS * 2) {
        composeRule.mainClock.advanceTimeBy(millis)
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
        const val DEFAULT_MILLIS = 700L
    }
}
