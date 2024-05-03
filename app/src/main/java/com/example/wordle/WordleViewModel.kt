package com.example.wordle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wordle.ui.helpers.AlertView
import java.util.Locale

class WordleViewModel : ViewModel() {
    var wordToGuess: String by mutableStateOf(WordSupplier.randomWord())
    var enteredText: String by mutableStateOf("")
    var charList by mutableStateOf(Array(6) { Array(5) { ' ' } })
    var currentGuess: Int by mutableIntStateOf(0)
    var alerting: Boolean by mutableStateOf(false)
    var displayedAlert: @Composable (() -> Unit)? by mutableStateOf(null)

    //MARK stats
    var numWins: Int by mutableIntStateOf(0)
    var numLosses: Int by mutableIntStateOf(0)
    var totalGuesses: Int by mutableIntStateOf(0)

    fun numGames(): Int { return numWins + numLosses }
    fun averageGuesses(): Int {
        if(numGames() != 0) {
            return totalGuesses / numGames()
        } else {
            return 0
        }
    }

    val userWonAlert: @Composable () -> Unit = {
        AlertView(
            title = "Congrats!",
            body = "You won in ${currentGuess + 1}",
            buttonLabel = "Reset Game"
        ) {
            resetGame()
        }
    }

    val gameOverAlert: @Composable () -> Unit = {
        AlertView(
            title = "Game over!",
            body = "The correct word was: ${wordToGuess.lowercase().capitalize(locale = Locale.US)}",
            buttonLabel = "Reset Game"
        ) {
            resetGame()
        }
    }

    val enterFiveLetterWord: @Composable () -> Unit = {
        AlertView(
            title = "Invalid word",
            body = "Please enter a 5 letter word",
            buttonLabel = "Ok"
        ) {
            alerting = false
        }
    }

    fun resetGame() {
        wordToGuess = WordSupplier.randomWord()
        enteredText = ""
        charList = Array(6) { Array(5) { ' ' } }
        currentGuess = 0
        alerting = false
    }

    fun validateText(text: String): String {
        var string = text

        // Remove any non-letter characters from the input text
        string = string.filter { it.isLetter() }

        // Ensure the resulting string is no longer than 5 characters
        if (string.length > 5) {
            string = string.substring(0, 5)
        }

        return string
    }

    fun textEnteredChanged(newValue: String) {
        // Validate the text
        enteredText = validateText(newValue)

        updateGrid()
    }

    fun updateGrid() {
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

    fun enterClicked() {
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
        totalGuesses++

        if(enteredText.equals(wordToGuess, ignoreCase = true)) {
            // the user has won, alert the user
            currentGuess--
            displayedAlert = userWonAlert
            alerting = true
            numWins++
        } else if (currentGuess > 5){
            // the user has used all guesses, they lose
            numLosses++
            displayedAlert = gameOverAlert
            alerting = true
        } else {
            enteredText = ""
            updateGrid()
        }
    }
}
