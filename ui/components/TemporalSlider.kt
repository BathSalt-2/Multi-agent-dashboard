package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TemporalSlider(
    modifier: Modifier = Modifier,
    sliderPosition: StateFlow<Float>,
    onValueChange: (Float) -> Unit
) {
    val currentPosition = sliderPosition.collectAsState()

    Slider(
        value = currentPosition.value,
        onValueChange = onValueChange,
        valueRange = 0f..100f,
        colors = SliderDefaults.colors(
            thumbColor = Color.Cyan,
            activeTrackColor = Color.Magenta
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// Usage example
@Composable
fun TemporalSliderExample() {
    val sliderPosition = remember { MutableStateFlow(50f) }

    TemporalSlider(
        sliderPosition = sliderPosition,
        onValueChange = { newValue ->
            sliderPosition.value = newValue
            // Update visual elements or perform actions based on newValue
        }
    )
}
