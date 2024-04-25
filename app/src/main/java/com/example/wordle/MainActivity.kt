package com.example.wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordle.ui.theme.WordleTheme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    companion object {
        val wordsToGuess = arrayOf(
            "Apple", "Beach", "Chair", "Dance", "Eagle",
            "Fairy", "Ghost", "House", "Jelly", "Koala",
            "Lemon", "Magic", "Nurse", "Ocean", "Peach",
            "Queen", "River", "Snake", "Tiger", "Umbra",
            "Grant"
        )
    }

    private var wordToGuess: String = MainActivity.wordsToGuess.random().uppercase()
    private var enteredText: String = ""
    private var charList by mutableStateOf(Array(6) { Array(5) { ' ' } })
    private var currentGuess: Int = 0
    private var userWon by mutableStateOf(false)
    private var isGameOver by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(wordToGuess)

                        WordleGrid(charList, wordToGuess)

                        var text by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = validateText(it)
                                enteredText = text
                            }
                        )

                        Button(onClick = { enterClicked() }) {
                            Text("Enter")
                        }

                        // Show alert dialog when user wins
                        if (userWon) {
                            AlertDialog(
                                onDismissRequest = { resetGame() },
                                title = {
                                    Text("Congrats!")
                                },
                                text = {
                                    Text("You won in ${currentGuess + 1}")
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = { resetGame() },
                                    ) {
                                        Text("Ok")
                                    }
                                }
                            )
                        }

                        if (isGameOver) {
                            AlertDialog(
                                onDismissRequest = { resetGame() }, // Reset state when dialog is dismissed
                                title = {
                                    Text("You lose")
                                },
                                text = {
                                    Text("The correct word was: $wordToGuess")
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = { resetGame() }, // Reset state when confirm button is clicked
                                    ) {
                                        Text("Ok")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun resetGame() {
        wordToGuess = MainActivity.wordsToGuess.random()
        enteredText = ""
        charList = Array(6) { Array(5) { ' ' } }
        currentGuess = 0
        userWon = false
        isGameOver = false
    }

    private fun validateText(text: String): String {
        var string = text

        if (string.length > 5) {
            string = string.subSequence(0, 5).toString()
        }

        return string
    }

    private fun enterClicked() {
        // Check if the entered string is exactly 5 letters
        if (enteredText.length != 5 ) {
            // TODO possibly alert the user
            return
        }

        if(currentGuess >= 6 || isGameOver || userWon) {
            return
        }

        val charArray = enteredText.uppercase().toCharArray().toTypedArray()

        // Create a new copy of charList and update it with the new values to update UI properly
        charList = charList.copyOf().also {
            it[currentGuess] = charArray
        }

        if(enteredText.equals(wordToGuess, ignoreCase = true)) {
            // the user has won, alert the user
            userWon = true
        } else if (currentGuess > 5){
            // the user has used all guesses, they lose
            isGameOver = true
        } else {
            // continue to next guess
            currentGuess++
        }
    }

}

@Composable
fun WordleGrid(charList: Array<Array<Char>>, wordToGuess: String) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.TopCenter)
    ) {
        for (rowIndex in charList.indices) {
            val row = charList[rowIndex]

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (columnIndex in row.indices) {
                    val char = row[columnIndex]

                    val color = if (char == wordToGuess[columnIndex]) {
                        Color.Green
                    } else if (wordToGuess.contains(char)) {
                        Color.Yellow
                    } else {
                        Color.LightGray
                    }

                    LetterBox(letter = char.toString(), color = color)
                }
            }
        }
    }
}

@Composable
fun LetterBox(letter: String, color: Color) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .background(color)
            .wrapContentSize(Alignment.Center) // Center align content inside the box
    ) {
        Text(
            text = letter,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center) // Align text to the center of the box
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordleTheme {
        LetterBox(letter = "String", color = Color.LightGray)
    }
}