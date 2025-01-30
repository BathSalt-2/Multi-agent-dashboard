package ui.components

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

@Composable
fun ChronalGridOverlay(modifier: Modifier = Modifier, recyclerView: RecyclerView) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            ChronalGridGLSurfaceView(context).apply {
                setRecyclerView(recyclerView)
            }
        }
    )
}

class ChronalGridGLSurfaceView(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {
    private val renderer: ChronalGridRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = ChronalGridRenderer()
        setRenderer(renderer)
    }

    fun setRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                renderer.scrollOffset = dy.toFloat()
            }
        })
    }
}

class ChronalGridRenderer : GLSurfaceView.Renderer {
    var scrollOffset: Float = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        // Initialize shaders and other OpenGL resources here
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        // Use scrollOffset to adjust the grid warping effect
        // Draw the 3D grid here
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }
}
