package com.example.practice6task2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice6task2.presentation.detail.LaureateDetailScreen
import com.example.practice6task2.presentation.login.LoginScreen
import com.example.practice6task2.presentation.prizes.NobelPrizesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.PRIZES_LIST) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.PRIZES_LIST) {
            NobelPrizesScreen(
                onLaureateClick = { prize, laureate ->
                    SelectedLaureateHolder.selectedPrize = prize
                    SelectedLaureateHolder.selectedLaureate = laureate
                    navController.navigate(Routes.LAUREATE_DETAIL)
                }
            )
        }

        composable(Routes.LAUREATE_DETAIL) {
            val prize = SelectedLaureateHolder.selectedPrize
            val laureate = SelectedLaureateHolder.selectedLaureate

            if (prize != null && laureate != null) {
                LaureateDetailScreen(
                    prize = prize,
                    laureate = laureate,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}