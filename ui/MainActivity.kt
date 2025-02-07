package ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import ui.components.AnimatedBackground
import ui.components.ChronalGridOverlay
import ui.components.TemporalSlider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainContent()
                }
            }
        }
    }

    @Composable
    fun MainContent() {
        val sliderPosition = remember { MutableStateFlow(50f) }
        val recyclerView = remember { RecyclerView(this@MainActivity) }

        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedBackground(sliderPosition = sliderPosition.collectAsState().value)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TemporalSlider(
                    sliderPosition = sliderPosition,
                    onValueChange = { newValue ->
                        sliderPosition.value = newValue
                        // Update AnimatedBackground and ChronalGridOverlay based on slider value
                        // This ensures synchronized interactions between components
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ChronalGridOverlay(
                    modifier = Modifier.weight(1f),
                    recyclerView = recyclerView,
                    sliderPosition = sliderPosition.collectAsState().value
                )
            }
        }
    }
}