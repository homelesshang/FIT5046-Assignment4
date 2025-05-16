package com.example.a5046demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a5046demo.ui.theme._5046demoTheme
import com.example.a5046demo.uipage.WeatherCard
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.example.a5046demo.uipage.*

import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModelFactory
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: ExerciseViewModel = ViewModelProvider(
            this,
            ExerciseViewModelFactory(application)
        )[ExerciseViewModel::class.java]
        setContent {
            _5046demoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StartAppNavigation(viewModel = viewModel) // ✅ 显示完整首页
                }
            }
        }
    }
}