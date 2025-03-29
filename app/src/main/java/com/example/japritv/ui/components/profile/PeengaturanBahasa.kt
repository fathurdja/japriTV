package com.example.japritv.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LanguageSelectionScreen() {
    val languages = listOf("Indonesian", "English", "Español", "Français", "Português")
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        LazyColumn {
            items(languages) { language ->
                LanguageItem(
                    language = language,
                    isSelected = language == selectedLanguage,
                    onSelected = { selectedLanguage = it }
                )
            }
        }
    }
}

@Composable
fun LanguageItem(language: String, isSelected: Boolean, onSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = language,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        RadioButton(
            colors = RadioButtonDefaults.colors(
            selectedColor = Color(0xFFFFA500),
            unselectedColor = Color.Gray
        ),
            selected = isSelected,
            onClick = { onSelected(language) },
        )
    }
}

@Preview
@Composable
fun PreviewLanguageSelectionScreen() {
    LanguageSelectionScreen()
}