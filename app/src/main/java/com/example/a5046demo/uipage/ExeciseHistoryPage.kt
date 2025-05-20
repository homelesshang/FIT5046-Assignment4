package com.example.a5046demo.uipage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.data.ExerciseRecord


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseHistoryScreen(viewModel: ExerciseViewModel,navController: NavHostController) {

    val records by viewModel.allRecords.collectAsState(initial = emptyList())
    var editingRecord by remember { mutableStateOf<ExerciseRecord?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("📖 Exercise History", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E8B57))
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("📊 View Recent Progress") },
                onClick = {
                    navController.navigate("progress")
                },
                icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
                containerColor = Color(0xFF2E8B57),
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(records, key = { it.id }) { record ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📅 ${record.date}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("🏃 Type: ${record.exerciseType}")
                        Text("⏱ Duration: ${record.duration} min")
                        Text("💪 Intensity: ${record.intensity}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            TextButton(onClick = {
                                // 打开编辑对话框
                                editingRecord = record
                            }) {
                                Text("✏️ Edit")
                            }

                            TextButton(onClick = {
                                viewModel.deleteRecord(record)
                            }) {
                                Text("🗑 Delete", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }

    // 👇 编辑弹窗
    if (editingRecord != null) {
        EditRecordDialog(
            record = editingRecord!!,
            onDismiss = { editingRecord = null },
            onConfirm = { updated ->
                viewModel.updateRecord(updated)
                editingRecord = null
            }
        )
    }
}

@Composable
fun EditRecordDialog(
    record: ExerciseRecord,
    onDismiss: () -> Unit,
    onConfirm: (ExerciseRecord) -> Unit
) {
    var updatedType by remember { mutableStateOf(record.exerciseType) }
    var updatedDuration by remember { mutableStateOf(record.duration.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val updated = record.copy(
                    exerciseType = updatedType,
                    duration = updatedDuration.toIntOrNull() ?: record.duration
                )
                onConfirm(updated)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Record") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = updatedType,
                    onValueChange = { updatedType = it },
                    label = { Text("Exercise Type") }
                )
                OutlinedTextField(
                    value = updatedDuration,
                    onValueChange = { updatedDuration = it },
                    label = { Text("Duration (minutes)") },

                )
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewExerciseHistoryCrudScreen() {
//    ExerciseHistoryScreen(viewModel = /* 传一个 mock ViewModel 或用 fake 数据 */)
//}