
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val ProfileGreen = Color(0xFF075B3D)
private val ProfileLight = Color(0xFFE1F7E9)
private val ProfileBackground = Color(0xFFF7F6FC)
private val ProfileLavender = Color(0xFFE9EAFB)
private val ProfileText = Color(0xFF202438)

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit = {}
){

    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ProfileBackground)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // ENCABEZADO
            Text(
                text = "Mi perfil",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(24.dp))

            // INFORMACIÓN DEL USUARIO
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
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Surface(
                        modifier = Modifier.size(85.dp),
                        shape = RoundedCornerShape(50),
                        color = ProfileLavender
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProfileGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Andrés Díaz",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProfileText
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "@andresgreen",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(13.dp))

                    Surface(
                        color = ProfileLight,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Nivel 3: Guardián Verde",
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 8.dp
                            ),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // PROGRESO
            Text(
                text = "Progreso de nivel",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(12.dp))

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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Nivel 3",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileText
                        )

                        Text(
                            text = "84%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { 0.84f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(9.dp),
                        color = ProfileGreen,
                        trackColor = ProfileLavender,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "80 puntos para alcanzar el Nivel 4: Eco Líder.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // ESTADÍSTICAS
            Text(
                text = "Mi impacto positivo",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = "27",
                    description = "Residuos identificados"
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = "19",
                    description = "Reciclables"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = "420",
                    description = "Puntos acumulados"
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = "14.8 kg",
                    description = "CO₂ evitado"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // INSIGNIAS
            Text(
                text = "Insignias ambientales",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                        text = "Guardián del Barrio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProfileText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "27 de 50 clasificaciones verificadas",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { 0.54f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = ProfileGreen,
                        trackColor = ProfileLavender
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CONFIGURACIÓN
            Text(
                text = "Configuración",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(17.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(
                                text = "Modo oscuro",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ProfileText
                            )

                            Text(
                                text = "Ajustar apariencia",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        Switch(
                            checked = darkMode,
                            onCheckedChange = {
                                darkMode = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = ProfileGreen
                            )
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 14.dp),
                        color = ProfileBackground
                    )

                    TextButton(
                        onClick = onLogoutClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            color = Color(0xFFB33B3B),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}

@Composable
private fun ProfileStatCard(
    modifier: Modifier = Modifier,
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

            Text(
                text = value,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileGreen
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}
