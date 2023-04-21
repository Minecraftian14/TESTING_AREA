package `in`.mcxiv.testing_area

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil

class TipCalculator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TESTING_AREATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Float, tipPercent: Float = 28f, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) tip = ceil(tip)
    return NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip) ?: "0"
}

@Composable
fun MyField(
    @StringRes nameId: Int,
    value: String,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        label = { Text(stringResource(nameId)) },
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun TipCalculatorScreen() {
    var amountInput by remember { mutableStateOf("0") }
    var tipRateInput by remember { mutableStateOf("28") }
    var roundUpInput by remember { mutableStateOf(false) }

    val amount = amountInput.toFloatOrNull() ?: 0f
    val tipRate = tipRateInput.toFloatOrNull() ?: 28f
    val tip = calculateTip(amount = amount, tipPercent = tipRate, roundUp = roundUpInput)

    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp
        )
        Spacer(Modifier.height(16.dp))
        MyField(
            R.string.bill_amount, amountInput, ImeAction.Next,
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) })
        ) { amountInput = it }
        MyField(
            R.string.tip_rate, tipRateInput, ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        ) { tipRateInput = it }
        RoundTheTipRow(roundUp = roundUpInput, onRoundUpChanged = { roundUpInput = it })
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TESTING_AREATheme {
        TipCalculatorScreen()
    }
}