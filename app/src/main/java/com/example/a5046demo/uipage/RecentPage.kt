package com.example.a5046demo.uipage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenStatsPageWithHeader(onClose: () -> Unit = {}) {
    Scaffold(containerColor = Color.White) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 顶部标题栏 + 关闭按钮
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Recent Progress 📈",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E8B57)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray
                    )
                }
            }

            // 欢迎语 + 总结语
            Text(
                text = "You're on track this week! Keep it up 💪 🏃‍♂️ 🍏",
                fontSize = 14.sp,
                color = Color(0xFF2E8B57),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // 卡片：Weight
            OverviewStatCard(
                icon = Icons.Default.MoreVert,
                title = "Weight 🃏",
                value = "68.5 kg",
                barData = listOf(67.8f, 68.0f, 68.2f, 68.5f, 68.4f, 68.3f, 68.5f),
                labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            )

            // 卡片：Calories
            OverviewStatCard(
                icon = Icons.Default.MoreVert,
                title = "Calories 🔥",
                value = "2,000+",
                barData = listOf(1200f, 1400f, 1300f, 1800f, 2200f, 1900f, 2000f),
                labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            )

            // 卡片：Workout Time
            OverviewStatCard(
                icon = Icons.Default.MoreVert,
                title = "Workout Time ⏱️",
                value = "45 min",
                barData = listOf(20f, 30f, 15f, 60f, 45f, 50f, 40f),
                labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            )
        }
    }
}

@Composable
fun OverviewStatCard(
    icon: ImageVector,
    title: String,
    value: String,
    barData: List<Float>,
    labels: List<String>,
    containerColor: Color = Color(0xFFD0F0C0),
    barColor: Color = Color(0xFF2E8B57)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = barColor,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontWeight = FontWeight.SemiBold, color = barColor)
                }
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu", tint = barColor)
            }

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = barColor
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                val max = barData.maxOrNull()?.takeIf { it > 0 } ?: 1f
                barData.forEach { item ->
                    Canvas(
                        modifier = Modifier
                            .width(6.dp)
                            .fillMaxHeight(fraction = item / max)
                    ) {
                        drawRoundRect(
                            color = barColor,
                            size = Size(size.width, size.height),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                labels.forEach {
                    Text(it, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGreenStatsPageWithHeader() {
    GreenStatsPageWithHeader()
}