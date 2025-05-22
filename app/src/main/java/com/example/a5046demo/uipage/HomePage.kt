package com.example.a5046demo.uipage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import com.example.a5046demo.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046demo.viewmodel.WeatherViewModel
import androidx.compose.runtime.*
import com.example.a5046demo.viewmodel.UserProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.text.style.TextAlign
import com.example.a5046demo.viewmodel.ExerciseViewModel
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.example.a5046demo.uipage.navigation.HomePageRoutes

@Composable
fun HomeScreen(
    viewModel: UserProfileViewModel,
    exerciseViewModel: ExerciseViewModel,
    navController: NavHostController
) {
    val profile by viewModel.userProfile.collectAsState(initial = null)
    val todayStats by exerciseViewModel.todayStats.collectAsState()

    LaunchedEffect(Unit) {
        exerciseViewModel.loadTodayStats()
    }

    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val nickname = profile!!.nickname
    val weight = profile!!.weight?.let { "$it kg" } ?: "Unknown"
    val todayText = remember {
        "Today, " + SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🔹 Profile Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.asd),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Welcome back,", fontSize = 14.sp, color = Color.Gray)
                Text(nickname, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
            }
        }

        // 🔹 Highlighted Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = todayText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { navController.navigate(HomePageRoutes.Record) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Track your activity", color = Color(0xFF2E8B57))
                }
            }
        }

        // 🔹 Daily Exercise Summary
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Today's Breakdown", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            if (todayStats.isEmpty()) {
                Text("No activity logged today.", color = Color.Gray)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(todayStats) { stat ->
                        ExerciseCard(
                            title = stat.name,
                            kcal = stat.calories,
                            color = stat.color
                        )
                    }
                }
            }
        }

        // 🔹 Weight Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        color = Color(0xFFFF8A80),
                        shape = RoundedCornerShape(50)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("⚡", color = Color.White, fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Body Weight", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Current: $weight", fontSize = 14.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Next exercise", fontSize = 12.sp, color = Color.Gray)
                        Text("Lower Strength", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // 🔹 Weather Info (Always Shown)
        WeatherCard(userProfileViewModel = viewModel)

        Spacer(modifier = Modifier.height(100.dp))
    }
}




@Composable
fun ExerciseCard(title: String, kcal: Int, color: Color) {
    Card(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$kcal Kcal", color = Color.White, fontWeight = FontWeight.Bold)
            Text(title, color = Color.White)
        }
    }
}

@Composable
fun WeatherCard(userProfileViewModel: UserProfileViewModel,viewModel: WeatherViewModel = viewModel()) {
    val weatherText by viewModel.weather.collectAsState()
    val profile by userProfileViewModel.userProfile.collectAsState(initial = null)
    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    val region by remember { mutableStateOf(profile!!.region) }
    val regionWithCountry = "$region,AU"


    LaunchedEffect(Unit) {
        viewModel.fetchWeather(regionWithCountry)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "\uD83C\uDF24\uFE0F Your location now:$region",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E8B57)
            )

            Text(
                text = weatherText, // 动态显示天气信息
                fontSize = 16.sp,
                color = Color(0xFF2E8B57)
            )
        }
    }
}






//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen()
//}