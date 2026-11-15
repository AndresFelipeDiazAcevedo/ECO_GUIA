
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val GuideGreen = Color(0xFF07583D)
private val GuideMint = Color(0xFF62F58C)
private val GuideBackground = Color(0xFFF7F6FC)
private val GuideText = Color(0xFF17223D)
private val GuideGray = Color(0xFF777B88)
private val GuideLavender = Color(0xFFF0EFFF)
private val GuideBorder = Color(0xFFE8E7EF)
private val GuideAmber = Color(0xFFFFF5D9)
private val GuideAmberText = Color(0xFF76501E)

private data class GuideCategory(
    val name: String,
    val description: String,
    val container: String
)

@Composable
fun GuideScreen(
    onCategoryClick: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    val aprovechables = listOf(
        GuideCategory("Plástico", "Botellas, envases y empaques plásticos.", "BLANCO"),
        GuideCategory("Vidrio", "Botellas y frascos de vidrio.", "BLANCO"),
        GuideCategory("Papel", "Hojas, periódicos, revistas y papel limpio.", "BLANCO"),
        GuideCategory("Cartón", "Cajas y empaques de cartón limpios y secos.", "BLANCO"),
        GuideCategory("Metal", "Latas y objetos metálicos aprovechables.", "BLANCO")
    )

    val noAprovechables = listOf(
        GuideCategory(
            "Basura",
            "Servilletas usadas, papel higiénico y residuos contaminados.",
            "NEGRO"
        )
    )

    val organicos = listOf(
        GuideCategory(
            "Orgánicos",
            "Cáscaras, restos de frutas, verduras y alimentos.",
            "VERDE"
        )
    )

    val especiales = listOf(
        GuideCategory(
            "Baterías",
            "Pilas y baterías usadas de diferentes dispositivos.",
            "PUNTO ESPECIAL"
        ),
        GuideCategory(
            "Bombillos",
            "Bombillos y lámparas que requieren recolección especial.",
            "PUNTO ESPECIAL"
        ),
        GuideCategory(
            "Cartuchos de tinta",
            "Cartuchos y tóner de impresoras.",
            "PUNTO ESPECIAL"
        ),
        GuideCategory(
            "Medicamentos",
            "Medicamentos vencidos o que ya no necesitas.",
            "PUNTO ESPECIAL"
        )
    )

    val allCategories = aprovechables + noAprovechables + organicos + especiales

    val filteredCategories = allCategories.filter {
        it.name.contains(searchText, ignoreCase = true) ||
                it.description.contains(searchText, ignoreCase = true) ||
                it.container.contains(searchText, ignoreCase = true)
    }

    val filteredNames = filteredCategories.map { it.name }.toSet()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideBackground)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "GUÍA ECOLÓGICA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = GuideGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Separa mejor,\nrecicla más.",
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold,
                color = GuideText
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Encuentra cómo clasificar tus residuos y descubre dónde depositarlos correctamente.",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = GuideGray
            )

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Buscar un residuo...",
                        color = GuideGray,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Text(
                        text = "⌕",
                        fontSize = 27.sp,
                        color = GuideGreen
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Text(
                            text = "×",
                            modifier = Modifier
                                .clickable { searchText = "" }
                                .padding(horizontal = 14.dp),
                            fontSize = 22.sp,
                            color = GuideGray
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GuideGreen,
                    unfocusedBorderColor = GuideBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = GuideGreen
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            SectionHeading(
                title = "Código de separación",
                subtitle = "Identifica cada residuo según su destino."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                ContainerColorCard(
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    textColor = GuideText,
                    title = "BLANCO",
                    description = "Aprovechables"
                )

                ContainerColorCard(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF252525),
                    textColor = Color.White,
                    title = "NEGRO",
                    description = "No aprovechables"
                )

                ContainerColorCard(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF18833B),
                    textColor = Color.White,
                    title = "VERDE",
                    description = "Orgánicos"
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            CategorySection(
                title = "Aprovechables",
                subtitle = "Contenedor blanco",
                categories = aprovechables,
                filteredNames = filteredNames,
                onCategoryClick = onCategoryClick
            )

            CategorySection(
                title = "No aprovechables",
                subtitle = "Contenedor negro",
                categories = noAprovechables,
                filteredNames = filteredNames,
                onCategoryClick = onCategoryClick
            )

            CategorySection(
                title = "Residuos orgánicos",
                subtitle = "Contenedor verde",
                categories = organicos,
                filteredNames = filteredNames,
                onCategoryClick = onCategoryClick
            )

            val visibleSpecials = especiales.filter { it.name in filteredNames }

            if (visibleSpecials.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = GuideAmber
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "RECOLECCIÓN ESPECIAL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = GuideAmberText
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "Residuos especiales y peligrosos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GuideText
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "No tienen un cuarto contenedor dentro del código de colores. Deben entregarse mediante programas y puntos de recolección específicos.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = GuideAmberText
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        visibleSpecials.forEach { category ->
                            SpecialCategoryCard(
                                category = category,
                                onClick = { onCategoryClick(category.name) }
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                        }
                    }
                }
            }

            if (filteredCategories.isEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp),
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, GuideBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sin resultados",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = GuideText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Prueba con otro nombre o categoría de residuo.",
                            fontSize = 13.sp,
                            color = GuideGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            SectionHeading(
                title = "Recomendaciones",
                subtitle = "Pequeñas acciones que hacen la diferencia."
            )

            Spacer(modifier = Modifier.height(14.dp))

            RecommendationCard(
                number = "01",
                title = "Limpia y seca",
                description = "Los materiales aprovechables deben estar limpios y secos antes de depositarlos."
            )

            Spacer(modifier = Modifier.height(10.dp))

            RecommendationCard(
                number = "02",
                title = "Separa los residuos especiales",
                description = "Las pilas, baterías, bombillos, medicamentos y cartuchos requieren puntos especiales de recolección."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionHeading(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GuideText
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = GuideGray
        )
    }
}

@Composable
private fun ContainerColorCard(
    modifier: Modifier = Modifier,
    color: Color,
    textColor: Color,
    title: String,
    description: String
) {
    Surface(
        modifier = modifier.height(112.dp),
        shape = RoundedCornerShape(16.dp),
        color = color,
        border = if (color == Color.White) {
            BorderStroke(1.dp, GuideBorder)
        } else null,
        shadowElevation = if (color == Color.White) 1.dp else 0.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(
                        color = textColor.copy(alpha = 0.12f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(textColor, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 10.sp,
                lineHeight = 13.sp,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun CategorySection(
    title: String,
    subtitle: String,
    categories: List<GuideCategory>,
    filteredNames: Set<String>,
    onCategoryClick: (String) -> Unit
) {
    val visibleCategories = categories.filter {
        it.name in filteredNames
    }

    if (visibleCategories.isEmpty()) return

    Column(
        modifier = Modifier.padding(bottom = 22.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GuideText
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = subtitle,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = GuideGreen
        )

        Spacer(modifier = Modifier.height(11.dp))

        visibleCategories.forEach { category ->
            CategoryCard(
                category = category,
                onClick = { onCategoryClick(category.name) }
            )

            Spacer(modifier = Modifier.height(9.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    category: GuideCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, GuideBorder),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(GuideLavender, RoundedCornerShape(13.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.name.take(1).uppercase(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideGreen
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = category.description,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = GuideGray
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "›",
                fontSize = 25.sp,
                color = GuideGreen
            )
        }
    }
}

@Composable
private fun SpecialCategoryCard(
    category: GuideCategory,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideText
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = category.description,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = GuideGray
                )
            }

            Text(
                text = "›",
                fontSize = 24.sp,
                color = GuideAmberText
            )
        }
    }
}

@Composable
private fun RecommendationCard(
    number: String,
    title: String,
    description: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        color = Color.White,
        border = BorderStroke(1.dp, GuideBorder)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = number,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GuideGreen
            )

            Spacer(modifier = Modifier.width(13.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GuideText
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = GuideGray
                )
            }
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
