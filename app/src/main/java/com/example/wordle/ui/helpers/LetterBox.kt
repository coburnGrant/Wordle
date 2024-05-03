package com.example.wordle.ui.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
