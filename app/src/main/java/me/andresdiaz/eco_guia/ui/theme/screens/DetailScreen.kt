
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val DetailGreen = Color(0xFF075B3D)
private val DetailBackground = Color(0xFFF7F6FC)
private val DetailText = Color(0xFF202438)
private val DetailLavender = Color(0xFFE9EAFB)
private val DetailRed = Color(0xFFB33B3B)
private val DetailLightRed = Color(0xFFFCE8E8)

private data class WasteDetail(
    val title: String,
    val type: String,
    val description: String,
    val warningTitle: String,
    val warning: String,
    val reminder: String,
    val storageTitle: String,
    val storageSteps: List<Pair<String, String>>,
    val disposalSteps: List<String>
)

private fun getWasteDetail(category: String): WasteDetail {

    return when (category) {

        "Aprovechables" -> WasteDetail(
            title = "Residuos aprovechables",
            type = "CONTENEDOR BLANCO",
            description = "Materiales que pueden recuperarse y transformarse para fabricar nuevos productos.",
            warningTitle = "¡Evita la contaminación!",
            warning = "Los residuos aprovechables deben estar limpios y secos. Si se mezclan con restos de comida o líquidos, pueden perder su posibilidad de aprovechamiento.",
            reminder = "Separa estos materiales y deposítalos en el contenedor blanco.",
            storageTitle = "Forma correcta de almacenarlos en casa",
            storageSteps = listOf(
                "Separar los materiales" to "Clasifica plástico, vidrio, papel, cartón y metales.",
                "Limpiar los envases" to "Retira los restos de comida y líquidos.",
                "Mantenerlos secos" to "Guárdalos en un espacio limpio y seco."
            ),
            disposalSteps = listOf(
                "Deposita los materiales limpios y secos en el contenedor blanco.",
                "Aplasta las botellas y cajas cuando sea posible para ahorrar espacio."
            )
        )

        "No aprovechables" -> WasteDetail(
            title = "Residuos no aprovechables",
            type = "CONTENEDOR NEGRO",
            description = "Residuos que no pueden aprovecharse mediante los procesos habituales de reciclaje.",
            warningTitle = "¡Evita mezclarlos!",
            warning = "Los residuos no aprovechables pueden contaminar los materiales reciclables si se depositan juntos.",
            reminder = "Utiliza una bolsa independiente para estos residuos.",
            storageTitle = "Forma correcta de almacenarlos en casa",
            storageSteps = listOf(
                "Utilizar una bolsa" to "Deposita los residuos en una bolsa resistente.",
                "Separarlos" to "No los mezcles con materiales aprovechables.",
                "Cerrar la bolsa" to "Evita derrames y malos olores."
            ),
            disposalSteps = listOf(
                "Deposita los residuos en el contenedor negro.",
                "Entrégalos al servicio de recolección correspondiente."
            )
        )

        "Orgánicos" -> WasteDetail(
            title = "Residuos orgánicos",
            type = "CONTENEDOR VERDE",
            description = "Residuos biodegradables como restos de frutas, verduras y alimentos.",
            warningTitle = "¡Aprovecha los residuos orgánicos!",
            warning = "Cuando se separan correctamente, muchos residuos orgánicos pueden aprovecharse mediante procesos como el compostaje.",
            reminder = "Evita mezclar los residuos orgánicos con plásticos, vidrio u otros materiales.",
            storageTitle = "Forma correcta de almacenarlos en casa",
            storageSteps = listOf(
                "Separar los restos" to "Recolecta cáscaras, frutas y verduras.",
                "Usar un recipiente" to "Almacénalos en un recipiente adecuado y preferiblemente con tapa.",
                "Evitar acumulaciones" to "Retira los residuos periódicamente para evitar malos olores."
            ),
            disposalSteps = listOf(
                "Deposita los residuos orgánicos en el contenedor verde.",
                "También pueden destinarse a procesos de compostaje cuando exista esa posibilidad."
            )
        )

        "Peligrosos y especiales" -> WasteDetail(
            title = "Pilas y baterías",
            type = "RESIDUO PELIGROSO / TÓXICO",
            description = "Dispositivos electroquímicos de uso cotidiano que pueden contener sustancias peligrosas para el ambiente.",
            warningTitle = "¡Peligro ambiental!",
            warning = "Las pilas y baterías pueden contener metales y sustancias contaminantes. Nunca deben depositarse en los residuos domésticos comunes.",
            reminder = "Lleva siempre tus pilas y baterías usadas a un punto especial de recolección.",
            storageTitle = "Forma correcta de almacenarlas en casa",
            storageSteps = listOf(
                "Recipiente adecuado" to "Utiliza un recipiente seco y resistente.",
                "Proteger los terminales" to "Si es posible, cubre los polos con cinta aislante para evitar cortocircuitos.",
                "Lugar seguro" to "Almacénalas lejos del calor y la humedad."
            ),
            disposalSteps = listOf(
                "No rompas, perfores ni quemes las pilas o baterías.",
                "Llévalas a contenedores especiales o programas de recolección autorizados."
            )
        )

        else -> WasteDetail(
            title = category,
            type = "INFORMACIÓN DE LA CATEGORÍA",
            description = "Consulta las recomendaciones para separar correctamente este tipo de residuo.",
            warningTitle = "Recomendación",
            warning = "Identifica el material y evita mezclarlo con residuos de otras categorías.",
            reminder = "Consulta las indicaciones locales de recolección.",
            storageTitle = "Forma correcta de almacenarlo en casa",
            storageSteps = listOf(
                "Separar el residuo" to "Mantenlo separado de otros materiales.",
                "Almacenarlo correctamente" to "Utiliza un recipiente adecuado y seguro."
            ),
            disposalSteps = listOf(
                "Consulta el contenedor o punto de recolección correspondiente.",
                "Sigue las indicaciones del servicio local de recolección."
            )
        )
    }
}

@Composable
fun DetailScreen(
    category: String = "Peligrosos y especiales",
    onBackClick: () -> Unit = {}
) {

    val detail = getWasteDetail(category)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailBackground)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "← Volver",
                    color = DetailGreen,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(
                            Color(0xFFE5F5EB),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            onBackClick()
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 9.dp
                        )
                )

                Text(
                    text = "Detalle",
                    color = DetailText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = DetailGreen
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Text(
                        text = detail.type,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = detail.title,
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = detail.description,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            InformationCard(
                title = detail.warningTitle,
                description = detail.warning,
                background = DetailLightRed,
                titleColor = DetailRed
            )

            Spacer(modifier = Modifier.height(14.dp))

            InformationCard(
                title = "Recordatorio importante",
                description = detail.reminder,
                background = DetailLavender,
                titleColor = DetailGreen
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = detail.storageTitle,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DetailText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DetailLavender
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    detail.storageSteps.forEachIndexed { index, step ->

                        StepCard(
                            number = index + 1,
                            title = step.first,
                            description = step.second
                        )

                        if (index < detail.storageSteps.lastIndex) {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Forma correcta de desecharlo",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DetailText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DetailLavender
            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    detail.disposalSteps.forEach { step ->

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White
                        ) {

                            Text(
                                text = step,
                                modifier = Modifier.padding(15.dp),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = DetailText
                            )
                        }

                        Spacer(modifier = Modifier.height(9.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun InformationCard(
    title: String,
    description: String,
    background: Color,
    titleColor: Color
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = background
    ) {

        Column(
            modifier = Modifier.padding(17.dp)
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = DetailText
            )
        }
    }
}

@Composable
private fun StepCard(
    number: Int,
    title: String,
    description: String
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {

        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Surface(
                shape = CircleShape,
                color = Color(0xFF62F58C)
            ) {

                Text(
                    text = number.toString(),
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
                    ),
                    color = DetailGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DetailText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    MaterialTheme {
        DetailScreen()
    }
}
