package com.example.wordle.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.WordleViewModel

@Composable
fun WordleSettingsView(wordleViewModel: WordleViewModel) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Stats",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Text(text = "Games: ${wordleViewModel.numGames()}")
            Text(text = "Wins: ${wordleViewModel.numWins}")
            Text(text = "Losses: ${wordleViewModel.numLosses}")
            Text(text = "Average Guesses: ${wordleViewModel.averageGuesses()}")
        }
    }
}
