
package me.andresdiaz.eco_guia.ui.screens

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
    onSaveClick: () -> Unit = {},
    onRescanClick: () -> Unit = {}
) {

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

            // ENCABEZADO
            Text(
                text = "Resultado del análisis",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = ResultText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "La inteligencia artificial ha identificado tu residuo.",
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
                        text = "IDENTIFICADO CON ÉXITO",
                        color = ResultGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                ResultBackground,
                                RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Imagen del residuo",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Botella plástica",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Polímero PET 1 (Tereftalato de polietileno)",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(color = ResultBackground)

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "95%",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultGreen
                    )

                    Text(
                        text = "Confianza del reconocimiento",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Surface(
                        color = ResultLight,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "APROVECHABLE",
                            modifier = Modifier.padding(
                                horizontal = 22.dp,
                                vertical = 10.dp
                            ),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ResultGreen
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
                        text = "Este residuo puede reciclarse si está limpio y completamente seco. Los restos de líquidos o grasas pueden afectar el proceso.",
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

            DisposalStep(
                number = "01",
                title = "Vaciar completamente",
                description = "Elimina cualquier resto de líquido o bebida."
            )

            DisposalStep(
                number = "02",
                title = "Enjuagar",
                description = "Lava brevemente el interior para retirar los residuos."
            )

            DisposalStep(
                number = "03",
                title = "Escurrir y compactar",
                description = "Retira el exceso de agua y aplasta la botella para reducir su volumen."
            )

            DisposalStep(
                number = "04",
                title = "Depositar en el contenedor",
                description = "Llévala al contenedor blanco destinado a materiales aprovechables."
            )

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
                        text = "BLANCO",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = ResultGreen
                    )

                    Text(
                        text = "Materiales aprovechables limpios y secos.",
                        fontSize = 13.sp,
                        color = ResultText
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // PUNTOS
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
        ResultScreen()
    }
}
