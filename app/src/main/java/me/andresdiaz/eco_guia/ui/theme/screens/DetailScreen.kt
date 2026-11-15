
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

    return when (category.trim().lowercase()) {


        "aprovechables" -> WasteDetail(
            title = "Residuos aprovechables",
            type = "CONTENEDOR BLANCO",
            description = "Materiales que pueden recuperarse y transformarse para fabricar nuevos productos.",
            warningTitle = "¡Evita la contaminación!",
            warning = "Los materiales deben estar limpios y secos. Los residuos contaminados pueden dificultar su aprovechamiento.",
            reminder = "Separa papel, cartón, plástico, vidrio y metales.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Separar los materiales" to "Clasifica cada material según su tipo.",
                "Limpiar los envases" to "Retira los restos de comida y líquidos.",
                "Mantenerlos secos" to "Guárdalos en un espacio limpio y seco."
            ),
            disposalSteps = listOf(
                "Deposita los residuos aprovechables limpios y secos en el contenedor blanco.",
                "Aplasta envases y cajas cuando sea posible para ahorrar espacio."
            )
        )

        "no aprovechables" -> WasteDetail(
            title = "Residuos no aprovechables",
            type = "CONTENEDOR NEGRO",
            description = "Residuos que no pueden aprovecharse mediante los procesos habituales de reciclaje.",
            warningTitle = "¡Evita mezclarlos!",
            warning = "Estos residuos pueden contaminar los materiales reciclables si se depositan juntos.",
            reminder = "Utiliza una bolsa independiente para estos residuos.",
            storageTitle = "Forma correcta de almacenarlos",
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

        "orgánicos" -> WasteDetail(
            title = "Residuos orgánicos",
            type = "CONTENEDOR VERDE",
            description = "Residuos biodegradables como restos de frutas, verduras y alimentos.",
            warningTitle = "¡Aprovecha los residuos orgánicos!",
            warning = "Muchos residuos orgánicos pueden aprovecharse mediante procesos como el compostaje.",
            reminder = "Evita mezclarlos con plásticos, vidrio u otros materiales.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Separar los restos" to "Recolecta cáscaras, frutas y verduras.",
                "Usar un recipiente" to "Almacénalos en un recipiente adecuado y preferiblemente con tapa.",
                "Evitar acumulaciones" to "Retíralos periódicamente para evitar malos olores."
            ),
            disposalSteps = listOf(
                "Deposita los residuos orgánicos en el contenedor verde.",
                "También pueden destinarse a procesos de compostaje cuando exista esa posibilidad."
            )
        )

        "peligrosos y especiales" -> WasteDetail(
            title = "Residuos peligrosos y especiales",
            type = "PUNTO DE RECOLECCIÓN ESPECIAL",
            description = "Residuos que requieren un manejo diferenciado para evitar riesgos para la salud y el ambiente.",
            warningTitle = "¡No los mezcles con la basura común!",
            warning = "Pilas, baterías, bombillos, medicamentos y cartuchos requieren sistemas especiales de recolección.",
            reminder = "Identifica el tipo de residuo y busca un punto autorizado para entregarlo.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Separar los residuos" to "No los mezcles con residuos domésticos.",
                "Usar recipientes adecuados" to "Evita que se rompan, derramen o deterioren.",
                "Almacenarlos de forma segura" to "Manténlos fuera del alcance de niños y mascotas."
            ),
            disposalSteps = listOf(
                "Identifica el programa de recolección correspondiente.",
                "Llévalos a un punto autorizado según el tipo de residuo."
            )
        )


        "basura" -> WasteDetail(
            title = "Basura ordinaria",
            type = "CONTENEDOR NEGRO",
            description = "Residuos no aprovechables que no pueden recuperarse mediante los procesos habituales de reciclaje.",
            warningTitle = "¡No mezcles los residuos!",
            warning = "Servilletas usadas, papeles contaminados y empaques con restos de comida pueden afectar el reciclaje de otros materiales.",
            reminder = "Antes de desechar algo, verifica si puede reutilizarse o reciclarse.",
            storageTitle = "Forma correcta de almacenarla",
            storageSteps = listOf(
                "Identificar el residuo" to "Comprueba que no sea aprovechable ni peligroso.",
                "Usar una bolsa" to "Deposítalo en una bolsa resistente.",
                "Evitar derrames" to "Cierra correctamente la bolsa."
            ),
            disposalSteps = listOf(
                "Deposita los residuos ordinarios en el contenedor negro.",
                "Los residuos peligrosos deben separarse y entregarse en puntos especiales."
            )
        )

        "baterías" -> WasteDetail(
            title = "Baterías y pilas",
            type = "RESIDUO PELIGROSO",
            description = "Dispositivos electroquímicos que almacenan energía y pueden contener sustancias contaminantes.",
            warningTitle = "¡Riesgo de contaminación!",
            warning = "Las baterías pueden liberar sustancias peligrosas si se rompen, queman o desechan incorrectamente.",
            reminder = "Nunca las deposites en la basura común ni en contenedores de reciclaje convencionales.",
            storageTitle = "Forma correcta de almacenarlas",
            storageSteps = listOf(
                "Mantenerlas intactas" to "No las abras, aplastes ni perfores.",
                "Proteger los terminales" to "En baterías con terminales expuestos, utiliza cinta aislante para evitar cortocircuitos.",
                "Lugar seguro" to "Guárdalas en un lugar seco y lejos del calor."
            ),
            disposalSteps = listOf(
                "Llévalas a puntos de recolección de pilas o baterías.",
                "Las baterías de litio dañadas o hinchadas requieren manejo especializado; no las manipules innecesariamente."
            )
        )

        "bombillos" -> WasteDetail(
            title = "Bombillos y lámparas",
            type = "RESIDUO ESPECIAL",
            description = "Elementos de iluminación que pueden contener componentes electrónicos, metales y, en algunos casos, mercurio.",
            warningTitle = "¡Evita romperlos!",
            warning = "Algunos bombillos fluorescentes contienen mercurio. Si se rompen, pueden liberar sustancias peligrosas.",
            reminder = "No deposites bombillos usados en el contenedor blanco ni en la basura común cuando tienen rutas especiales.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Conservar su integridad" to "Evita golpes y movimientos bruscos.",
                "Protegerlos" to "Guárdalos en su empaque original o en una caja.",
                "Separarlos" to "No los mezcles con vidrio de envases."
            ),
            disposalSteps = listOf(
                "Busca programas de recolección de bombillos y luminarias.",
                "Entrégalos en puntos autorizados para residuos de iluminación."
            )
        )

        "cartón" -> WasteDetail(
            title = "Cartón",
            type = "CONTENEDOR BLANCO",
            description = "Material de origen vegetal utilizado en cajas, empaques y otros productos que puede recuperarse mediante reciclaje.",
            warningTitle = "¡Evita la humedad!",
            warning = "El cartón mojado, grasoso o contaminado con comida puede perder sus posibilidades de aprovechamiento.",
            reminder = "Asegúrate de que esté limpio y seco antes de reciclarlo.",
            storageTitle = "Forma correcta de almacenarlo",
            storageSteps = listOf(
                "Retirar los residuos" to "Quita cintas, plásticos y otros elementos cuando sea posible.",
                "Aplanar las cajas" to "Desarma las cajas para reducir su volumen.",
                "Mantenerlo seco" to "Almacénalo lejos del agua y la humedad."
            ),
            disposalSteps = listOf(
                "Deposita el cartón limpio y seco en el contenedor blanco.",
                "El cartón con grasa o restos de comida debe ir al contenedor negro."
            )
        )

        "cartuchos de tinta" -> WasteDetail(
            title = "Cartuchos de tinta",
            type = "RESIDUO ESPECIAL",
            description = "Componentes de impresoras que contienen tinta o tóner y materiales plásticos y electrónicos.",
            warningTitle = "¡Evita los derrames!",
            warning = "Los restos de tinta y tóner pueden generar manchas y contaminación. No abras ni desarmes los cartuchos.",
            reminder = "Prioriza los programas de devolución y recolección de cartuchos.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "No abrirlos" to "Evita perforar o desarmar el cartucho.",
                "Protegerlos" to "Guárdalos en una bolsa o empaque para evitar derrames.",
                "Mantenerlos separados" to "No los mezcles con papel, plástico o basura común."
            ),
            disposalSteps = listOf(
                "Consulta los programas de devolución del fabricante o proveedor.",
                "Entrégalos en puntos de recolección de residuos electrónicos o especiales."
            )
        )

        "medicamentos" -> WasteDetail(
            title = "Medicamentos vencidos",
            type = "RESIDUO ESPECIAL",
            description = "Medicamentos vencidos, parcialmente consumidos o que ya no se necesitan y requieren una disposición adecuada.",
            warningTitle = "¡Protege tu salud!",
            warning = "Los medicamentos no deben arrojarse al sanitario, lavamanos ni mezclarse con residuos ordinarios.",
            reminder = "Utiliza los puntos autorizados para la recolección de medicamentos.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Conservar el empaque" to "Mantén los medicamentos en sus envases originales cuando sea posible.",
                "Separarlos" to "No mezcles medicamentos con otros residuos.",
                "Mantenerlos seguros" to "Almacénalos fuera del alcance de niños y mascotas."
            ),
            disposalSteps = listOf(
                "Llévalos a los contenedores o puntos autorizados del programa de recolección de medicamentos.",
                "No reutilices medicamentos vencidos ni los entregues a otras personas."
            )
        )

        "metal" -> WasteDetail(
            title = "Metales",
            type = "CONTENEDOR BLANCO",
            description = "Materiales como aluminio, acero y otros metales presentes en latas, envases y objetos que pueden recuperarse.",
            warningTitle = "¡Cuidado con los bordes!",
            warning = "Las latas y piezas metálicas pueden tener bordes cortantes. Manipúlalas con precaución.",
            reminder = "Los metales limpios y secos pueden aprovecharse mediante procesos de reciclaje.",
            storageTitle = "Forma correcta de almacenarlos",
            storageSteps = listOf(
                "Vaciar los envases" to "Retira los restos de bebidas o alimentos.",
                "Limpiar y secar" to "Evita dejar residuos orgánicos en las latas.",
                "Separar los objetos" to "Mantén los metales separados de otros materiales."
            ),
            disposalSteps = listOf(
                "Deposita los metales aprovechables limpios y secos en el contenedor blanco.",
                "Los objetos metálicos peligrosos o contaminados requieren una ruta de manejo diferente."
            )
        )

        "papel" -> WasteDetail(
            title = "Papel",
            type = "CONTENEDOR BLANCO",
            description = "Material elaborado principalmente a partir de fibras vegetales que puede recuperarse para fabricar nuevos productos.",
            warningTitle = "¡Evita la contaminación!",
            warning = "El papel mojado, grasoso o contaminado con alimentos puede dificultar su reciclaje.",
            reminder = "Separa el papel limpio y seco de los residuos ordinarios.",
            storageTitle = "Forma correcta de almacenarlo",
            storageSteps = listOf(
                "Separar los tipos de papel" to "Agrupa hojas, periódicos y revistas limpios.",
                "Retirar elementos adicionales" to "Quita plásticos y otros componentes cuando sea posible.",
                "Mantenerlo seco" to "Guárdalo en un lugar protegido de la humedad."
            ),
            disposalSteps = listOf(
                "Deposita el papel limpio y seco en el contenedor blanco.",
                "El papel higiénico usado, las servilletas sucias y el papel contaminado van al contenedor negro."
            )
        )

        "plástico" -> WasteDetail(
            title = "Plástico",
            type = "CONTENEDOR BLANCO",
            description = "Material utilizado en botellas, envases, recipientes y empaques que puede recuperarse según su composición.",
            warningTitle = "¡Prepáralo antes de reciclar!",
            warning = "Los envases con restos de alimentos o líquidos pueden contaminar otros materiales aprovechables.",
            reminder = "No todos los plásticos tienen las mismas posibilidades de reciclaje. Sigue las indicaciones de tu programa local.",
            storageTitle = "Forma correcta de almacenarlo",
            storageSteps = listOf(
                "Vaciar los envases" to "Retira completamente los restos de líquidos y alimentos.",
                "Limpiar y secar" to "Enjuaga los recipientes cuando sea necesario y déjalos secar.",
                "Reducir el volumen" to "Aplasta las botellas cuando sea posible."
            ),
            disposalSteps = listOf(
                "Deposita los envases plásticos aprovechables, limpios y secos en el contenedor blanco.",
                "Los empaques contaminados o que no puedan aprovecharse deben separarse según las indicaciones locales."
            )
        )

        "vidrio" -> WasteDetail(
            title = "Vidrio",
            type = "CONTENEDOR BLANCO",
            description = "Material presente en botellas y frascos que puede reciclarse y transformarse en nuevos productos.",
            warningTitle = "¡Manipúlalo con cuidado!",
            warning = "Los fragmentos de vidrio pueden provocar cortes. Nunca manipules piezas rotas con las manos desprotegidas.",
            reminder = "Separa los envases de vidrio de bombillos, espejos y otros residuos especiales.",
            storageTitle = "Forma correcta de almacenarlo",
            storageSteps = listOf(
                "Vaciar los envases" to "Retira los restos de líquidos o alimentos.",
                "Limpiar los recipientes" to "Enjuaga los envases cuando sea necesario.",
                "Evitar roturas" to "Almacénalos en un lugar estable y seguro."
            ),
            disposalSteps = listOf(
                "Deposita los envases de vidrio aprovechables, limpios y secos en el contenedor blanco.",
                "No mezcles bombillos, espejos, cerámica ni vidrio roto sin protección con los envases reciclables."
            )
        )

        else -> WasteDetail(
            title = category,
            type = "INFORMACIÓN DEL RESIDUO",
            description = "Consulta las recomendaciones para separar correctamente este tipo de residuo.",
            warningTitle = "Recomendación",
            warning = "Identifica el material y evita mezclarlo con residuos de otras categorías.",
            reminder = "Consulta las indicaciones locales de recolección.",
            storageTitle = "Forma correcta de almacenarlo",
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
                        .clickable { onBackClick() }
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
