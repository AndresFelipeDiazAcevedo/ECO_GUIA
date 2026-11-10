
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

private val GuideGreen = Color(0xFF075B3D)
private val GuideBackground = Color(0xFFF7F6FC)
private val GuideText = Color(0xFF202438)
private val GuideLavender = Color(0xFFE9EAFB)

@Composable
fun GuideScreen() {

    var searchText by remember { mutableStateOf("") }

    val categories = listOf(
        Triple("Aprovechables", "Plástico, vidrio, papel y cartón", "BLANCO"),
        Triple("No aprovechables", "Servilletas usadas, residuos sucios y empaques contaminados", "NEGRO"),
        Triple("Orgánicos", "Cáscaras, restos de frutas y verduras", "VERDE"),
        Triple("Peligrosos y especiales", "Pilas, baterías, medicamentos y electrónicos", "ROJO")
    )

    val filteredCategories = categories.filter {
        it.first.contains(searchText, ignoreCase = true) ||
                it.second.contains(searchText, ignoreCase = true) ||
                it.third.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideBackground)
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
                text = "Guía de reciclaje",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = GuideText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aprende a separar tus residuos correctamente.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            // BUSCADOR
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Buscar residuo o categoría...")
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GuideGreen,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Código oficial de separación",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = GuideText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Consulta los contenedores según el tipo de residuo.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CATEGORÍAS
            filteredCategories.forEach { category ->

                CategoryCard(
                    title = category.first,
                    description = category.second,
                    container = category.third
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filteredCategories.isEmpty()) {
                Text(
                    text = "No se encontraron resultados.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // RECOMENDACIONES
            Text(
                text = "Recomendaciones generales",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = GuideText
            )

            Spacer(modifier = Modifier.height(12.dp))

            RecommendationCard(
                title = "Limpia y seca",
                description = "Los materiales aprovechables deben estar limpios y secos antes de depositarlos."
            )

            Spacer(modifier = Modifier.height(10.dp))

            RecommendationCard(
                title = "Separa los residuos peligrosos",
                description = "Las pilas, medicamentos y baterías requieren puntos especiales de recolección."
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}

@Composable
private fun CategoryCard(
    title: String,
    description: String,
    container: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                color = GuideLavender,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = container,
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 9.dp
                    ),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideGreen
                )
            }
        }
    }
}

@Composable
private fun RecommendationCard(
    title: String,
    description: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GuideLavender
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = GuideText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GuideScreenPreview() {
    MaterialTheme {
        GuideScreen()
    }
}
