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
    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    private fun createGridVertices(): FloatArray {
        val gridSize = 10
        val step = 0.2f
        val vertices = mutableListOf<Float>()

        for (i in -gridSize..gridSize) {
            val position = i * step
            // Horizontal lines
            vertices.add(-gridSize * step)
            vertices.add(position)
            vertices.add(0f)
            vertices.add(gridSize * step)
            vertices.add(position)
            vertices.add(0f)

            // Vertical lines
            vertices.add(position)
            vertices.add(-gridSize * step)
            vertices.add(0f)
            vertices.add(position)
            vertices.add(gridSize * step)
            vertices.add(0f)
        }

        return vertices.toFloatArray()
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
        val vertexShaderCode = """
            attribute vec4 vPosition;
            uniform mat4 uMVPMatrix;
            uniform float uScrollOffset;
            void main() {
                vec4 warpedPosition = vPosition;
                warpedPosition.y += sin(warpedPosition.x * 10.0 + uScrollOffset * 0.1) * 0.1;
                gl_Position = uMVPMatrix * warpedPosition;
            }
        """.trimIndent()

        val fragmentShaderCode = """
            precision mediump float;
            void main() {
                gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0); // Green grid lines
            }
        """.trimIndent()

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        shaderProgram = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        // Use scrollOffset to adjust the grid warping effect
        GLES20.glUseProgram(shaderProgram)

        val positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition")
        val mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix")
        val scrollOffsetHandle = GLES20.glGetUniformLocation(shaderProgram, "uScrollOffset")

        GLES20.glUniform1f(scrollOffsetHandle, scrollOffset)

        // Set up the grid vertices and draw the grid
        val gridVertices = createGridVertices()
        val vertexBuffer = ByteBuffer.allocateDirect(gridVertices.size * 4).apply {
            order(ByteOrder.nativeOrder())
        }.asFloatBuffer().apply {
            put(gridVertices)
            position(0)
        }

        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        val mvpMatrix = FloatArray(16).apply {
            Matrix.setIdentityM(this, 0)
        }
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_LINES, 0, gridVertices.size / 3)

        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }
}