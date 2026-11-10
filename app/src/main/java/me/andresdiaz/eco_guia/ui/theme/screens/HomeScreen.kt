
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val EcoGreen = Color(0xFF075B3D)
private val EcoLight = Color(0xFF62F58C)
private val EcoBackground = Color(0xFFF7F6FC)
private val EcoLavender = Color(0xFFE9EAFB)
private val EcoText = Color(0xFF202438)

@Composable
fun HomeScreen(
    onIdentifyClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

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

            Spacer(modifier = Modifier.height(24.dp))

            // ENCABEZADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "¡Hola, Andrés!",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoText
                    )

                    Text(
                        text = "Pequeñas acciones, grandes cambios",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                // FOTO DE PERFIL
                Surface(
                    shape = RoundedCornerShape(50),
                    color = EcoLavender,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            onProfileClick()
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("👤", fontSize = 25.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TARJETA PRINCIPAL
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EcoGreen
                )
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = " IA DE CLASIFICACIÓN",
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 7.dp
                            ),
                            color = EcoLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "¿Qué quieres desechar hoy?",
                        fontSize = 25.sp,
                        lineHeight = 31.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Identifica tus residuos y descubre cómo clasificarlos correctamente.",
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = onIdentifyClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EcoLight,
                            contentColor = EcoGreen
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = " Identificar residuo",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // ESTADÍSTICAS
            Text(
                text = "Tu impacto ambiental",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = EcoText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "19",
                    description = "Residuos clasificados",
                    icon = ""
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "3.2 kg",
                    description = "CO₂ evitado",
                    icon = ""
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CONSEJOS
            Text(
                text = "Consejos ecológicos",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = EcoText
            )

            Spacer(modifier = Modifier.height(12.dp))

            TipCard(
                title = "Limpia y seca tus plásticos",
                description = "Evita que los restos de comida contaminen los materiales reciclables.",
                category = "PLÁSTICOS Y LATAS"
            )

            Spacer(modifier = Modifier.height(12.dp))

            TipCard(
                title = "Separa correctamente tus residuos",
                description = "Identifica los materiales antes de depositarlos en los contenedores.",
                category = "SEPARACIÓN DE RESIDUOS"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    icon: String,
    value: String,
    description: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(icon, fontSize = 23.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = EcoText
            )

            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun TipCard(
    title: String,
    description: String,
    category: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = EcoLavender
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = " $title",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = EcoText
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = category,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = EcoGreen
            )
        }
    }
}

@Composable
private fun NavigationItem(
    icon: String,
    title: String,
    selected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            fontSize = 23.sp,
            color = if (selected) EcoGreen else Color.Gray
        )

        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold
            else FontWeight.Normal,
            color = if (selected) EcoGreen else Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
