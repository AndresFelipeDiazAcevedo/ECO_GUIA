
package me.andresdiaz.eco_guia.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.andresdiaz.eco_guia.WasteClassifier

private val ScannerGreen = Color(0xFF075B3D)
private val ScannerBackground = Color(0xFFF7F6FC)
private val ScannerText = Color(0xFF202438)

@Composable
fun ScannerScreen(
    onResultClick: (Bitmap, String, Float) -> Unit = { _, _, _ -> }
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedImage by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var isAnalyzing by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    // ABRIR CÁMARA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            selectedImage = bitmap
            errorMessage = null
        }
    }

    // ABRIR GALERÍA
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)

                val bitmap = inputStream?.use {
                    BitmapFactory.decodeStream(it)
                }

                if (bitmap != null) {
                    selectedImage = bitmap
                    errorMessage = null
                } else {
                    errorMessage = "No se pudo abrir la imagen."
                }

            } catch (e: Exception) {
                errorMessage = "Error al cargar la fotografía."
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScannerBackground)
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ENCABEZADO
        Text(
            text = "Identificar residuo",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = ScannerText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Coloca el residuo frente a la cámara para identificarlo.",
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        // ÁREA DE IMAGEN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Color(0xFFE5E7E9),
                    RoundedCornerShape(24.dp)
                )
                .border(
                    width = 2.dp,
                    color = ScannerGreen,
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            if (selectedImage != null) {

                Image(
                    bitmap = selectedImage!!.asImageBitmap(),
                    contentDescription = "Imagen del residuo seleccionado",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentScale = ContentScale.Fit
                )

            } else {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Vista de cámara",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ScannerText
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "La imagen del residuo aparecerá aquí",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // INDICACIÓN
        Text(
            text = if (selectedImage == null) {
                "Centra el residuo dentro del área de captura."
            } else {
                "Imagen seleccionada correctamente."
            },
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // BOTÓN DE CÁMARA
        Button(
            onClick = {
                cameraLauncher.launch(null)
            },
            enabled = !isAnalyzing,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ScannerGreen
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Capturar imagen",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTÓN DE GALERÍA
        OutlinedButton(
            onClick = {
                galleryLauncher.launch("image/*")
            },
            enabled = !isAnalyzing,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, ScannerGreen)
        ) {
            Text(
                text = "Seleccionar desde galería",
                color = ScannerGreen,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // CONTINUAR Y ANALIZAR CON IA
        if (selectedImage != null) {

            Button(
                onClick = {
                    val image = selectedImage

                    if (image != null && !isAnalyzing) {

                        scope.launch {
                            isAnalyzing = true
                            errorMessage = null

                            try {
                                val result = withContext(Dispatchers.Default) {
                                    val classifier = WasteClassifier(
                                        context.applicationContext
                                    )

                                    try {
                                        classifier.classify(image)
                                    } finally {
                                        classifier.close()
                                    }
                                }

                                onResultClick(
                                    image,
                                    result.first,
                                    result.second
                                )

                            } catch (e: Exception) {
                                errorMessage =
                                    "No se pudo analizar la imagen: ${e.message}"
                            } finally {
                                isAnalyzing = false
                            }
                        }
                    }
                },
                enabled = !isAnalyzing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF62F58C)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {

                if (isAnalyzing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = ScannerGreen,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Text(
                    text = if (isAnalyzing) {
                        "Analizando imagen..."
                    } else {
                        "Continuar con la identificación"
                    },
                    color = ScannerGreen,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        } else {

            Text(
                text = "La identificación será realizada mediante inteligencia artificial.",
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }

        if (errorMessage != null) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScannerScreenPreview() {
    MaterialTheme {
        ScannerScreen()
    }
}
