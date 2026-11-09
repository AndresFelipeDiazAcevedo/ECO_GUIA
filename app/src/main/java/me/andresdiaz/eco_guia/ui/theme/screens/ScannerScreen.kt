
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val ScannerGreen = Color(0xFF075B3D)
private val ScannerBackground = Color(0xFFF7F6FC)
private val ScannerText = Color(0xFF202438)

@Composable
fun ScannerScreen() {

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

        // ÁREA DE CÁMARA
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

        Spacer(modifier = Modifier.height(20.dp))

        // INDICACIÓN
        Text(
            text = "Centra el residuo dentro del área de captura.",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // BOTÓN PRINCIPAL
        Button(
            onClick = { },
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

        // OPCIONES SECUNDARIAS
        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                ScannerGreen
            )
        ) {
            Text(
                text = "Seleccionar desde galería",
                color = ScannerGreen,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "La identificación será realizada mediante inteligencia artificial.",
            fontSize = 12.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )

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
