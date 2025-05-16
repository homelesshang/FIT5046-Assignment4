package com.example.a5046demo.uipage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    onConfirm: () -> Unit = {}
) {
    var selectedIntensity by remember { mutableStateOf("Medium") }
    var dateInput by remember { mutableStateOf("") }
    val exerciseTypes = listOf("Cardio", "Strength", "Yoga", "HIIT", "Pilates")
    var expanded by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf(exerciseTypes[0]) }

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
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "🔥 Stay consistent and track your daily workout to reach your goals!",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF444444),
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
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
                            exerciseTypes.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        selectedExercise = selectionOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = dateInput,
                    onValueChange = { dateInput = it },
                    label = { Text("📅 Date") },
                    placeholder = { Text("e.g., 2025-04-16") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Duration
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
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
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
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
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
            ) {
                Text("✅ CONFIRM", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecordScreen() {
    RecordScreen()
}