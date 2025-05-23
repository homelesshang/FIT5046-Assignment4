package com.example.a5046demo.uipage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a5046demo.R
import com.example.a5046demo.viewmodel.AuthViewModel
import com.example.a5046demo.viewmodel.ExerciseViewModel
import com.example.a5046demo.viewmodel.UserProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    userProfileViewModel: UserProfileViewModel,
    exerciseViewModel: ExerciseViewModel,
    burned: String = "273 kcal"
) {
    // Observe the current user profile
    val profile by userProfileViewModel.userProfile.collectAsState(initial = null)

    // Show loading spinner if profile is not yet loaded
    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Extract user profile data
    val p = profile ?: return
    val nickname = p.nickname
    val weightValue = p.weight ?: 0f
    val heightValue = p.height ?: 0f

    // Format data for display
    val weightDisplay = "$weightValue kg"
    val heightDisplay = "$heightValue cm"
    val bmi = if (heightValue > 0f) {
        val h = heightValue / 100f
        String.format("%.1f", weightValue / (h * h))
    } else {
        "0.0"
    }
    var region by remember { mutableStateOf(profile!!.region) }

    // Calculate today’s total exercise duration
    val allRecords by exerciseViewModel.allRecords.collectAsState(initial = emptyList())
    val today = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
    val todayTotalDuration = allRecords
        .filter { it.date == today }
        .sumOf { it.duration }

    // Screen UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    // Navigate to edit profile screen
                    TextButton(onClick = { navController.navigate("edit_profile") }) {
                        Text("Edit", color = Color(0xFF2E8B57), fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile image
            Image(
                painter = painterResource(id = R.drawable.asd),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            // Name and location
            Text(nickname, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
            Text(region ?: "", fontSize = 14.sp, color = Color.Gray)

            // Info Cards
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Weight", weightDisplay, "⚖️") {
                            navController.navigate("edit_profile")
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Height", heightDisplay, "📏") {
                            navController.navigate("edit_profile")
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("Today Exercise time", "$todayTotalDuration min", "⏱️")
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        InfoCardGreen("BMI", bmi, "📊")
                    }
                }

                Spacer(modifier = Modifier.height(128.dp))

                // Log out button
                Button(
                    onClick = {
                        authViewModel.signOut()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun InfoCardGreen(
    title: String,
    value: String,
    icon: String,
    onEditClick: (() -> Unit)? = null
) {
    // Reusable card for showing user info
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF0))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(icon, fontSize = 20.sp)
                Text(title, fontSize = 14.sp, color = Color.Gray)
                Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2E8B57))
            }

            // Optional edit icon
            if (onEditClick != null) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $title",
                        tint = Color(0xFF2E8B57)
                    )
                }
            }
        }
    }
}
