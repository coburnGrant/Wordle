package com.example.wordle

enum class WordleScreen(val route: String, val title: String) {
    GAME("game", "Wordle"),
    SETTINGS("settings", "Settings");
    companion object {
        fun fromRoute(route: String?): WordleScreen {
            return when (route) {
                GAME.route -> GAME
                SETTINGS.route -> SETTINGS
                else -> GAME // Default to the game screen if route is null or not recognized
            }
        }
    }
}