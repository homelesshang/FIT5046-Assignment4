//package com.example.a5046demo.uipage.auth
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginScreen() {
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Title
//        Text(
//            text = "Member Login",
//            style = MaterialTheme.typography.titleLarge,
//            color = Color(0xFF2E8B57),
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Username TextField with leading icon
//        OutlinedTextField(
//            value = username,
//            onValueChange = { username = it },
//            label = { Text("Username") },
//
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color(0xFF2E8B57),
//                focusedLabelColor = Color(0xFF2E8B57)
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Password TextField with leading icon
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            trailingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.eye), // Replace with your icon resource
//                    contentDescription = "Password Icon",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(24.dp)
//                )
//            },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = Color(0xFF2E8B57),
//                focusedLabelColor = Color(0xFF2E8B57)
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Login Button
//        Button(
//            onClick = { /* Handle login logic here */ },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAF0D1)) // 浅绿色
//        ) {
//            Text("Login")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Divider(color = Color(0xFF2E8B57), thickness = 1.dp)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Sign-up Link (TextButton)
//        TextButton(onClick = { /* Handle navigation to sign-up */ }) {
//            Text("Don't have an account? Sign up", color = Color(0xFF2E8B57))
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Google Login Button
//        Button(
//            onClick = { /* Handle Google login logic here */ },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)) // Google Blue
//        ) {
//            // Google logo icon
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    painter = painterResource(id = R.drawable.google), // 使用Google图标资源
//                    contentDescription = "Google Login",
//                    tint = Color.White,
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Sign in with Google", color = Color.White)
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen()
//}