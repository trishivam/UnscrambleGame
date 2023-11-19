package com.example.unscramblegame.ui.theme

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessWordWrong: Boolean = false,
    val currentWordCount: Int = 1,
    val score: Int = 0,
    val isGameOver: Boolean = false
    //Example code no need to copy over
    )

