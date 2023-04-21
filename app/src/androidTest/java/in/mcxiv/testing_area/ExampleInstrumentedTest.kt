package `in`.mcxiv.testing_area

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.text.NumberFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val cTR = createComposeRule()


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("in.mcxiv.testing_area", appContext.packageName)
    }

    fun myFirstTest() {
        cTR.setContent {
            TESTING_AREATheme {
                TipCalculatorScreen()
            }
        }
        cTR.onNodeWithText("Bill Amount").performTextInput("10")
        cTR.onNodeWithText("Tip (%)").performTextInput("20")
        val expectedTip = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(2)
        cTR.onNodeWithText("Tip Amount: $expectedTip").assertExists("No node with this text was found.")
    }
}