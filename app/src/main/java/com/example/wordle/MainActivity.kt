package com.example.wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wordle.ui.theme.WordleTheme

class MainActivity : ComponentActivity() {
    private val wordleViewModel: WordleViewModel = WordleViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()

                    val backStackEntry by navController.currentBackStackEntryAsState()

                    val currentScreen: WordleScreen = WordleScreen.fromRoute(backStackEntry?.destination?.route)

                    WordleApp(wordleViewModel = wordleViewModel, navController = navController, currentScreen = currentScreen)
                }
            }
        }
    }
}