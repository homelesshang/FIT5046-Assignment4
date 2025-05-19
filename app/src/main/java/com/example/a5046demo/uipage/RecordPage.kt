package com.example.a5046demo.uipage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5046demo.data.ExerciseRecord
import com.example.a5046demo.viewmodel.ExerciseViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale
import java.util.Date
import androidx.compose.ui.res.painterResource
import com.example.a5046demo.R
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(viewModel: ExerciseViewModel, onConfirm: () -> Unit = {}) {

    val exerciseTypes = listOf("Cardio", "Strength", "Yoga", "HIIT", "Pilates")
    var selectedExercise by remember { mutableStateOf(exerciseTypes[0]) }
    var expanded by remember { mutableStateOf(false) }
    var dateInput by remember { mutableStateOf("") }
    var durationInput by remember { mutableStateOf("") }
    var selectedIntensity by remember { mutableStateOf("Medium") }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    Log.d("RecordScreen", "Inserting record with userId = $userId")
    val records by viewModel.allRecords.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "📋 Log Your Progress",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E8B57))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "🔥 Stay consistent and track your daily workout to reach your goals!",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF444444),
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    )

                    // Exercise Type
                    Column {
                        Text("🏃 Exercise Type", fontSize = 16.sp, color = Color.Gray)
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedExercise,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Choose Exercise") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                exerciseTypes.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedExercise = option
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Date

                    var showDatePicker by remember { mutableStateOf(false) }
                    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = Instant.now().toEpochMilli()
                    )

                    OutlinedTextField(
                        value = dateInput,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("📅 Date") },
                        placeholder = { Text("e.g., 2025-04-16") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Pick Date",
                                modifier = Modifier
                                    .clickable { showDatePicker = true }
                                    .size(24.dp),
                                tint = Color(0xFF2E8B57)
                            )
                        },
                        singleLine = true
                    )

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    val millis = datePickerState.selectedDateMillis
                                    if (millis != null) {
                                        dateInput = formatter.format(Date(millis))
                                    }
                                    showDatePicker = false
                                }) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text("Cancel")
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    // Duration
                    OutlinedTextField(
                        value = durationInput,
                        onValueChange = { durationInput = it },
                        label = { Text("⏱ Duration (minutes)") },
                        placeholder = { Text("e.g., 30") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Intensity
                    Column {
                        Text(
                            "💪 Training Intensity",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            listOf("Low 🟢", "Medium 🟡", "High 🔴").forEach { label ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    RadioButton(
                                        selected = selectedIntensity in label,
                                        onClick = { selectedIntensity = label.split(" ")[0] },
                                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2E8B57))
                                    )
                                    Text(
                                        text = label,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (dateInput.isNotBlank() && durationInput.isNotBlank()) {
                                val record = ExerciseRecord(
                                    exerciseType = selectedExercise,
                                    date = dateInput,
                                    duration = durationInput.toIntOrNull() ?: 0,
                                    intensity = selectedIntensity,
                                    userId = userId
                                )
                                viewModel.insertRecord(record)


                                dateInput = ""
                                durationInput = ""
                                selectedExercise = exerciseTypes[0]
                                selectedIntensity = "Medium"

                                onConfirm()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
                    ) {
                        Text("✅ CONFIRM", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRecordScreen() {
//    RecordScreen()
//}