package com.example.practice6task2.presentation.prizes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice6task2.domain.model.Laureate
import com.example.practice6task2.domain.model.NobelPrize

@Composable
fun NobelPrizesScreen(
    onLaureateClick: (NobelPrize, Laureate) -> Unit,
    viewModel: NobelPrizesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val year by viewModel.year.collectAsState()
    val category by viewModel.category.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Нобелевские премии",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        FilterBlock(
            year = year,
            category = category,
            onYearChanged = viewModel::onYearChanged,
            onCategoryChanged = viewModel::onCategoryChanged,
            onApplyClick = viewModel::applyFilters
        )

        when (val state = uiState) {
            NobelPrizesUiState.Loading -> {
                LoadingContent()
            }

            is NobelPrizesUiState.Success -> {
                NobelPrizesList(
                    prizes = state.prizes,
                    onLaureateClick = onLaureateClick
                )
            }

            is NobelPrizesUiState.Error -> {
                ErrorContent(
                    message = state.message,
                    onRetryClick = viewModel::loadPrizes
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBlock(
    year: String,
    category: String,
    onYearChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onApplyClick: () -> Unit
) {
    val categories = listOf(
        "",
        "physics",
        "chemistry",
        "literature",
        "peace",
        "medicine",
        "economics"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = year,
                onValueChange = onYearChanged,
                label = { Text("Год") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = category.ifBlank { "Все" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Категория") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item.ifBlank { "Все" })
                            },
                            onClick = {
                                onCategoryChanged(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Фильтр")
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = message)

            Button(
                onClick = onRetryClick,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text("Повторить")
            }
        }
    }
}

@Composable
private fun NobelPrizesList(
    prizes: List<NobelPrize>,
    onLaureateClick: (NobelPrize, Laureate) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        prizes.forEach { prize ->
            items(
                items = prize.laureates,
                key = { laureate -> "${prize.awardYear}_${prize.category}_${laureate.id}" }
            ) { laureate ->
                LaureateCard(
                    prize = prize,
                    laureate = laureate,
                    onClick = {
                        onLaureateClick(prize, laureate)
                    }
                )
            }
        }
    }
}

@Composable
private fun LaureateCard(
    prize: NobelPrize,
    laureate: Laureate,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${prize.awardYear} — ${prize.category.uppercase()}",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = laureate.fullName,
                modifier = Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = laureate.motivation.take(100),
                modifier = Modifier.padding(top = 6.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}