package com.example.bt1tuan3_ltdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("components") { ComponentListScreen(navController) }
        composable("details/{component}") { backStackEntry ->
            DetailScreen(navController, backStackEntry.arguments?.getString("component") ?: "")
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 110.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.anh),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
        Text(
            text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate("components") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("I'm ready")
        }
    }
}

@Composable
fun ComponentListScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTitle()
        Spacer(modifier = Modifier.height(16.dp))
        ComponentCategories(navController)
    }
}

@Composable
fun HeaderTitle() {
    Text(
        text = "UI Components List",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2196F3),
        modifier = Modifier.padding(top = 40.dp, bottom = 16.dp)
    )
}

@Composable
fun ComponentCategories(navController: NavHostController) {
    Column {
        Text("Display", fontWeight = FontWeight.Bold)
        CategoryBox("Text", listOf("Displays text"), navController)
        CategoryBox("Image", listOf("Displays an image"), navController)
        Text("Input", fontWeight = FontWeight.Bold)
        CategoryBox("TextField", listOf("Input field for text"), navController)
        CategoryBox("PasswordField", listOf("Input field for password"), navController)
        Text("Layout", fontWeight = FontWeight.Bold)
        CategoryBox("Column", listOf("Arranges elements vertically"), navController)
        CategoryBox("Row", listOf("Arranges elements horizontally"), navController)
    }
}

@Composable
fun CategoryBox(category: String, items: List<String>, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color(0x4D2196F3), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = category, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { component ->
            Text(
                text = component,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("details/$component") }
                    .padding(8.dp),
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, component: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Back Button and Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.chevronleft),
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "Text Detail",
                fontSize = 30.sp,
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            Spacer(modifier = Modifier.size(24.dp))
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 220.dp)
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            val styledText = buildAnnotatedString {
                append("The ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick ")
                }
                withStyle(style = SpanStyle(color = Color(0xFFAA6600), fontWeight = FontWeight.Bold)) {
                    append("Brown ")
                }
                append("fox jumps ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("over ")
                }
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("the lazy ")
                }
                append("dog.")
            }
            Text(text = styledText, fontSize = 40.sp)
        }
    }
}