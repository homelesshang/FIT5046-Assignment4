package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.a2.R
import java.time.LocalDate

import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.foundation.text.BasicTextField


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Member Login",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF2E8B57),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username TextField with leading icon
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

        // Password TextField with leading icon
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E8B57),
                focusedLabelColor = Color(0xFF2E8B57)
            ),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button
        Button(
            onClick = { /* Handle login logic here */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAF0D1)) // 浅绿色
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color(0xFF2E8B57), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Sign-up Link (TextButton)
        TextButton(onClick = { /* Handle navigation to sign-up */ }) {
            Text("Don't have an account? Sign up", color = Color(0xFF2E8B57))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Google Login Button
        Button(
            onClick = { /* Handle Google login logic here */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4)) // Google Blue
        ) {
            // Google logo icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.google), // 使用Google图标资源
                    contentDescription = "Google Login",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign in with Google", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}

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


@Composable
fun SplashScreenPreviewOnly() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)), // 淡绿色背景
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "\uD83C\uDFC3\u200D♀\uFE0F FitNest \uD83C\uDFCB\uFE0F\u200D♂\uFE0F",
                fontSize = 32.sp,
                color = Color(0xFF2E8B57), // 深绿色
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Move Freely. Live Naturally.",
                fontSize = 16.sp,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreenPreviewOnly()
}



@Composable
fun EditProfileScreen2(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("John Doe") }
    var birthday by remember { mutableStateOf("1990-01-01") }
    var region by remember { mutableStateOf("Melbourne, Australia") }
    val green = Color(0xFF2E8B57)

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
                        tint = green // ← 这里设置为淡绿色
                    )
                }
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        bottomBar = {
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = green)
            ) {
                Text("Save", color = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 上半区：头像 + 用户名
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(120.dp)
                ) {
                    // 头像
                    Image(
                        painter = painterResource(id = R.drawable.asd),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )

                    // 笔图标（编辑头像）
                    IconButton(
                        onClick = { /* 这里可以放置修改头像的逻辑 */ },
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile Picture",
                            tint = green
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 用户名
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = green, // 设为绿色
                    fontWeight = FontWeight.Bold // 加粗
                )
            }

            // 中间：表单输入
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // 修改名字
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

                Spacer(modifier = Modifier.height(16.dp))

                // 修改生日
                OutlinedTextField(
                    value = birthday,
                    onValueChange = { birthday = it },
                    label = { Text("Birthday") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = "Pick Date",
                            modifier = Modifier.size(24.dp),
                            tint = green
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = green,
                        unfocusedBorderColor = green,
                        focusedLabelColor = green,
                        cursorColor = green
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 修改区域
                OutlinedTextField(
                    value = region,
                    onValueChange = { region = it },
                    label = { Text("Region") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = green,
                        unfocusedBorderColor = green,
                        focusedLabelColor = green,
                        cursorColor = green
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScree2n() {
    EditProfileScreen2()
}

