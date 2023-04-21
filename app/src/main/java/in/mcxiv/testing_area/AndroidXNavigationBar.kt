package `in`.mcxiv.testing_area

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.mcxiv.testing_area.ui.theme.TESTING_AREATheme

class AndroidXNavigationBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TESTING_AREATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainViewContainer()
                }
            }
        }
    }
}

@Composable
fun MainViewContainer() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { NavigationBar(navController, NavigableScreen.values()) }, content = {
        println(it)
        MainScreenNavigationConfigurations(navController)
    })
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("")
}

@Composable
fun NavigationBar(navController: NavHostController, list: Array<NavigableScreen>) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        list.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(screen.icon), "") },
                label = { Text(screen.displayName) },
                selected = currentRoute == screen.displayName,
                alwaysShowLabel = false,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.displayName) {
                        navController.navigate(screen.displayName)
                    }
                }
            )
        }
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    val selectedEntriesHistory = remember { mutableStateListOf<Int>() }
    NavHost(navController, startDestination = NavigableScreen.HOME.displayName) {
        composable(NavigableScreen.HOME.displayName) {
            HomeScreen(DogType.values(), selectedEntriesHistory)
        }
        composable(NavigableScreen.HISTORY.displayName) {
            HistoryScreen(DogType.values(), selectedEntriesHistory)
        }
        composable(NavigableScreen.PROFILE.displayName) {
            ProfileScreen(DogType.values()[0])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    TESTING_AREATheme {
        MainViewContainer()
    }
}