package `in`.mcxiv.testing_area

import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TESTING_AREATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyMainView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TESTING_AREATheme {
        MyMainView()
    }
}

@Composable
fun <T : ComponentActivity> MyNavigationButton(clazz: Class<T>, modifier: Modifier = Modifier) {

    val current = LocalContext.current
    val reduce = clazz.simpleName.chars().mapToObj {
        if (Character.isUpperCase(it))
            return@mapToObj " ${it.toChar()}"
        else return@mapToObj it.toChar().toString()
    }.reduce("") { a, b -> a + b }.substring(1)

    Button(modifier = modifier.fillMaxWidth(), onClick = {
        current.startActivity(Intent(current, clazz))
    }) { Text(text = reduce) }
}

@Composable
fun MyMainView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(24.dp)) {
        Text(text = "Experiments")
        MyNavigationButton(clazz = ImagesAndQuotesList::class.java)
        MyNavigationButton(clazz = TopicsGrid::class.java)
        MyNavigationButton(clazz = TipCalculator::class.java)
        MyNavigationButton(clazz = AndroidXNavigationBar::class.java)
        MyNavigationButton(clazz = NaiveNavigationBar::class.java)
    }
}