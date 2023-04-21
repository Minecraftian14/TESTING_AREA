package `in`.mcxiv.testing_area

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme

class TopicsGrid : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TESTING_AREATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyGridView(DataSource().topics)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TESTING_AREATheme {
        MyGridView(DataSource().topics)
    }
}

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color(68, 68, 68, 255))) {
        Image(
            painterResource(id = topic.imgId), stringResource(id = topic.strId),
            modifier = modifier
                .width(68.dp)
                .height(68.dp)
        )
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                stringResource(id = topic.strId),
                modifier = modifier.padding(8.dp),
                fontSize = 12.sp
            )
            Row {
                Image(
                    painterResource(id = R.drawable.ic_grain), "",
                    modifier = modifier
                        .padding(8.dp, 0.dp)
                        .width(12.dp)
                        .height(12.dp)
                )
                Text("${topic.smtg}", fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun MyGridView(topics: List<Topic>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(topics) {
            TopicCard(it)
        }
    }
}
