import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExerciseRecord(
    val id: Int,
    val date: String,
    val type: String,
    val duration: String,
    val caloriesBurned: Int
)

@Composable
fun ExerciseHistoryCrudScreen() {
    val exerciseList = remember {
        mutableStateListOf(
            ExerciseRecord(1, "2025-04-14", "Jogging", "30 mins", 220),
            ExerciseRecord(2, "2025-04-13", "Yoga", "45 mins", 150),
            ExerciseRecord(3, "2025-04-12", "Jump Rope", "20 mins", 180)
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2E8B57)) {
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, selectedTextColor = Color.White,
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
                    selected = true,
                    onClick = {},
                    icon = { Text("📚", fontSize = 20.sp) },
                    label = { Text("History", color = Color(0xFF2E8B57)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF2E8B57),
                        unselectedIconColor = Color(0xFF888888),
                        unselectedTextColor = Color(0xFF888888)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", color = Color(0xFF888888)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, selectedTextColor = Color.White,
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
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "📖 Exercise History",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E8B57),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(exerciseList, key = { it.id }) { record ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("📅 Date: ${record.date}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("🏃 Type: ${record.type}", fontSize = 14.sp)
                            Text("⏱ Duration: ${record.duration}", fontSize = 14.sp)
                            Text("🔥 Calories Burned: ${record.caloriesBurned} kcal", fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                TextButton(onClick = {
                                    val index = exerciseList.indexOf(record)
                                    if (index != -1) {
                                        exerciseList[index] = record.copy(type = "${record.type} (edited)")
                                    }
                                }) {
                                    Text("✏️ Edit")
                                }
                                TextButton(onClick = {
                                    exerciseList.remove(record)
                                }) {
                                    Text("🗑 Delete", color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📊 View Recent Progress Button
            Button(
                onClick = {
                    // TODO: Replace with navigation or dialog
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
            ) {
                Text("📊 View Recent Progress", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExerciseHistoryCrudScreen() {
    ExerciseHistoryCrudScreen()
}