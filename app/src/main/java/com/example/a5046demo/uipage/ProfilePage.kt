package com.example.a5046demo.uipage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5046demo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenGreenTheme(
    userName: String = "John Doe",
    location: String = "Melbourne, Australia",
    weight: String = "60kg",
    height: String = "175cm",
    intake: String = "2168kcal",
    bmi: String = "19.6",
    burned: String = "273 kcal"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    TextButton(onClick = { /* TODO: handle edit profile */ }) {
                        Text("Edit", color = Color(0xFF2E8B57), fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2E8B57)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { /* Navigate to Home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF888888),
                        unselectedTextColor = Color(0xFF888888)
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
                    selected = true,
                    onClick = { /* Already on Profile */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", color = Color(0xFF2E8B57)) },
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
            // Avatar
            Image(
                painter = painterResource(id = R.drawable.asd),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            // Name and Location
            Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
            Text(location, fontSize = 14.sp, color = Color.Gray)

            // Info Cards Grid
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Weight", weight, "⚖️")
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Height", height, "📏")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Daily Intake", intake, "🍽️")
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("BMI", bmi, "📊")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { /* TODO: handle logout */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // Calorie Burned
            Spacer(modifier = Modifier.height(16.dp))
            Text("Calories Burned Today", fontSize = 14.sp, color = Color.Gray)
            Text(burned, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))


        }

        // Logout Button

    }
}


@Composable
fun InfoCardGreen(title: String, value: String, icon: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF0))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(icon, fontSize = 20.sp)
            Text(title, fontSize = 14.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2E8B57))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreenGreen() {
    ProfileScreenGreenTheme()
}
