package `in`.mcxiv.testing_area

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme

class NaiveNavigationBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TESTING_AREATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyScreenView()
                }
            }
        }
    }
//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
}

enum class NavigableScreen(val displayName: String, @DrawableRes val icon: Int) {
    HOME("Home", R.drawable.house_solid),
    HISTORY("History", R.drawable.envelope_solid),
    PROFILE("Profile", R.drawable.user_solid);
}

enum class DogType(val specieName: String, @DrawableRes val img: Int) {
    A1("Labrador Retrievers", R.drawable.a2),
    A2("German Shepherds", R.drawable.a3),
    A3("Golden Retrievers", R.drawable.a4),
    A4("French Bulldogs", R.drawable.a5),
    A5("Bulldogs", R.drawable.a6),
    A6("Beagles", R.drawable.a7),
    A7("Poodles", R.drawable.a8),
    A8("Rottweilers", R.drawable.a9),
    A9("German Shorthaired Pointers", R.drawable.a10),
    A0("Yorkshire Terriers", R.drawable.a11)
}

@Composable
fun MyScreenView() {
    val values = NavigableScreen.values()
    val entries = DogType.values()
    var selectedMenuIndex by remember { mutableStateOf(0) }
    val selectedEntriesHistory = remember { mutableStateListOf<Int>() }

    Scaffold(
        bottomBar = {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(values.size) {
                    MyNavButton(
                        screen = values[it],
                        isSelected = it == selectedMenuIndex,
                        onClick = { selectedMenuIndex = it })
                }
            }
        }
    ) {
        println(it)
        when (selectedMenuIndex) {
            0 -> HomeScreen(entries, selectedEntriesHistory)
            1 -> HistoryScreen(entries, selectedEntriesHistory)
            2 -> ProfileScreen(DogType.values()[0])
        }
    }
}

@Composable
fun MyNavButton(screen: NavigableScreen, isSelected: Boolean = false, onClick: () -> Unit) {
    val textAlpha = if (isSelected) 1.0f else 0.0f
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(0)
    ) {
        Column {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.displayName,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = screen.displayName,
                color = contentColorFor(MaterialTheme.colors.primary).copy(alpha = textAlpha)
            )
        }
    }
}


@Composable
fun HomeScreen(entries: Array<DogType>, selectedEntriesHistory: SnapshotStateList<Int>) {
    var selectedEntryIndex by remember { mutableStateOf(-1) }

    if (selectedEntryIndex == -1)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(entries.size) {
                DisplayCard(entries[it]) {
                    selectedEntryIndex = it
                    selectedEntriesHistory.add(it)
                }
            }
        }
    else
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("IoT Pets Review")
            Image(
                painter = painterResource(id = entries[selectedEntryIndex].img),
                contentDescription = entries[selectedEntryIndex].specieName,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Text(entries[selectedEntryIndex].specieName)
            StarField("Popularity Rating", 5)
            StarField("Marking Rating", 3)
            StarField("Attendance Rating", 1)
        }

    BackHandler(enabled = selectedEntryIndex != -1) {
        selectedEntryIndex = -1
    }
}

@Composable
fun DisplayCard(entry: DogType, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp, 10.dp, 0.dp, 10.dp))
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 30.dp, 10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painterResource(entry.img), entry.specieName,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .weight(1f)
        ) {
            Text(text = entry.specieName, fontSize = 16.sp)
            Text(text = "Content id?", fontSize = 12.sp)
            Text(text = "Rating Placeholder", fontSize = 12.sp)
        }
        Icon(
            painterResource(id = R.drawable.arrow_right_solid), entry.specieName,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun StarField(name: String, stars: Int) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 30.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name)
        LazyRow {
            items(stars) {
                Icon(
                    painter = painterResource(id = R.drawable.star_solid),
                    contentDescription = "star",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

@Composable
fun HistoryScreen(entries: Array<DogType>, selectedEntriesHistory: SnapshotStateList<Int>) {
    if (selectedEntriesHistory.size == 0) {
        Text(text = "Nothing to show here...")
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(selectedEntriesHistory) {
                DisplayCard(entries[it]) {}
            }
        }
    }
}

@Composable
fun LabelledTextField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = label, fontSize = 15.sp)
        TextField(value = value, onValueChange = {}, shape = RoundedCornerShape(10.dp))
    }
}

@Composable
fun ProfileScreen(entry: DogType) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Dog Specie Preview", modifier = Modifier.padding(20.dp))
        Text(text = "Profile", fontSize = 20.sp, modifier = Modifier.padding(20.dp))
        Image(
            painter = painterResource(id = entry.img),
            contentDescription = entry.specieName,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Text(text = entry.specieName, fontSize = 20.sp, modifier = Modifier.padding(20.dp))

        LabelledTextField(
            label = "Roll Number",
            value = "${entry.hashCode()}"
        )
        LabelledTextField(
            label = "Email ID",
            value = "${entry.specieName}@mail.co"
        )
        LabelledTextField(
            label = "Semester",
            value = "${1 + entry.ordinal}"
        )
        Button(onClick = { }, shape = CircleShape) {
            Text(text = "Sign Out")
        }
    }
//    androidx.compose.material.T
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    TESTING_AREATheme {
        MyScreenView()
//        HomeScreen(entries = DogType.values())
//        ProfileScreen(entry = DogType.values()[0])
    }
}