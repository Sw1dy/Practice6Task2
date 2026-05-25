package com.example.practice6task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.practice6task2.presentation.navigation.AppNavigation
import com.example.practice6task2.ui.theme.Practice6Task2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Practice6Task2Theme {
                AppNavigation()
            }
        }
    }
}