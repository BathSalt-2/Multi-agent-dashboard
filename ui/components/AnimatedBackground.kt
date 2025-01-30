create a comprehensive  package ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sin

@Composable
fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for undulating timelines
    val timelineOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Animation for particle collisions
    val particleOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawUndulatingTimelines(timelineOffset.value)
        drawParticleCollisions(particleOffset.value)
    }
}

private fun DrawScope.drawUndulatingTimelines(offset: Float) {
    val amplitude = 50f
    val frequency = 0.1f
    val centerY = size.height / 2

    for (x in 0 until size.width.toInt() step 20) {
        val y = centerY + amplitude * sin(frequency * x + offset)
        drawLine(
            color = Color.Cyan,
            start = Offset(x.toFloat(), centerY),
            end = Offset(x.toFloat(), y),
            strokeWidth = 2f
        )
    }
}

private fun DrawScope.drawParticleCollisions(offset: Float) {
    val centerX = size.width / 2
    val centerY = size.height / 2

    drawCircle(
        color = Color.Magenta,
        radius = 10f + offset / 10,
        center = Offset(centerX + offset, centerY + offset),
        style = Stroke(width = 3f)
    )

    drawCircle(
        color = Color.Yellow,
        radius = 10f + offset / 10,
        center = Offset(centerX - offset, centerY - offset),
        style = Stroke(width = 3f)
    )
}
