package io.github.sinistance.vtubing.broadcast.presentation

import android.app.Activity
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mediapipe.formats.proto.LandmarkProto
import com.google.mediapipe.tasks.components.containers.Category
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import com.live2d.utils.GLRenderer
import com.live2d.utils.LAppDelegate
import com.live2d.utils.LAppLive2DManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

    var latestFaceLandmarks by remember { mutableStateOf<FaceLandmarkerResult?>(null) }

    Box {
        AndroidView(
            factory = { _ -> glSurfaceView },
        )
//        CameraPreview(
//            modifier = Modifier.size(200.dp)
//        ) {}
        CameraFaceTrackingScreen {
            latestFaceLandmarks = it
        }

        latestFaceLandmarks?.let { landmarks ->
            Live2DAvatarController(landmarks)
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onFrameCaptured: (ImageProxy) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    ) { view ->
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(view.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also {
                    it.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->
                        onFrameCaptured(imageProxy)
                        imageProxy.close()
                    }
                }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}

@Composable
fun CameraFaceTrackingScreen(
    onFaceLandmarksReceived: (FaceLandmarkerResult) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

//    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

//    when (val status = cameraPermissionState.status) {
//        PermissionStatus.Granted -> {
            var previewView by remember { mutableStateOf<PreviewView?>(null) }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.Main) {
                    setupCamera(context, lifecycleOwner, onFaceLandmarksReceived) {
                        previewView = it
                    }
                }
            }

//            Box(modifier = Modifier.fillMaxSize()) {
                previewView?.let { preview ->
                    AndroidView(
//                        modifier = Modifier.fillMaxSize(),
                        modifier = Modifier.size(200.dp),
                        factory = { preview }
                    )
                }
//            }
//        }
//
//        is PermissionStatus.Denied -> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Camera permission is required")
//                Button(
//                    onClick = { cameraPermissionState.launchPermissionRequest() },
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text("Request Permission")
//                }
//            }
//        }
//    }
}

private fun setupCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onFaceLandmarksReceived: (FaceLandmarkerResult) -> Unit,
    onPreviewReady: (PreviewView) -> Unit
) {
    val previewView = PreviewView(context)
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    Executors.newSingleThreadExecutor(),
                    FaceLandmarkAnalyzer(context) { faceLandmarks ->
                        onFaceLandmarksReceived(faceLandmarks)
                    }
                )
            }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
            onPreviewReady(previewView)
        } catch (exc: Exception) {
            Toast.makeText(context, "Camera setup failed", Toast.LENGTH_SHORT).show()
        }
    }, ContextCompat.getMainExecutor(context))
}

@Composable
fun Live2DAvatarController(faceLandmarks: FaceLandmarkerResult) {
    // Placeholder for Live2D avatar parameter manipulation
    // You'll need to integrate with Live2D Cubism SDK
    LaunchedEffect(faceLandmarks) {
        if (faceLandmarks.faceLandmarks().isNotEmpty()) {
            val landmarks = faceLandmarks.faceLandmarks()[0]

            // https://storage.googleapis.com/mediapipe-assets/documentation/mediapipe_face_landmark_fullsize.png
            // Left eye landmarks
            val leftUpperEye = landmarks[159]  // Upper eyelid
            val leftLowerEye = landmarks[145]  // Lower eyelid
            val leftEyeDistance = abs(leftUpperEye.y() - leftLowerEye.y())

            // Right eye landmarks
            val rightUpperEye = landmarks[386]  // Upper eyelid
            val rightLowerEye = landmarks[374]  // Lower eyelid
            val rightEyeDistance = abs(rightUpperEye.y() - rightLowerEye.y())

            val leftEyeOpenness = when {
                leftEyeDistance < 0.01 -> false
                else -> true
            }

            val rightEyeOpenness = when {
                rightEyeDistance < 0.01 -> false
                else -> true
            }

            println("eyeROpen: $rightEyeOpenness, eyeLOpen: $leftEyeOpenness")

            LAppLive2DManager.getInstance().getModel(0).update(rightEyeOpenness, leftEyeOpenness)
        }
    }
}

fun calculateEyeOpenness(upperEyelid: Float, lowerEyelid: Float): Float {
    // Calculate vertical distance between upper and lower eyelids
    val verticalDistance = abs(upperEyelid - lowerEyelid)

    // Normalize and invert (closed = 0, fully open = 1)
    return 1 - verticalDistance
}