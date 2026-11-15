
package me.andresdiaz.eco_guia.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import me.andresdiaz.eco_guia.ui.theme.*

private data class WasteInformation(
    val description: String,
    val recommendation: String,
    val container: String,
    val status: String,
    val steps: List<Pair<String, String>>
)

private fun getWasteInformation(category: String): WasteInformation {
    return when (category.trim().lowercase()) {

        "plástico" -> WasteInformation(
            description = "Material plástico identificado por la inteligencia artificial.",
            recommendation = "Vacía los envases, retira los restos de alimentos y déjalos limpios y secos antes de reciclarlos.",
            container = "BLANCO",
            status = "APROVECHABLE SI ESTÁ LIMPIO Y SECO",
            steps = listOf(
                "Vaciar" to "Elimina los restos de líquidos o alimentos.",
                "Limpiar" to "Enjuaga el envase cuando sea necesario.",
                "Secar" to "Deja secar completamente.",
                "Separar" to "Deposítalo en el contenedor blanco."
            )
        )

        "cartón" -> WasteInformation(
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

        "papel" -> WasteInformation(
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

        "vidrio" -> WasteInformation(
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

        "metal" -> WasteInformation(
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

        "basura" -> WasteInformation(
            description = "El modelo clasificó el elemento dentro de la categoría de basura.",
            recommendation = "Esta categoría representa residuos ordinarios no aprovechables. Comprueba que el elemento no corresponda a un residuo reciclable o peligroso.",
            container = "NEGRO",
            status = "NO APROVECHABLE",
            steps = listOf(
                "Revisar" to "Comprueba si el residuo está contaminado.",
                "Identificar" to "Verifica que no sea un material aprovechable.",
                "Separar" to "Evita mezclarlo con materiales reciclables.",
                "Depositar" to "Utiliza el contenedor negro si es un residuo ordinario no aprovechable."
            )
        )

        "baterías" -> WasteInformation(
            description = "El modelo identificó una pila o batería usada.",
            recommendation = "Las baterías pueden contener sustancias peligrosas. Nunca las deposites en los contenedores convencionales.",
            container = "PUNTO ESPECIAL DE RECOLECCIÓN",
            status = "RESIDUO ESPECIAL",
            steps = listOf(
                "Conservar" to "No abras, aplastes ni perfores la batería.",
                "Proteger" to "Evita el contacto entre terminales expuestos.",
                "Almacenar" to "Guárdala en un lugar seco y seguro.",
                "Entregar" to "Llévala a un punto autorizado de recolección."
            )
        )

        "bombillos" -> WasteInformation(
            description = "El modelo identificó un bombillo o elemento de iluminación.",
            recommendation = "Algunos bombillos, especialmente los fluorescentes, pueden contener mercurio. Evita romperlos y manipúlalos con cuidado.",
            container = "PUNTO ESPECIAL DE RECOLECCIÓN",
            status = "RESIDUO ESPECIAL",
            steps = listOf(
                "Conservar" to "Evita golpes y roturas.",
                "Proteger" to "Utiliza su empaque original o una caja.",
                "Separar" to "No los mezcles con envases de vidrio.",
                "Entregar" to "Busca un programa autorizado de recolección de luminarias."
            )
        )

        "cartuchos de tinta" -> WasteInformation(
            description = "El modelo identificó un cartucho de tinta o tóner de impresora.",
            recommendation = "Estos elementos contienen tinta, plástico y componentes que requieren una disposición diferenciada. Evita abrirlos o perforarlos.",
            container = "PUNTO ESPECIAL DE RECOLECCIÓN",
            status = "RESIDUO ESPECIAL",
            steps = listOf(
                "No abrir" to "Evita desarmar o perforar el cartucho.",
                "Proteger" to "Guárdalo en su empaque para evitar derrames.",
                "Separar" to "No lo mezcles con residuos ordinarios.",
                "Entregar" to "Consulta los programas de devolución del fabricante."
            )
        )

        "medicamentos" -> WasteInformation(
            description = "El modelo identificó medicamentos que pueden requerir una disposición especial.",
            recommendation = "Los medicamentos vencidos o que ya no necesitas no deben arrojarse al sanitario ni mezclarse con la basura común.",
            container = "PUNTO AUTORIZADO DE RECOLECCIÓN",
            status = "RESIDUO ESPECIAL",
            steps = listOf(
                "Identificar" to "Verifica que sean medicamentos vencidos o que ya no necesitas.",
                "Conservar" to "Mantén el empaque original cuando sea posible.",
                "Separar" to "No los mezcles con otros residuos.",
                "Entregar" to "Llévalos a un punto autorizado de recolección."
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
                "Consultar" to "Utiliza la guía para identificarlo manualmente."
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
    onRescanClick: () -> Unit = {},
    onGuideClick: (String) -> Unit = {}
) {

    val information = getWasteInformation(category)
    val normalizedCategory = category.trim().lowercase()

    val isIdentified = normalizedCategory != "sin identificar" &&
            normalizedCategory != ""

    val isSpecial = normalizedCategory in listOf(
        "baterías",
        "bombillos",
        "cartuchos de tinta",
        "medicamentos"
    )

    val isTrash = normalizedCategory == "basura"

    val percentage = (confidence * 100)
        .toInt()
        .coerceIn(0, 100)

    val statusColor = when {
        isSpecial -> Color(0xFF9A671C)
        isTrash -> Color(0xFFB3261E)
        isIdentified -> EcoGreen
        else -> EcoGray
    }

    val statusBackground = when {
        isSpecial -> Color(0xFFFFF1D8)
        isTrash -> Color(0xFFFFE9E9)
        isIdentified -> EcoGreenLight
        else -> EcoLavender
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoBackground)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Resultado del análisis",
                style = MaterialTheme.typography.headlineMedium,
                color = EcoNavy
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Conoce cómo debes disponer este residuo.",
                style = MaterialTheme.typography.bodyMedium,
                color = EcoGray
            )

            Spacer(modifier = Modifier.height(19.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EcoWhite
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(17.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (isIdentified) EcoGreen else EcoGray,
                                    CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if (isIdentified) {
                                "IDENTIFICACIÓN COMPLETADA"
                            } else {
                                "IDENTIFICACIÓN PENDIENTE"
                            },
                            color = EcoGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(17.dp))
                            .background(EcoBackground),
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
                                color = EcoGray,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(17.dp))

                    Text(
                        text = category,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoNavy
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = information.description,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = EcoGray
                    )

                    Spacer(modifier = Modifier.height(19.dp))

                    HorizontalDivider(color = EcoLightGray)

                    Spacer(modifier = Modifier.height(17.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "Confianza de la IA",
                                fontSize = 13.sp,
                                color = EcoGray,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "$percentage%",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoGreen
                            )
                        }

                        Text(
                            text = "PREDICCIÓN",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.7.sp,
                            color = EcoGray
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { percentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = EcoGreen,
                        trackColor = EcoGreenLight
                    )

                    Spacer(modifier = Modifier.height(17.dp))

                    Surface(
                        color = statusBackground,
                        shape = RoundedCornerShape(11.dp)
                    ) {
                        Text(
                            text = information.status,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 11.dp
                                ),
                            color = statusColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(17.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EcoGreen
                )
            ) {
                Column(
                    modifier = Modifier.padding(19.dp)
                ) {

                    Text(
                        text = "RECOMENDACIÓN AMBIENTAL",
                        color = EcoMint,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.7.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = information.recommendation,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "¿Qué debes hacer?",
                style = MaterialTheme.typography.titleLarge,
                color = EcoNavy
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Sigue estos pasos para disponerlo correctamente.",
                fontSize = 12.sp,
                color = EcoGray
            )

            Spacer(modifier = Modifier.height(15.dp))

            information.steps.forEachIndexed { index, step ->

                DisposalStep(
                    number = "0${index + 1}",
                    title = step.first,
                    description = step.second
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EcoWhite
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = if (isSpecial) {
                            "DISPOSICIÓN ESPECIAL"
                        } else {
                            "CONTENEDOR RECOMENDADO"
                        },
                        color = EcoGray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )

                    Spacer(modifier = Modifier.height(13.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val binColor = when {
                            isSpecial -> Color(0xFFD69A38)
                            isTrash -> Color(0xFF252525)
                            isIdentified -> Color(0xFFF0F0F0)
                            else -> EcoLavender
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(binColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when {
                                    isSpecial -> "E"
                                    isTrash -> "N"
                                    isIdentified -> "B"
                                    else -> "?"
                                },
                                color = when {
                                    isSpecial -> Color.White
                                    isTrash -> Color.White
                                    isIdentified -> EcoGreen
                                    else -> EcoGray
                                },
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(13.dp))

                        Column {
                            Text(
                                text = information.container,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoNavy
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Verifica las condiciones de recolección de tu municipio.",
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                color = EcoGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (isIdentified) {

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(17.dp),
                    color = EcoLavender
                ) {
                    Row(
                        modifier = Modifier.padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "+15 PUNTOS ECOGUÍA",
                                color = EcoGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Registra esta clasificación para acumular puntos.",
                                color = EcoGray,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }

                        Text(
                            text = "+15",
                            color = EcoGreen,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(13.dp))

                OutlinedButton(
                    onClick = {
                        onGuideClick(category)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        EcoGreen.copy(alpha = 0.45f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = EcoGreen
                    )
                ) {
                    Text(
                        text = "Consultar guía de este residuo",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "→",
                        fontSize = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Surface(
            color = EcoWhite,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 13.dp)
            ) {

                Button(
                    onClick = onSaveClick,
                    enabled = isIdentified,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EcoGreen,
                        disabledContainerColor = EcoLightGray
                    )
                ) {
                    Text(
                        text = "Guardar en mi historial",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(9.dp))

                OutlinedButton(
                    onClick = onRescanClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
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
                        text = "Volver a identificar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
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
            .padding(bottom = 17.dp),
        verticalAlignment = Alignment.Top
    ) {

        Box(
            modifier = Modifier
                .size(39.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(EcoGreenLight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = EcoGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 2.dp)
        ) {

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = EcoGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    ECO_GUIATheme {
        ResultScreen(
            category = "Plástico",
            confidence = 0.6883f
        )
    }
}
