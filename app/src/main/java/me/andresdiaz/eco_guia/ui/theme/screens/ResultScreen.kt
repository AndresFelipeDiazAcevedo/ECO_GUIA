
package me.andresdiaz.eco_guia.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val ResultGreen = Color(0xFF075B3D)
private val ResultLight = Color(0xFFE1F7E9)
private val ResultBackground = Color(0xFFF7F6FC)
private val ResultText = Color(0xFF202438)

@Composable
fun ResultScreen(
    image: Bitmap? = null,
    category: String = "Sin identificar",
    confidence: Float = 0f,
    onSaveClick: () -> Unit = {},
    onRescanClick: () -> Unit = {}
) {

    val isRecyclable = category != "Basura" &&
            category != "Sin identificar"

    val recommendation = when (category) {
        "Plástico" ->
            "Separa los envases plásticos, vacíalos y procura entregarlos limpios y secos para facilitar su aprovechamiento."

        "Cartón" ->
            "Mantén el cartón limpio y seco. Dóblalo para reducir su volumen antes de entregarlo para reciclaje."

        "Papel" ->
            "Separa el papel limpio y seco. Evita mezclarlo con papel sanitario, grasoso o contaminado."

        "Vidrio" ->
            "Separa los envases de vidrio y evita romperlos. Deposítalos en un punto de recolección adecuado."

        "Metal" ->
            "Vacía los envases metálicos y entrégalos limpios y secos para facilitar su aprovechamiento."

        "Basura" ->
            "Este modelo clasificó el elemento como basura. Verifica si está contaminado o si pertenece a una categoría que no reconoce."

        else ->
            "No se pudo determinar una recomendación específica para este residuo."
    }

    val disposalSteps = when (category) {
        "Plástico" -> listOf(
            "Vaciar completamente" to "Elimina cualquier resto de líquido.",
            "Enjuagar" to "Retira restos de bebidas o alimentos.",
            "Escurrir" to "Deja secar el envase.",
            "Separar" to "Entrégalo con los materiales aprovechables."
        )

        "Cartón" -> listOf(
            "Revisar" to "Comprueba que no esté mojado ni contaminado.",
            "Limpiar" to "Retira cintas y residuos cuando sea posible.",
            "Doblar" to "Reduce su volumen.",
            "Separar" to "Deposítalo con los materiales aprovechables."
        )

        "Papel" -> listOf(
            "Revisar" to "Comprueba que esté limpio y seco.",
            "Separar" to "Retira otros materiales adheridos.",
            "Organizar" to "Agrupa las hojas o el papel.",
            "Depositar" to "Entrégalo para su aprovechamiento."
        )

        "Vidrio" -> listOf(
            "Vaciar" to "Retira los restos de líquidos.",
            "Enjuagar" to "Limpia el recipiente si es posible.",
            "Proteger" to "Evita romperlo o manipular fragmentos.",
            "Separar" to "Llévalo a un punto de recolección de vidrio."
        )

        "Metal" -> listOf(
            "Vaciar" to "Retira restos de bebidas o alimentos.",
            "Enjuagar" to "Limpia el envase.",
            "Escurrir" to "Déjalo secar.",
            "Separar" to "Entrégalo con los materiales aprovechables."
        )

        else -> listOf(
            "Revisar" to "Comprueba si el residuo está contaminado.",
            "Identificar" to "Verifica si pertenece a otra categoría.",
            "Separar" to "Evita mezclarlo con materiales reciclables.",
            "Disponer" to "Consulta las indicaciones de recolección local."
        )
    }

    val containerName = when (category) {
        "Plástico", "Cartón", "Papel", "Metal" ->
            "BLANCO"

        "Vidrio" ->
            "BLANCO (si es envase aprovechable)"

        else ->
            "NO APROVECHABLE"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ResultBackground)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Resultado del análisis",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = ResultText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "La inteligencia artificial ha analizado tu residuo.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // RESULTADO PRINCIPAL
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = if (category == "Sin identificar") {
                            "RESULTADO NO DISPONIBLE"
                        } else {
                            "IDENTIFICADO POR INTELIGENCIA ARTIFICIAL"
                        },
                        color = ResultGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // IMAGEN
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                ResultBackground,
                                RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        if (image != null) {

                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "Fotografía del residuo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )

                        } else {

                            Text(
                                text = "Imagen no disponible",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = category,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Categoría identificada",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(color = ResultBackground)

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "${(confidence * 100).toInt()}%",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultGreen
                    )

                    Text(
                        text = "Puntuación del modelo",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Surface(
                        color = if (isRecyclable) {
                            ResultLight
                        } else {
                            Color(0xFFFFE8E8)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            text = if (isRecyclable) {
                                "APROVECHABLE"
                            } else {
                                "NO APROVECHABLE / REVISAR"
                            },
                            modifier = Modifier.padding(
                                horizontal = 18.dp,
                                vertical = 10.dp
                            ),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isRecyclable) {
                                ResultGreen
                            } else {
                                Color(0xFFB3261E)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // RECOMENDACIÓN
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ResultLight
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Recomendación ambiental",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultGreen
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = recommendation,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = ResultText
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // INSTRUCCIONES
            Text(
                text = "Instrucciones de disposición",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ResultText
            )

            Spacer(modifier = Modifier.height(12.dp))

            disposalSteps.forEachIndexed { index, step ->

                DisposalStep(
                    number = "0${index + 1}",
                    title = step.first,
                    description = step.second
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // CONTENEDOR
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Contenedor recomendado",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = containerName,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultGreen
                    )

                    Text(
                        text = "Verifica las condiciones de recolección de tu municipio.",
                        fontSize = 13.sp,
                        color = ResultText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "+15 puntos EcoGuía",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ResultGreen
            )

            Text(
                text = "Puedes acumular puntos al registrar esta clasificación.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(15.dp))
        }

        // BOTONES INFERIORES
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ResultGreen
                ),
                shape = RoundedCornerShape(14.dp)
            ) {

                Text(
                    text = "Guardar en mi historial",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onRescanClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp)
            ) {

                Text(
                    text = "Volver a escanear",
                    color = ResultGreen
                )
            }
        }
    }
}

@Composable
private fun DisposalStep(
    number: String,
    title: String,
    description: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.Top
    ) {

        Surface(
            color = ResultLight,
            shape = RoundedCornerShape(10.dp)
        ) {

            Text(
                text = number,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),
                color = ResultGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = ResultText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    MaterialTheme {
        ResultScreen(
            category = "Plástico",
            confidence = 0.6883f
        )
    }
}
