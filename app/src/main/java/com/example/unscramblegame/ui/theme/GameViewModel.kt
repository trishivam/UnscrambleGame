package com.example.unscramblegame.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String

    private var usedWords: MutableSet<String> = mutableSetOf()

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        while ( String(tempWord) == word ) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }
    var userGuess by mutableStateOf("")

    // Reset Game

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    init {
        resetGame()
    }

    // Verify guess word and update score
    fun checkUserGuess(){
        if (userGuess.equals(currentWord, ignoreCase = true)){
            val updateScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updateScore)
        }
        else
        {
            _uiState.update { currentState -> currentState.copy(isGuessWordWrong = true) }
        }
    }

    // Update Game State
    private fun updateGameState (updateSCore: Int){
        if (usedWords.size == MAX_NO_OF_WORDS){
            // Last Round in the Game

        }
        else
        {
            _uiState.update { currentState -> currentState
                .copy(
                isGuessWordWrong = false,
                currentScrambledWord = pickRandomWordAndShuffle(),
                score = updateSCore,
                currentWordCount = currentState.currentWordCount.inc(),
            ) }
        }


    }

    // Skip Word
    fun skipWord(){
        updateGameState (_uiState.value.score)
        // reset user guess
        updateUserGuess("")
    }

    // Handle last round of game

    
}

