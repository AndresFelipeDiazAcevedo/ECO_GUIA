
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

private val HistoryGreen = Color(0xFF075B3D)
private val HistoryBackground = Color(0xFFF7F6FC)
private val HistoryText = Color(0xFF202438)
private val HistoryLavender = Color(0xFFE9EAFB)

data class HistoryRecord(
    val name: String,
    val category: String,
    val date: String,
    val container: String,
    val points: Int
)

@Composable
fun HistoryScreen() {

    var selectedFilter by remember { mutableStateOf("Todos") }

    val records = listOf(
        HistoryRecord(
            "Botella plástica (PET)",
            "Aprovechable",
            "Hoy, 10:24 AM",
            "Contenedor blanco",
            15
        ),
        HistoryRecord(
            "Pila AA alcalina",
            "Peligroso",
            "Ayer, 4:15 PM",
            "Punto especial",
            30
        ),
        HistoryRecord(
            "Caja de cartón",
            "Aprovechable",
            "12 Oct, 3:00 PM",
            "Contenedor blanco",
            15
        ),
        HistoryRecord(
            "Envase de vidrio",
            "Aprovechable",
            "10 Oct, 1:12 PM",
            "Contenedor blanco",
            20
        ),
        HistoryRecord(
            "Medicamento vencido",
            "Peligroso",
            "08 Oct, 11:05 AM",
            "Punto de recolección",
            25
        )
    )

    val filteredRecords = when (selectedFilter) {
        "Aprovechables" -> records.filter {
            it.category == "Aprovechable"
        }

        "Peligrosos" -> records.filter {
            it.category == "Peligroso"
        }

        else -> records
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HistoryBackground)
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
                text = "Mi historial",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryText
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Consulta los residuos que has clasificado.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(22.dp))

            // RESUMEN
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = HistoryGreen
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "27",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Registros clasificados",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "105",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Puntos acumulados",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TÍTULO DE REGISTROS
            Text(
                text = "Mis residuos",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FILTROS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                listOf(
                    "Todos",
                    "Aprovechables",
                    "Peligrosos"
                ).forEach { filter ->

                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = {
                            selectedFilter = filter
                        },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 11.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = HistoryGreen,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = HistoryText
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedFilter == filter,
                            borderColor = Color.LightGray,
                            selectedBorderColor = HistoryGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DE REGISTROS
            filteredRecords.forEach { record ->

                HistoryRecordCard(record)

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filteredRecords.isEmpty()) {
                Text(
                    text = "No hay registros en esta categoría.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        BottomNavigationPreview()
    }
}

@Composable
private fun HistoryRecordCard(
    record: HistoryRecord
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // FECHA Y CATEGORÍA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    color = if (record.category == "Aprovechable")
                        Color(0xFFE1F7E9)
                    else
                        Color(0xFFFFE8E8),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = record.category.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (record.category == "Aprovechable")
                            HistoryGreen
                        else
                            Color(0xFFB33B3B)
                    )
                }

                Text(
                    text = record.date,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            // NOMBRE
            Text(
                text = record.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryText
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = record.container,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(13.dp))

            HorizontalDivider(color = HistoryBackground)

            Spacer(modifier = Modifier.height(12.dp))

            // PUNTOS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Clasificación registrada",
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Text(
                    text = "+${record.points} pts",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryGreen
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen()
    }
}
