
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

private data class WasteInformation(
    val description: String,
    val recommendation: String,
    val container: String,
    val status: String,
    val steps: List<Pair<String, String>>
)

private fun getWasteInformation(category: String): WasteInformation {

    return when (category) {

        "Plástico" -> WasteInformation(
            description = "Material plástico identificado por la inteligencia artificial.",
            recommendation = "Vacía el envase, enjuágalo y déjalo completamente seco. Los plásticos limpios pueden aprovecharse mediante procesos de reciclaje.",
            container = "BLANCO",
            status = "APROVECHABLE SI ESTÁ LIMPIO Y SECO",
            steps = listOf(
                "Vaciar" to "Elimina los restos de líquidos o alimentos.",
                "Enjuagar" to "Limpia el interior del envase.",
                "Secar" to "Escurre y deja secar completamente.",
                "Separar" to "Deposítalo en el contenedor blanco."
            )
        )

        "Cartón" -> WasteInformation(
            description = "Material de cartón identificado por la inteligencia artificial.",
            recommendation = "El cartón debe estar limpio y seco. Si tiene grasa, humedad o restos de comida, no debe mezclarse con los materiales aprovechables.",
            container = "BLANCO",
            status = "APROVECHABLE SI ESTÁ LIMPIO Y SECO",
            steps = listOf(
                "Revisar" to "Comprueba que no tenga grasa ni humedad.",
                "Limpiar" to "Retira residuos y elementos adheridos.",
                "Doblar" to "Pliega el cartón para reducir su volumen.",
                "Separar" to "Deposítalo en el contenedor blanco."
            )
        )

        "Papel" -> WasteInformation(
            description = "Material de papel identificado por la inteligencia artificial.",
            recommendation = "El papel limpio y seco puede recuperarse. No incluyas servilletas usadas, papel higiénico ni papel contaminado con alimentos.",
            container = "BLANCO",
            status = "APROVECHABLE SI ESTÁ LIMPIO Y SECO",
            steps = listOf(
                "Revisar" to "Comprueba que el papel esté limpio y seco.",
                "Separar" to "Retira materiales que no sean papel.",
                "Organizar" to "Agrupa las hojas y evita arrugarlas.",
                "Depositar" to "Entrégalo en el contenedor blanco."
            )
        )

        "Vidrio" -> WasteInformation(
            description = "Material de vidrio identificado por la inteligencia artificial.",
            recommendation = "Los frascos y botellas de vidrio pueden aprovecharse. Evita romperlos y separa tapas y otros materiales cuando corresponda.",
            container = "BLANCO (ENVASES APROVECHABLES)",
            status = "APROVECHABLE SI ES UN ENVASE",
            steps = listOf(
                "Vaciar" to "Retira los restos de líquidos.",
                "Enjuagar" to "Limpia el recipiente si es posible.",
                "Proteger" to "Evita romperlo y manipular fragmentos.",
                "Separar" to "Deposita los envases aprovechables en el contenedor blanco."
            )
        )

        "Metal" -> WasteInformation(
            description = "Material metálico identificado por la inteligencia artificial.",
            recommendation = "Las latas y envases metálicos limpios pueden recuperarse. Vacíalos y elimina los restos de alimentos antes de separarlos.",
            container = "BLANCO",
            status = "APROVECHABLE SI ESTÁ LIMPIO Y SECO",
            steps = listOf(
                "Vaciar" to "Retira los restos de bebidas o alimentos.",
                "Enjuagar" to "Limpia el envase cuando sea necesario.",
                "Secar" to "Escurre el exceso de agua.",
                "Separar" to "Deposítalo en el contenedor blanco."
            )
        )

        "Basura" -> WasteInformation(
            description = "El modelo clasificó el elemento dentro de la categoría de basura.",
            recommendation = "Este resultado corresponde a la categoría general de basura del modelo. Comprueba si el residuo está contaminado o si requiere una recolección especial.",
            container = "NEGRO (NO APROVECHABLES)",
            status = "NO APROVECHABLE",
            steps = listOf(
                "Revisar" to "Comprueba si el residuo está contaminado.",
                "Identificar" to "Verifica que no sea un residuo aprovechable.",
                "Separar" to "Evita mezclarlo con materiales reciclables.",
                "Depositar" to "Si es un residuo ordinario no aprovechable, utiliza el contenedor negro."
            )
        )

        else -> WasteInformation(
            description = "No se pudo identificar una categoría.",
            recommendation = "Intenta tomar otra fotografía con buena iluminación y mostrando el residuo completo.",
            container = "NO DETERMINADO",
            status = "IDENTIFICACIÓN PENDIENTE",
            steps = listOf(
                "Revisar" to "Comprueba que el residuo se vea claramente.",
                "Iluminar" to "Busca un lugar con buena iluminación.",
                "Reintentar" to "Toma otra fotografía.",
                "Consultar" to "Utiliza la guía de residuos para identificarlo manualmente."
            )
        )
    }
}

@Composable
fun ResultScreen(
    image: Bitmap? = null,
    category: String = "Sin identificar",
    confidence: Float = 0f,
    onSaveClick: () -> Unit = {},
    onRescanClick: () -> Unit = {}
) {

    val information = getWasteInformation(category)

    val isIdentified = category != "Sin identificar"

    val percentage = (confidence * 100)
        .toInt()
        .coerceIn(0, 100)

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
                        text = if (isIdentified) {
                            "IDENTIFICADO POR INTELIGENCIA ARTIFICIAL"
                        } else {
                            "RESULTADO NO DISPONIBLE"
                        },
                        color = ResultGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

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
                        text = information.description,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(color = ResultBackground)

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "$percentage%",
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
                        color = if (category == "Basura") {
                            Color(0xFFFFE8E8)
                        } else if (isIdentified) {
                            ResultLight
                        } else {
                            Color(0xFFFFF1D6)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            text = information.status,
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 10.dp
                            ),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (category == "Basura") {
                                Color(0xFFB3261E)
                            } else {
                                ResultGreen
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
                        text = information.recommendation,
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

            information.steps.forEachIndexed { index, step ->

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
                        text = information.container,
                        fontSize = 21.sp,
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

            if (isIdentified) {

                Text(
                    text = "+15 puntos EcoGuía",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ResultGreen
                )

                Text(
                    text = "Puedes acumular puntos registrando esta clasificación.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

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
                enabled = isIdentified,
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
