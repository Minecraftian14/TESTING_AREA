package `in`.mcxiv.testing_area

import org.junit.Test

import org.junit.Assert.*
import java.text.NumberFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun calculate_20_percent_tip_no_roundup() {
        val amount = 10.00f
        val tipPercent = 20.00f
        val expectedTip = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(2)
        val actualTip = calculateTip(amount = amount, tipPercent = tipPercent, false)
        assertEquals(expectedTip, actualTip)
    }
}