package com.example.wordle.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wordle.ui.helpers.WordleGrid
import com.example.wordle.WordleViewModel

@Composable
fun WordleGameView(
    wordleViewModel: WordleViewModel
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WordleGrid(wordleViewModel.charList, wordleViewModel.wordToGuess, wordleViewModel.currentGuess)

        Row {
            OutlinedTextField(
                modifier = Modifier.padding(8.dp),
                value = wordleViewModel.enteredText,
                singleLine = true,

                onValueChange = {
                    wordleViewModel.textEnteredChanged(it)
                }
            )

            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { wordleViewModel.enterClicked() }) {
                Text("Enter")
            }
        }

        if(wordleViewModel.alerting) {
            wordleViewModel.displayedAlert?.let { it() }
        }
    }
}