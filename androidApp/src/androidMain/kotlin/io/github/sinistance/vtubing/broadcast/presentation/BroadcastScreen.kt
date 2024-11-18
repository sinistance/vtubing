package io.github.sinistance.vtubing.broadcast.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import com.live2d.utils.GLRenderer
import com.live2d.utils.LAppDelegate
import org.koin.androidx.compose.koinViewModel

@Composable
fun BroadcastScreen(
    viewModel: BroadcastViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    BroadcastContentScreen()
}

@Composable
fun BroadcastContentScreen(
    uiState: BroadcastUiState = BroadcastUiState(),
) {
    val context = LocalContext.current
    val glSurfaceView = remember {
        GLSurfaceView(context).apply {
            setEGLContextClientVersion(2)
            setRenderer(GLRenderer())
            renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        }
    }

    LifecycleStartEffect(glSurfaceView) {
        LAppDelegate.getInstance().onStart(context as Activity)
        onStopOrDispose {
            LAppDelegate.getInstance().onStop()
        }
    }

    LifecycleResumeEffect(glSurfaceView) {
        glSurfaceView.onResume()
        onPauseOrDispose {
            glSurfaceView.onPause()
        }
    }

    DisposableEffect(glSurfaceView) {
        onDispose {
            LAppDelegate.getInstance().onDestroy()
        }
    }

    Live2dView(glSurfaceView)
}

@SuppressLint("ClickableViewAccessibility")
@Composable
private fun Live2dView(glSurfaceView: GLSurfaceView){
    AndroidView(
        factory = { _ ->
            glSurfaceView.apply {
                setOnTouchListener { _, event ->
                    val pointX = event.x
                    val pointY = event.y
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> LAppDelegate.getInstance()
                            .onTouchBegan(pointX, pointY)

                        MotionEvent.ACTION_UP -> LAppDelegate.getInstance()
                            .onTouchEnd(pointX, pointY)

                        MotionEvent.ACTION_MOVE -> LAppDelegate.getInstance()
                            .onTouchMoved(pointX, pointY)
                    }
                    return@setOnTouchListener true
                }
            }
        },
    )
}