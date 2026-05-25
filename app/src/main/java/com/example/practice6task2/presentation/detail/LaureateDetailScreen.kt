package com.example.practice6task2.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.practice6task2.domain.model.Laureate
import com.example.practice6task2.domain.model.NobelPrize

@Composable
fun LaureateDetailScreen(
    prize: NobelPrize,
    laureate: Laureate,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onBackClick) {
            Text(text = "Назад")
        }

        Text(
            text = "Детали премии",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = laureate.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(text = "Год: ${prize.awardYear}")

                Text(text = "Категория: ${prize.category}")

                Text(
                    text = "Описание:",
                    fontWeight = FontWeight.Bold
                )

                Text(text = laureate.motivation)

                Text(
                    text = "Страна: ${laureate.birthCountry ?: "Не указана"}"
                )

                Text(
                    text = "Место рождения: ${laureate.birthPlace ?: "Не указано"}"
                )
            }
        }
    }
}