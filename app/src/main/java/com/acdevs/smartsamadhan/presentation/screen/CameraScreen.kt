package com.acdevs.smartsamadhan.presentation.screen

import android.net.Uri
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider // for camera provider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CameraScreen(
    isHindi: Boolean,
    onBack: () -> Unit,
    onCapture: (Uri?) -> Unit,
    onGalleryClick: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    var flashEnabled by remember { mutableStateOf(false) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    val previewView = remember { PreviewView(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    // Bind camera when screen loads
    LaunchedEffect(cameraSelector, flashEnabled) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        try {
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
            // Set flash mode
            imageCapture?.flashMode =
                if (flashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
        } catch (e: Exception) {
            Log.e("Camera", "Binding failed", e)
        }
    }

    var isCapturing by remember { mutableStateOf(false) }
    val captureScale by animateFloatAsState(
        targetValue = if (isCapturing) 0.9f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "समस्या की फोटो लें" else "Take Issue Photo",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (isHindi) "Take Issue Photo" else "समस्या की फोटो लें",
                        color = Color(0xFF9CA3AF),
                        fontSize = 12.sp
                    )
                }

                IconButton(
                    onClick = { flashEnabled = !flashEnabled },
                    modifier = Modifier
                        .background(
                            if (flashEnabled) Color(0xFFFBBF24).copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.5f),
                            CircleShape
                        )
                        .size(48.dp)
                ) {
                    Text(
                        text = if (flashEnabled) "⚡" else "⚡︎",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            // Camera Preview with grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 140.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Real Camera Preview
                        AndroidView(
                            factory = { previewView },
                            modifier = Modifier.fillMaxSize()
                        )

                        // Grid overlay
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val width = size.width
                            val height = size.height
                            for (i in 1..2) {
                                drawLine(
                                    color = Color.White.copy(alpha = 0.3f),
                                    start = androidx.compose.ui.geometry.Offset(width * i / 3, 0f),
                                    end = androidx.compose.ui.geometry.Offset(width * i / 3, height),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                            for (i in 1..2) {
                                drawLine(
                                    color = Color.White.copy(alpha = 0.3f),
                                    start = androidx.compose.ui.geometry.Offset(0f, height * i / 3),
                                    end = androidx.compose.ui.geometry.Offset(width, height * i / 3),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .offset(y = (-50).dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gallery
            Card(
                modifier = Modifier
                    .size(56.dp)
                    .clickable { onGalleryClick() },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                ),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("🖼️", fontSize = 20.sp)
                }
            }

            // Capture
            Box(contentAlignment = Alignment.Center) {
                FloatingActionButton(
                    onClick = {
                        val photoFile = File(
                            context.cacheDir,
                            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                                .format(System.currentTimeMillis()) + ".jpg"
                        )
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                        isCapturing = true
                        scope.launch {
                            delay(200)
                            isCapturing = false
                        }

                        imageCapture?.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    onCapture(Uri.fromFile(photoFile))
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Log.e("Camera", "Capture failed", exception)
                                    onCapture(null)
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .size(80.dp)
                        .scale(captureScale),
                    containerColor = Color.White,
                    contentColor = Color(0xFF3B82F6),
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
                ) {}
            }

            // Switch camera
            Card(
                modifier = Modifier
                    .size(56.dp)
                    .clickable {
                        cameraSelector =
                            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            else
                                CameraSelector.DEFAULT_BACK_CAMERA
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                ),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("🔄", fontSize = 20.sp)
                }
            }
        }

        // Capture instructions
        Text(
            text = if (isHindi)
                "समस्या को फ्रेम के केंद्र में रखें"
            else
                "Keep the issue centered in the frame",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
                .background(
                    Color.Black.copy(alpha = 0.6f),
                    RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        )
    }
}

