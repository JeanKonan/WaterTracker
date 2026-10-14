package com.example.watertracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watertracker.ui.theme.WaterTrackerTheme

private const val DAILY_GOAL = 8

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    WaterTrackerScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun WaterTrackerScreen(
    innerPadding: PaddingValues = PaddingValues()
) {
    var glasses by remember { mutableIntStateOf(0) }

    val progress = (glasses.toFloat() / DAILY_GOAL)
        .coerceAtMost(1f)

    val blue = Color(0xFF2878D0)
    val red = Color(0xFFEF5350)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "💧",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Water Tracker",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Stay hydrated, stay healthy!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF4FF)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$glasses / $DAILY_GOAL",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = blue
                )

                Text(
                    text = "glasses of water today",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = blue,
                    trackColor = Color(0xFFD0E3F7)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (glasses >= DAILY_GOAL) {
                        "Daily goal completed!"
                    } else {
                        "${(progress * 100).toInt()}% of your daily goal"
                    },
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                if (glasses < DAILY_GOAL) {
                    glasses++
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            enabled = glasses < DAILY_GOAL
        ) {
            Text(
                text = if (glasses >= DAILY_GOAL) {
                    "Daily Goal Reached!"
                } else {
                    "+ Add a Glass of Water"
                },
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                if (glasses > 0) glasses--
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = glasses > 0
        ) {
            Text("Undo Last Glass")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                glasses = 0
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = red
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = glasses > 0
        ) {
            Text("Reset Today's Intake")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterTrackerPreview() {
    WaterTrackerTheme {
        WaterTrackerScreen()
    }
}

