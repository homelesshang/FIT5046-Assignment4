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
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.filled.Cached
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.clickable
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a5046demo.data.ExerciseRecord
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed // only if you use them
import androidx.compose.foundation.lazy.rememberLazyListState
import com.example.a5046demo.viewmodel.UserProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenStatsPageWithHeader(viewModel: ExerciseViewModel,
                             userViewModel: UserProfileViewModel,
                             onClose: () -> Unit = {}) {

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    LaunchedEffect(userId) {
        viewModel.loadWeeklyStats(userId)
    }
    val recordList by viewModel.allRecords.collectAsState(initial = emptyList())
    val recordMap = recordList.associateBy { it.date }
    val weeklyStats = viewModel.weeklyStats.collectAsState().value

    val fullWeekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    val todayIndex = java.time.LocalDate.now().dayOfWeek.value % 7
    val todayIndexAdjusted = if (todayIndex == 0) 6 else todayIndex - 1
    val recentStats = weeklyStats

    val weights = recentStats.map { it.stat.weight }
    val weightLabels = recentStats.map { it.label }

    val calories = weeklyStats.map { it.stat.totalCaloriesBurned.toFloat() }
    val durations = weeklyStats.map { it.stat.totalExerciseTime.toFloat() }
    val labels = weeklyStats.map { it.label }





// Generate full week with fallbacks
    val completeRecords = fullWeekDays.map { day ->
    recordMap[day] ?: ExerciseRecord(
            id = 0, // won't be used
            exerciseType = "",
            date = day,
            duration = 0,
            intensity = "",
            userId = userId
        )
    }

    val userProfile by userViewModel.userProfile.collectAsState(initial = null)
    val baseWeight = userProfile?.weight ?: 0f


    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recent Progress",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF2E8B57),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF2E8B57)
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) //
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                Text(
                    text = "Your Weekly Summary",
                    fontSize = 14.sp,
                    color = Color(0xFF2E8B57)
                    //modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            item {
                OverviewStatCard(
                    icon = Icons.Default.MoreVert,
                    title = "Weight 🃏",
                    value = weights.lastOrNull({ it != 0f }) ?: 0f,
                    barData = weights,
                    unitA = "KG",
                    unitB = "LBS",
                    labels = weightLabels
                )
            }

            item {
                OverviewStatCard(
                    icon = Icons.Default.MoreVert,
                    title = "Calories 🔥",
                    value = calories.lastOrNull({ it != 0f }) ?: 0f,
                    barData = calories,
                    unitA = "Calories",
                    unitB = "KJ",
                    labels = labels
                )
            }

            item {
                OverviewStatCard(
                    icon = Icons.Default.MoreVert,
                    title = "Workout Time ⏱️",
                    value = durations.lastOrNull({ it != 0f }) ?: 0f,
                    barData = durations,
                    unitA = "Mins",
                    unitB = "Hours",
                    labels = labels
                )
            }
        }
    }
}

@Composable
fun OverviewStatCard(
    icon: ImageVector,
    title: String,
    value: Float,
    barData: List<Float>,
    labels: List<String>,
    unitA: String,
    unitB: String,
    containerColor: Color = Color(0xFFD0F0C0),
    barColor: Color = Color(0xFF2E8B57),
) {
    var useAltUnit by remember { mutableStateOf(false) }
    val max = barData.maxOrNull()?.takeIf { it > 0 } ?: 1f
    val displayedUnit = if (useAltUnit) unitB else unitA
    var showBack by remember { mutableStateOf(false) }

    val convertedValue = if (useAltUnit) {
        when (title) {
            "Weight 🃏" -> String.format("%.1f %s", value * 2.2f, unitB)
            "Workout Time ⏱️" -> String.format("%.1f %s", value / 60f, unitB)
            "Calories 🔥" -> String.format("%.0f %s", value * 4.184f, unitB)
            else -> String.format("%.1f %s", value, unitB)
        }
    } else {
        String.format("%.1f %s", value, unitA)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { showBack = !showBack },
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
                        IconButton(
                            onClick = { useAltUnit = !useAltUnit },
                            modifier = Modifier.pointerInput(32.dp) {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cached,
                                contentDescription = "Switch Unit",
                                tint = barColor
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontWeight = FontWeight.SemiBold, color = barColor)
                }
            }

            AnimatedContent(targetState = showBack, label = "CardFlip") { isBack ->
                if (isBack) {
                    Text(
                        text = when (title) {
                            "Weight 🃏" -> {
                                val totalLogs = barData.count { it > 0f }
                                if (totalLogs == 0) "Start logging today!"
                                else "You workout $totalLogs days week. Way to go king 👑"
                            }
                            "Calories 🔥" -> {
                                val totalCalories = barData.sum().toInt()
                                "You burned $totalCalories Calories this week 🔥"
                            }
                            "Workout Time ⏱️" -> {
                                val totalMinutes = barData.sum().toInt()
                                "You exercised $totalMinutes minutes this week 💪"
                            }
                            else -> "Keep up the good work!"
                        },
                        color = barColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = convertedValue,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = barColor
                        )
                        if (title == "Weight 🃏") {
                            AndroidView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                factory = { context ->
                                    LineChart(context).apply {
                                        val entries = barData.mapIndexed { index, value ->
                                            Entry(index.toFloat(), value)
                                        }

                                        val dataSet = LineDataSet(entries, "Weight").apply {
                                            color = android.graphics.Color.parseColor("#2E8B57")
                                            setCircleColor(android.graphics.Color.parseColor("#2E8B57"))
                                            valueTextColor = android.graphics.Color.TRANSPARENT
                                            lineWidth = 2f
                                            circleRadius = 3f
                                            mode = LineDataSet.Mode.LINEAR // STRAIGHT lines
                                        }

                                        data = LineData(dataSet)

                                        xAxis.apply {
                                            position = XAxis.XAxisPosition.BOTTOM
                                            valueFormatter = IndexAxisValueFormatter(labels)
                                            granularity = 1f
                                            setDrawGridLines(false)
                                            textColor = android.graphics.Color.GRAY
                                        }

                                        axisLeft.apply {
                                            axisMinimum = 0f // Start from 0
                                            setDrawGridLines(false)
                                            textColor = android.graphics.Color.GRAY
                                            textSize = 10f
                                            setLabelCount(3, true)
                                            axisLineColor = android.graphics.Color.TRANSPARENT
                                        }

                                        axisRight.isEnabled = false
                                        description.isEnabled = false
                                        legend.isEnabled = false

                                        setTouchEnabled(false)
                                        isDragEnabled = false
                                        setScaleEnabled(false)
                                        setPinchZoom(false)
                                        isDoubleTapToZoomEnabled = false

                                        animateX(1000)
                                        invalidate() // Forces redraw
                                    }
                                },update = { chart -> // ✅ this block runs every recomposition
                                    val entries = barData.mapIndexed { index, value ->
                                        Entry(index.toFloat(), value)
                                    }

                                    val dataSet = LineDataSet(entries, "Weight").apply {
                                        color = android.graphics.Color.parseColor("#2E8B57")
                                        setCircleColor(android.graphics.Color.parseColor("#2E8B57"))
                                        valueTextColor = android.graphics.Color.TRANSPARENT
                                        lineWidth = 2f
                                        circleRadius = 3f
                                        mode = LineDataSet.Mode.LINEAR
                                    }

                                    chart.data = LineData(dataSet)

                                    chart.xAxis.apply {
                                        position = XAxis.XAxisPosition.BOTTOM
                                        valueFormatter = IndexAxisValueFormatter(labels)
                                        granularity = 1f
                                        setDrawGridLines(false)
                                        textColor = android.graphics.Color.GRAY
                                    }

                                    chart.axisLeft.apply {
                                        axisMinimum = 0f
                                        setDrawGridLines(false)
                                        textColor = android.graphics.Color.GRAY
                                        textSize = 10f
                                        setLabelCount(3, true)
                                        axisLineColor = android.graphics.Color.TRANSPARENT
                                    }

                                    chart.axisRight.isEnabled = false
                                    chart.description.isEnabled = false
                                    chart.legend.isEnabled = false

                                    chart.setTouchEnabled(false)
                                    chart.isDragEnabled = false
                                    chart.setScaleEnabled(false)
                                    chart.setPinchZoom(false)
                                    chart.isDoubleTapToZoomEnabled = false

                                    chart.animateX(1000)
                                    chart.invalidate() // 🔁 force re-render
                                }
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    barData.forEach { item ->
                                        Box(
                                            modifier = Modifier
                                                .width(10.dp)
                                                .fillMaxHeight(item / max),
                                            contentAlignment = Alignment.BottomCenter
                                        ) {
                                            Canvas(modifier = Modifier.fillMaxSize()) {
                                                drawRoundRect(
                                                    color = barColor,
                                                    size = size,
                                                    cornerRadius = CornerRadius(6f, 6f)
                                                )
                                            }
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    labels.forEach {
                                        Text(it, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewGreenStatsPageWithHeader() {
//    GreenStatsPageWithHeader(ExerciseViewModel)
//}

