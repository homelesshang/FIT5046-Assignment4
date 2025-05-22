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
import androidx.compose.material.rememberScaffoldState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(viewModel: ExerciseViewModel, onConfirm: () -> Unit = {}) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val exerciseTypes = listOf("Cardio", "Strength", "Yoga", "HIIT", "Pilates")
    var selectedExercise by remember { mutableStateOf(exerciseTypes[0]) }
    var expanded by remember { mutableStateOf(false) }
    var dateInput by remember { mutableStateOf("") }
    var durationInput by remember { mutableStateOf("") }
    var selectedIntensity by remember { mutableStateOf("Medium") }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    Log.d("RecordScreen", "Inserting record with userId = $userId")
    val records by viewModel.allRecords.collectAsState(initial = emptyList())
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

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
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color(
                                                0xFF2E8B57
                                            )
                                        )
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
                            errorMessage = null

                            if (dateInput.isBlank()) {
                                errorMessage = "❗ Please select a date"
                            } else if (durationInput.isBlank()) {
                                errorMessage = "❗ Duration is required"
                            } else if (durationInput.toIntOrNull() == null || durationInput.toInt() <= 0) {
                                errorMessage = "❗ Duration must be a valid number"
                            }

                            if (errorMessage != null) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(errorMessage!!)
                                }
                                return@Button
                            }

                            try {
                                val duration = durationInput.toInt()
                                val record = ExerciseRecord(
                                    exerciseType = selectedExercise,
                                    date = dateInput,
                                    duration = duration,
                                    intensity = selectedIntensity,
                                    userId = userId
                                )

                                viewModel.insertRecord(record)


                                val intensityMultiplier = when (selectedIntensity.lowercase()) {
                                    "low" -> 3
                                    "medium" -> 5
                                    "high" -> 8
                                    else -> 4
                                }

                                val typeMultiplier = when (selectedExercise.lowercase()) {
                                    "cardio" -> 1.2f
                                    "strength" -> 1.0f
                                    "yoga" -> 0.8f
                                    "hiit" -> 1.5f
                                    else -> 1.0f
                                }

                                val calories = (duration * intensityMultiplier * typeMultiplier).toInt()

                                val intensityIndex = when (selectedIntensity.lowercase()) {
                                    "low" -> 0
                                    "medium" -> 1
                                    "high" -> 2
                                    else -> 1
                                }

                                viewModel.logExerciseToFirebase(
                                    uid = userId,
                                    date = dateInput.replace("-", ""),
                                    duration = duration,
                                    calories = calories,
                                    intensityIndex = intensityIndex
                                )


                                dateInput = ""
                                durationInput = ""
                                selectedExercise = "Cardio"
                                selectedIntensity = "Medium"

                                onConfirm()
                            } catch (e: Exception) {
                                Log.e("RecordScreen", "Failed to insert record: ${e.message}", e)
                                scope.launch {
                                    snackbarHostState.showSnackbar("⚠️ Failed to save record.")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
                    ) {
                        Text(
                            "✅ CONFIRM",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
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