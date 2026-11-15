
package me.andresdiaz.eco_guia.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import me.andresdiaz.eco_guia.ui.theme.*

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

    // CÁMARA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            selectedImage = bitmap
            errorMessage = null
        }
    }

    // GALERÍA
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
            .background(EcoBackground)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ENCABEZADO
        Text(
            text = "Identificar",
            style = MaterialTheme.typography.headlineMedium,
            color = EcoNavy
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Reconoce tus residuos con inteligencia artificial.",
            style = MaterialTheme.typography.bodyMedium,
            color = EcoGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // INDICADOR DE ESTADO
        Surface(
            shape = RoundedCornerShape(50.dp),
            color = EcoGreenLight
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 13.dp,
                    vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(EcoGreen, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (selectedImage == null) {
                        "LISTO PARA ESCANEAR"
                    } else {
                        "IMAGEN SELECCIONADA"
                    },
                    color = EcoGreen,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // ÁREA DE ESCANEO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(27.dp))
                .background(Color(0xFF172B27)),
            contentAlignment = Alignment.Center
        ) {

            if (selectedImage != null) {

                Image(
                    bitmap = selectedImage!!.asImageBitmap(),
                    contentDescription = "Imagen del residuo seleccionado",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.10f))
                )

            } else {

                // FONDO DE ESPERA
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF203A32),
                                    Color(0xFF101E1A)
                                )
                            )
                        )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    // MARCO DE ESCANEO
                    Box(
                        modifier = Modifier
                            .size(width = 205.dp, height = 235.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        // Borde exterior sutil
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 1.dp,
                                    color = Color.White.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(24.dp)
                                )
                        )

                        // Marco interior
                        Box(
                            modifier = Modifier
                                .size(width = 145.dp, height = 175.dp)
                                .border(
                                    width = 1.5.dp,
                                    color = EcoMint.copy(alpha = 0.75f),
                                    shape = RoundedCornerShape(18.dp)
                                )
                        )

                        // Punto central
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(EcoMint, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Área de identificación",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Selecciona una fotografía\npara comenzar el análisis",
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        lineHeight = 19.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // ETIQUETA INFERIOR
            if (selectedImage != null) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(15.dp),
                    shape = RoundedCornerShape(50.dp),
                    color = EcoGreen
                ) {
                    Text(
                        text = "Imagen lista para analizar",
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 9.dp
                        ),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (isAnalyzing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.65f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = EcoMint,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(43.dp)
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Analizando residuo...",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "La inteligencia artificial está trabajando",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(17.dp))

        // INSTRUCCIÓN
        Text(
            text = if (selectedImage == null) {
                "Centra el residuo en la imagen y procura tener buena iluminación."
            } else {
                "Comprueba que el residuo se vea claramente antes de continuar."
            },
            color = EcoGray,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CONTROLES DE CAPTURA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // GALERÍA
            OutlinedButton(
                onClick = {
                    galleryLauncher.launch("image/*")
                },
                enabled = !isAnalyzing,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    EcoGreen.copy(alpha = 0.35f)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = EcoGreen
                )
            ) {
                Text(
                    text = "▧",
                    fontSize = 21.sp
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = "Galería",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // CÁMARA
            Button(
                onClick = {
                    cameraLauncher.launch(null)
                },
                enabled = !isAnalyzing,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "◎",
                    fontSize = 23.sp
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = "Cámara",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ANALIZAR IMAGEN
        if (selectedImage != null) {

            Spacer(modifier = Modifier.height(11.dp))

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
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EcoMint,
                    contentColor = EcoGreen
                )
            ) {
                Text(
                    text = "Analizar residuo",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "→",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ERRORES
        if (errorMessage != null) {

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = Color(0xFFFFE8E8),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = errorMessage ?: "",
                    modifier = Modifier.padding(12.dp),
                    color = EcoRed,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScannerScreenPreview() {
    ECO_GUIATheme {
        ScannerScreen()
    }
}
