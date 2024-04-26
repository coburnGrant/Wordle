package com.example.wordle

import WordSupplier
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordle.ui.theme.WordleTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

public val gray: Color = Color(120, 124, 126)
public val yellow: Color = Color(201, 180, 88)
public val green: Color = Color(106, 170, 100)

class MainActivity : ComponentActivity() {
    private var wordToGuess: String = WordSupplier.randomWord()
    private var enteredText by mutableStateOf("")
    private var charList by mutableStateOf(Array(6) { Array(5) { ' ' } })
    private var currentGuess: Int = 0
    private var alerting by mutableStateOf(false)

    private var displayedAlert: @Composable (() -> Unit)? = null



    private val userWonAlert: @Composable () -> Unit = {
        AlertView(
            title = "Congrats!",
            body = "You won in ${currentGuess + 1}",
            buttonLabel = "Reset Game"
        ) {
            resetGame()
        }
    }

    private val gameOverAlert: @Composable () -> Unit = {
        AlertView(title = "Game over!", body = "The correct word was: ${wordToGuess.lowercase().capitalize(locale = Locale.US)}", buttonLabel = "Reset Game") {
            resetGame()
        }
    }

    private val enterFiveLetterWord: @Composable () -> Unit = {
        AlertView(title = "Invalid word", body = "Please enter a 5 letter word", buttonLabel = "Ok") {
            alerting = false
        }
    }

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
                            .padding(top = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WordleGrid(charList, wordToGuess, currentGuess)

                        Row {
                            OutlinedTextField(
                                modifier = Modifier.padding(8.dp),
                                value = enteredText,
                                singleLine = true,

                                onValueChange = {
                                    textEnteredChanged(it)
                                }
                            )

                            Button(
                                modifier = Modifier.padding(8.dp),
                                onClick = { enterClicked() }) {
                                Text("Enter")
                            }
                        }

                        if(alerting) {
                            displayedAlert?.let { it() }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AlertView(title: String, body: String, buttonLabel: String, onClick: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onClick() },
            title = {
                Text(title)
            },
            text = {
                Text(body)
            },
            confirmButton = {
                TextButton(
                    onClick = { onClick() },
                ) {
                    Text(buttonLabel)
                }
            }
        )
    }

    private fun resetGame() {
        wordToGuess = WordSupplier.randomWord()
        enteredText = ""
        charList = Array(6) { Array(5) { ' ' } }
        currentGuess = 0
        alerting = false
    }

    private fun validateText(text: String): String {
        var string = text

        // Remove any non-letter characters from the input text
        string = string.filter { it.isLetter() }

        // Ensure the resulting string is no longer than 5 characters
        if (string.length > 5) {
            string = string.substring(0, 5)
        }

        return string
    }

    private fun textEnteredChanged(newValue: String) {
        // Validate the text
        enteredText = validateText(newValue)

        updateGrid()
    }

    private fun updateGrid() {
        // Make sure list has exactly 5 characters
        var textToDisplay = enteredText
        if (textToDisplay.length < 5) {
            val spacesToAdd: Int = 5 - textToDisplay.length
            var whiteSpace = ""
            repeat(spacesToAdd) {
                whiteSpace += " "
            }

            // Append whitespace to the end of the text
            textToDisplay += whiteSpace
        }

        val charArray = textToDisplay.uppercase().toCharArray().toTypedArray()

        // Create a new copy of charList and update it with the new values to update UI properly
        if(currentGuess > 5) {
            currentGuess = 5
        }

        charList = charList.copyOf().also {
            it[currentGuess] = charArray
        }
    }

    private fun enterClicked() {
        // Check if the entered string is exactly 5 letters
        if (enteredText.length != 5 ) {
            displayedAlert = enterFiveLetterWord
            alerting = true
            return
        }

        if(currentGuess >= 6) {
            return
        }

        updateGrid()
        currentGuess++

        if(enteredText.equals(wordToGuess, ignoreCase = true)) {
            // the user has won, alert the user
            displayedAlert = userWonAlert
            alerting = true
        } else if (currentGuess > 5){
            // the user has used all guesses, they lose
            displayedAlert = gameOverAlert
            alerting = true
        } else {
            enteredText = ""
            updateGrid()
        }
    }
}

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
                            green
                        } else if (wordToGuess.contains(char)) {
                            yellow
                        } else {
                            if (rowIndex < currentGuessIndex) {
                                gray
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

@Composable
fun LetterBox(letter: String, boxColor: Color, textColor: Color) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    print("screen width: $screenWidth")
    val boxWidth = (screenWidth - 75)/ 5

    Box(
        modifier = Modifier
            .padding(6.dp)
            .background(boxColor)
            .size(width = boxWidth.dp, height = boxWidth.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = letter,
            color = textColor,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.Center)
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WordleTheme {
////        LetterBox(letter = "String", color = Color.LightGray)
//    }
//}