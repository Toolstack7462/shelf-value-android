package com.shelfvalue.beta

import android.graphics.Bitmap
import android.view.View
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File

class ShelfScreenTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    private val instrumentation get() = InstrumentationRegistry.getInstrumentation()
    private val args get() = InstrumentationRegistry.getArguments()
    private val config get() = compose.activity.resources.configuration

    private fun capture(name: String) {
        compose.waitForIdle()
        val folder = File(checkNotNull(compose.activity.getExternalFilesDir(null)), "ui-checks")
        check(folder.isDirectory || folder.mkdirs())
        val image = checkNotNull(instrumentation.uiAutomation.takeScreenshot())
        val output = File(folder, "${config.locales[0].language}-${config.fontScale}-$name.png")
        output.outputStream().use { check(image.compress(Bitmap.CompressFormat.PNG, 100, it)) }
        image.recycle()
    }

    private fun enter(tag: String, text: String) {
        compose.onNodeWithTag(tag).performScrollTo().assertIsDisplayed()
            .assert(hasSetTextAction()).performTextReplacement(text)
    }

    @Test fun localizedControlsAndValidation() {
        val language = config.locales[0].language
        args.getString("expected_language")?.let { assertEquals(it, language) }
        args.getString("expected_font")?.let { assertEquals(it.toFloat(), config.fontScale, 0.01f) }
        val rtl = language == "ur"
        assertEquals(if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR, config.layoutDirection)
        compose.onNodeWithText(compose.activity.getString(R.string.app_name)).assertIsDisplayed()
        capture("start")
        val compare = compose.onNodeWithTag("compare").performScrollTo().assertIsDisplayed().assertHasClickAction()
        val reset = compose.onNodeWithTag("reset").assertIsDisplayed().assertHasClickAction()
        val c = compare.fetchSemanticsNode().boundsInRoot
        val r = reset.fetchSemanticsNode().boundsInRoot
        assertTrue("Both controls must have width", c.width > 0 && r.width > 0)
        assertTrue("Action order must follow layout direction", if (rtl) r.right <= c.left else c.right <= r.left)
        compare.performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.invalid)).performScrollTo().assertIsDisplayed()
        compose.onNodeWithTag("result").assert(SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Polite))
    }

    @Test fun comparesMultipacksAndResetsEveryField() {
        val urdu = config.locales[0].language == "ur"
        enter("price_A", if (urdu) "۳۲۰" else "320")
        enter("quantity_A", if (urdu) "۵۰۰" else "500")
        enter("packs_A", if (urdu) "۲" else "2")
        enter("price_B", "500")
        enter("quantity_B", "1")
        compose.onNodeWithTag("unit_B").performScrollTo().performClick()
        val kg = compose.activity.getString(R.string.kilogram)
        compose.onNodeWithText(kg).performClick()
        compose.onNodeWithTag("compare").performScrollTo().performClick()
        val winner = compose.activity.getString(R.string.winner_a)
        compose.onNodeWithText(winner).performScrollTo().assertIsDisplayed()
        val saving = compose.activity.getString(R.string.savings, "Rs", "180.00", kg, "36.0")
        compose.onNodeWithText(saving).performScrollTo().assertIsDisplayed()
        capture("result")
        compose.onNodeWithTag("reset").performScrollTo().performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.prompt)).performScrollTo().assertIsDisplayed()
        compose.onNodeWithText(winner).assertDoesNotExist()
        for (tag in listOf("price_A", "quantity_A", "price_B", "quantity_B")) {
            assertEquals("", compose.onNodeWithTag(tag).fetchSemanticsNode().config[SemanticsProperties.EditableText].text)
        }
        for (tag in listOf("packs_A", "packs_B")) {
            assertEquals("1", compose.onNodeWithTag(tag).fetchSemanticsNode().config[SemanticsProperties.EditableText].text)
        }
        for (tag in listOf("unit_A", "unit_B")) {
            compose.onNodeWithTag(tag).assertTextContains(compose.activity.getString(R.string.gram), substring = true)
        }
        compose.onNodeWithTag("currency").assertTextContains("Rs")
    }
}
