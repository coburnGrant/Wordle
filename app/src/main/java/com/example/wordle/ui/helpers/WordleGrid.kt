package com.example.wordle.ui.helpers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.wordle.ui.theme.wordleGray
import com.example.wordle.ui.theme.wordleGreen
import com.example.wordle.ui.theme.wordleYellow

@Composable
fun WordleGrid(charList: Array<Array<Char>>, wordToGuess: String, currentGuessIndex: Int) {
    Column(
        modifier = Modifier
    ) {
        for (rowIndex in charList.indices) {
            val row = charList[rowIndex]

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (columnIndex in row.indices) {
                    val char = row[columnIndex]

                    val boxColor: Color = if (rowIndex != currentGuessIndex) {
                        if (char == wordToGuess[columnIndex]) {
                            wordleGreen
                        } else if (wordToGuess.contains(char)) {
                            wordleYellow
                        } else {
                            if (rowIndex < currentGuessIndex) {
                                wordleGray
                            } else {
                                Color.LightGray
                            }
                        }
                    } else {
                        Color.LightGray
                    }

                    val textColor = if(boxColor == Color.LightGray) {
                        Color.Black
                    } else {
                        Color.White
                    }

                    LetterBox(letter = char.toString(), boxColor = boxColor, textColor = textColor)
                }
            }
        }
    }
}
