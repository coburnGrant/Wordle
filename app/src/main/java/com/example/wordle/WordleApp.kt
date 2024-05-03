package com.example.wordle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wordle.ui.WordleGameView
import com.example.wordle.ui.WordleSettingsView
import com.example.wordle.ui.helpers.WordleTopAppBar

@Composable
fun WordleApp(wordleViewModel: WordleViewModel, navController: NavHostController, currentScreen: WordleScreen) {
    Scaffold(
        topBar = {
            WordleTopAppBar(
                navController = navController,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = WordleScreen.GAME.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            composable(WordleScreen.GAME.route) {
                WordleGameView(wordleViewModel = wordleViewModel)
            }

            composable(WordleScreen.SETTINGS.route) {
                WordleSettingsView(wordleViewModel = wordleViewModel)
            }
        }
    }
}