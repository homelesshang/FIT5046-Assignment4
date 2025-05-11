package com.example.a5046demo.uipage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import com.example.a5046demo.R

@Composable
fun EnhancedHomeScreen(userName: String = "John Doe", weight: String = "68.5 kg") {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2E8B57)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", color = Color(0xFF2E8B57)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF2E8B57),
                        unselectedTextColor = Color(0xFF2E8B57)
                    )
                )
                NavigationBarItem(
                    selected = false, // 或 true 取决于你是否当前页面
                    onClick = { /* Navigate to Record */ },
                    icon = { Text("📝", fontSize = 20.sp) }, // 或者用 Icon(Icons.Default.Edit, ...)
                    label = { Text("Record", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF888888),
                        unselectedTextColor = Color(0xFF888888)
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { /* Navigate to History screen */ },
                    icon = { Text("📚", fontSize = 20.sp) }, // 你也可以改成 Icons.Default.List 或自定义图标
                    label = { Text("History", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF888888),
                        unselectedTextColor = Color(0xFF888888)
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF888888),
                        unselectedTextColor = Color(0xFF888888)
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Top Profile Avatar + Welcome
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.asd), // 换成你的文件名
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(50))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Welcome back,", fontSize = 14.sp, color = Color.Gray)
                    Text(userName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
                }
            }

            // Highlighted Kcal Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E8B57)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Today, 14 Apr", fontSize = 16.sp, color = Color.White)
                    Text("1883 Kcal", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                        Text("Track your activity", color = Color(0xFF2E8B57))
                    }
                }
            }

            // Top Exercises
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Today's Breakdown", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExerciseCard(title = "Dumbbell", kcal = 628, color = Color(0xFF2E8B57))
                    ExerciseCard(title = "Treadmill", kcal = 235, color = Color(0xFF000000))
                    ExerciseCard(title = "Rope", kcal = 432, color = Color(0xFF888888))
                }
            }

            // Weekly Workout Card with Weight
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

            // Weather & Suggestion Card (放大版)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🌤️ Melbourne",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E8B57)
                        )
                        Text(
                            text = "22°C",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2E8B57)
                        )
                    }

                    Text(
                        text = "Perfect weather for an outdoor run! 🏃‍♂️ Stay hydrated 💧",
                        fontSize = 14.sp,
                        color = Color(0xFF2E8B57)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SummaryStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(label, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun ExerciseCard(title: String, kcal: Int, color: Color) {
    Card(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$kcal Kcal", color = Color.White, fontWeight = FontWeight.Bold)
            Text(title, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEnhancedHomeScreen() {
    EnhancedHomeScreen()
}