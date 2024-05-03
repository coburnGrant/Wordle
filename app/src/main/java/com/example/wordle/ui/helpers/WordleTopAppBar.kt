package com.example.wordle.ui.helpers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.wordle.WordleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordleTopAppBar(
    navController: NavHostController,
    currentScreen: WordleScreen,
    canNavigateBack: Boolean
) {
    TopAppBar(
        title = { Text(text = currentScreen.title) },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (currentScreen != WordleScreen.SETTINGS) {
                IconButton(onClick = {
                    navController.navigate(WordleScreen.SETTINGS.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}