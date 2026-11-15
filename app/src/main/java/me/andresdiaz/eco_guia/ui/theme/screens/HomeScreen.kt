
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import me.andresdiaz.eco_guia.ui.theme.*

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
            Spacer(modifier = Modifier.height(22.dp))

            // ENCABEZADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¡Hola, Andrés!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = EcoNavy
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Pequeñas acciones, grandes cambios",
                        style = MaterialTheme.typography.bodyMedium,
                        color = EcoGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = EcoGreenLight
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 11.dp,
                                vertical = 6.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(EcoGreen, CircleShape)
                            )

                            Spacer(modifier = Modifier.width(7.dp))

                            Text(
                                text = "NIVEL 1  ·  ECO APRENDIZ",
                                color = EcoGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.4.sp
                            )
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onProfileClick() },
                    shape = CircleShape,
                    color = EcoLavender
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "A",
                            color = EcoGreen,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // TARJETA PRINCIPAL
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(27.dp),
                colors = CardDefaults.cardColors(
                    containerColor = EcoGreen
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    EcoGreen,
                                    EcoGreenDark,
                                    Color(0xFF063B2D)
                                )
                            )
                        )
                ) {
                    // Decoración geométrica
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 18.dp, end = 18.dp)
                            .size(112.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.045f))
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 42.dp, end = 42.dp)
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.045f))
                    )

                    Column(
                        modifier = Modifier.padding(21.dp)
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.13f),
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 7.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "AI",
                                    color = EcoMint,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "CLASIFICACIÓN INTELIGENTE",
                                    color = EcoMint,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.6.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(21.dp))

                        Text(
                            text = "¿Qué quieres\ndesechar hoy?",
                            color = Color.White,
                            fontSize = 29.sp,
                            lineHeight = 35.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Identifica tus residuos y descubre cómo clasificarlos correctamente.",
                            color = Color.White.copy(alpha = 0.82f),
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        Button(
                            onClick = onIdentifyClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EcoMint,
                                contentColor = EcoGreen
                            )
                        ) {

                            Spacer(modifier = Modifier.width(9.dp))

                            Text(
                                text = "Identificar residuo",
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

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Reconocimiento de residuos mediante inteligencia artificial",
                            color = Color.White.copy(alpha = 0.65f),
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(27.dp))

            // ESTADÍSTICAS
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tu impacto positivo",
                    style = MaterialTheme.typography.titleLarge,
                    color = EcoNavy
                )

                Text(
                    text = "RESUMEN",
                    color = EcoGray,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    symbol = "↻",
                    value = "19",
                    description = "Residuos clasificados",
                    accent = EcoGreen
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    symbol = "CO₂",
                    value = "3.2 kg",
                    description = "Emisiones evitadas",
                    accent = Color(0xFF6876C5)
                )
            }

            Spacer(modifier = Modifier.height(27.dp))

            // CONSEJOS
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Consejos del día",
                    style = MaterialTheme.typography.titleLarge,
                    color = EcoNavy
                )

                Text(
                    text = "ECO TIPS",
                    color = EcoGreen,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            TipCard(
                symbol = "01",
                title = "Limpia y seca tus plásticos",
                description = "Evita que los restos de comida contaminen los materiales reciclables.",
                category = "PLÁSTICOS Y LATAS",
                background = EcoLavender,
                accent = EcoGreen
            )

            Spacer(modifier = Modifier.height(12.dp))

            TipCard(
                symbol = "02",
                title = "Separa correctamente tus residuos",
                description = "Identifica los materiales antes de depositarlos en su contenedor correspondiente.",
                category = "SEPARACIÓN DE RESIDUOS",
                background = EcoGreenLight,
                accent = EcoGreen
            )

            Spacer(modifier = Modifier.height(17.dp))

            // MENSAJE FINAL
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFFFFF5D9)
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.8f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "03",
                            color = EcoGreen,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Cada acción cuenta",
                            color = EcoNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Un pequeño cambio genera un gran impacto.",
                            color = EcoGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    symbol: String,
    value: String,
    description: String,
    accent: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(
            containerColor = EcoWhite
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accent.copy(alpha = 0.11f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symbol,
                    fontSize = if (symbol == "CO₂") 13.sp else 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = value,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = EcoGray
            )
        }
    }
}

@Composable
private fun TipCard(
    symbol: String,
    title: String,
    description: String,
    category: String,
    background: Color,
    accent: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = background
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symbol,
                    color = accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category,
                    color = accent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = EcoGray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ECO_GUIATheme {
        HomeScreen()
    }
}
