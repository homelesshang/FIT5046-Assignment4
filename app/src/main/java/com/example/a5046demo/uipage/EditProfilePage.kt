package com.example.a5046demo.uipage

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.a5046demo.R

import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import com.example.a5046demo.viewmodel.UserProfileViewModel
import java.util.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import com.example.a5046demo.viewmodel.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    userProfileViewModel: UserProfileViewModel,
    onBackClick: () -> Unit = {}
) {
    val profile by userProfileViewModel.userProfile.collectAsState(initial = null)
    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    var name by remember { mutableStateOf(profile!!.nickname) }
    var birthday by remember { mutableStateOf(profile!!.birthday ?: "") }
    var region by remember { mutableStateOf(profile!!.region ?: "") }
    var weight by remember { mutableStateOf(profile!!.weight?.toString() ?: "") }
    var height by remember { mutableStateOf(profile!!.height?.toString() ?: "") }
    val green = Color(0xFF2E8B57)
    val context = LocalContext.current


    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = green
                    )
                }
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleLarge,
                    color = green,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { innerPadding ->
            // Form Section
            var showDatePicker by remember { mutableStateOf(false) }
            val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = Instant.now().toEpochMilli()
            )

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.size(120.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.asd),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )


                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = green,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            unfocusedBorderColor = green,
                            focusedLabelColor = green,
                            cursorColor = green
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = birthday,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Birthday") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Pick Date",
                                modifier = Modifier
                                    .clickable { showDatePicker = true }
                                    .size(24.dp),
                                tint = green
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            unfocusedBorderColor = green,
                            focusedLabelColor = green,
                            cursorColor = green
                        )
                    )
                }



                item {
                    val cities = listOf("Melbourne", "Sydney", "Brisbane", "Perth", "Adelaide", "Canberra", "Hobart", "Darwin")
                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = region,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Region") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = green,
                                unfocusedBorderColor = green,
                                focusedLabelColor = green,
                                cursorColor = green
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            cities.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city) },
                                    onClick = {
                                        region = city
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            unfocusedBorderColor = green,
                            focusedLabelColor = green,
                            cursorColor = green
                        )
                    )
                }

                item {
                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Height") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = green,
                            unfocusedBorderColor = green,
                            focusedLabelColor = green,
                            cursorColor = green
                        )
                    )
                }

                item {
                    Button(
                        onClick = {
                            val isNameValid = name.all { it.isLetter() } && name.isNotBlank()
                            val isWeightValid = weight.toFloatOrNull() != null
                            val isHeightValid = height.toFloatOrNull() != null

                            if (!isNameValid || !isWeightValid || !isHeightValid) {
                                if(!isNameValid)
                                {
                                    Toast.makeText(
                                        context,
                                        "Please fix the name input errors before saving.Name could only include letters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else if(!isWeightValid)
                                {
                                    Toast.makeText(
                                        context,
                                        "Please fix the weight input errors before saving.Weight could only be floats.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else if(!isHeightValid)
                                {
                                    Toast.makeText(
                                        context,
                                        "Please fix the height input errors before saving.Height could only be floats.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                return@Button
                            }
                            else{
                                val updatedProfile = profile!!.copy(
                                    nickname = name,
                                    birthday = birthday,
                                    weight = weight.toFloatOrNull(),
                                    height = height.toFloatOrNull(),
                                    region = region
                                )
                                userProfileViewModel.updateProfile(updatedProfile)
                                onBackClick()
                            }

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                            .height(50.dp)
                    ) {
                        Text("Save Changes", color = Color.White)
                    }
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                birthday = formatter.format(Date(millis))
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



            }
        }



