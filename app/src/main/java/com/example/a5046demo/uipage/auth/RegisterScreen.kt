package com.example.a5046demo.uipage.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a5046demo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "New Member Register",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF2E8B57),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },


            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E8B57),
                focusedLabelColor = Color(0xFF2E8B57)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.eye), // Replace with your icon resource
                    contentDescription = "Password Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E8B57),
                focusedLabelColor = Color(0xFF2E8B57)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.eye), // Replace with your icon resource
                    contentDescription = "Password Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E8B57),
                focusedLabelColor = Color(0xFF2E8B57)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date of Birth Input with Calendar Icon
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { /* No direct input, only selection */ },
            label = { Text("Date of Birth") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { /* 日期选择逻辑可以放这里 */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar_icon), // 使用你的日历图标资源
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E8B57),
                focusedLabelColor = Color(0xFF2E8B57)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = { /* 处理注册逻辑 */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAF0D1))
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
        Divider(color = Color(0xFF2E8B57), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Login Navigation Link
        TextButton(onClick = { /* 处理跳转到登录界面 */ }) {
            Text("Already have an account? Login", color = Color(0xFF2E8B57))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen()
}