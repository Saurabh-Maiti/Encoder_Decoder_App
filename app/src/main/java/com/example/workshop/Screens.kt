package com.example.workshop

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Encoder_Decoder() {
    var input by remember { mutableStateOf("") }
    var encodedOutput by remember { mutableStateOf("") }
    var decodedOutput by remember { mutableStateOf("") }
    var decodeInput by remember { mutableStateOf("") } // Separate state for decode input
    val context = LocalContext.current // Context for Sharing

    // Function to share text
    fun shareText(text: String) {
        if (text.isNotEmpty()) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    Box(modifier = Modifier.fillMaxSize().clickable { }) {
        val bg = painterResource(id = R.drawable.bg2)
        Image(
            painter = bg,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.2f),
            contentScale = ContentScale.Crop // Full Screen
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                "Encoder/Decoder App",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )

            // Input Field for Encoding
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter a String to Encode") },
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            )

            // Encode Button
            Button(
                onClick = {
                    encodedOutput = input.map { (it.toInt() + 1).toChar() }.joinToString("") // Increase ASCII value by 1
                    input = "" // Clear input after encoding
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Text("Encode")
            }

            // Encoded Output (Separate Box, Not Inside Decode Input)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Encoded: $encodedOutput",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(8.dp)
                )
                Button(onClick = { shareText(encodedOutput) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) { // Share Encoded Text
                    Text("Share")
                }
            }

            // Input Field for Decoding (User Can Enter Manually)
            OutlinedTextField(
                value = decodeInput,
                onValueChange = { decodeInput = it },
                label = { Text("Enter a String to Decode") },
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            )

            // Decode Button
            Button(
                onClick = {
                    decodedOutput = decodeInput.map { (it.toInt() - 1).toChar() }.joinToString("") // Decrease ASCII value by 1
                    decodeInput = "" // Clear input after decoding
                },
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Decode")
            }

            // Decoded Output
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Decoded: $decodedOutput",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(8.dp)
                )
                Button(onClick = { shareText(decodedOutput) },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) { // Share Decoded Text
                    Text("Share")
                }
            }

        }
    }
}
