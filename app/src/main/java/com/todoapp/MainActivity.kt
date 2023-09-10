package com.todoapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.todoapp.ui.screens.mainscreen.MainScreen
import com.todoapp.ui.theme.TodoappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "1")
        super.onCreate(savedInstanceState)
        setContent {
            TodoappTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}
