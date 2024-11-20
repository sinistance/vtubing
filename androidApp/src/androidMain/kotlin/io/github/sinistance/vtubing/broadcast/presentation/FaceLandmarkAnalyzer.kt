package io.github.sinistance.vtubing.broadcast.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FaceLandmarkAnalyzer(
    private val context: Context,
    private val onFaceLandmarksDetected: (FaceLandmarkerResult) -> Unit
) : ImageAnalysis.Analyzer {
    private val faceLandmarker: FaceLandmarker by lazy {
        val baseOptions = BaseOptions
            .builder()
            .setDelegate(Delegate.GPU)
            .setModelAssetPath("face_landmarker.task")
            .build()
        val options = FaceLandmarker.FaceLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setNumFaces(1)
            .setMinFaceDetectionConfidence(0.5f)
            .setMinTrackingConfidence(0.5f)
            .setMinFacePresenceConfidence(0.5f)
            .setResultListener { result, _ ->
                onFaceLandmarksDetected(result)
            }
            .setErrorListener {
//                it.printStackTrace()
            }
            .build()

        FaceLandmarker.createFromOptions(context, options)
    }

    override fun analyze(imageProxy: ImageProxy) {
        val rotatedBitmap = imageProxy.toBitmap().rotate(imageProxy.imageInfo.rotationDegrees)
        val mpImage = BitmapImageBuilder(rotatedBitmap).build()

        try {
//            val result =
            faceLandmarker.detectAsync(mpImage, System.currentTimeMillis())
//            result?.let { onFaceLandmarksDetected(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            imageProxy.close()
        }
    }

    private fun Bitmap.rotate(degrees: Int): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }
}