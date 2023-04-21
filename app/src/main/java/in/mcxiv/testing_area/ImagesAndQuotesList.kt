package `in`.mcxiv.testing_area

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme

class ImagesAndQuotesList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TESTING_AREATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AffirmationList(l = DataSource().loadAffirmations())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelper() {
    TESTING_AREATheme {
        AffirmationList(l = DataSource().loadAffirmations())
    }
}

@Composable
private fun AffirmationList(l: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn() {
        items(l) { aff ->
            Pair(aff)
        }
    }
}

@Composable
fun Pair(aff: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp), elevation = 4.dp) {
        Column() {
            Image(
                painter = painterResource(aff.imgId),
                contentDescription = stringResource(aff.strId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(aff.strId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}
