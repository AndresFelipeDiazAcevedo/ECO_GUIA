
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
import com.google.firebase.auth.FirebaseAuth
import me.andresdiaz.eco_guia.data.ScanDatabaseHelper
import me.andresdiaz.eco_guia.data.ScanRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val HistoryGreen = Color(0xFF075B3D)
private val HistoryBackground = Color(0xFFF7F6FC)
private val HistoryText = Color(0xFF202438)

@Composable
fun HistoryScreen(
    databaseHelper: ScanDatabaseHelper? = null,
    onBackClick: () -> Unit = {}
) {

    var selectedFilter by remember { mutableStateOf("Todos") }
    var records by remember { mutableStateOf<List<ScanRecord>>(emptyList()) }

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Cargar únicamente los registros del usuario autenticado
    LaunchedEffect(databaseHelper, userId) {
        records = if (databaseHelper != null && userId != null) {
            databaseHelper.getAllScans(userId)
        } else {
            emptyList()
        }
    }

    // Aplicar filtros
    val filteredRecords = when (selectedFilter) {

        "Aprovechables" -> records.filter {
            getCategoryType(it.material) == "Aprovechable"
        }

        "No aprovechables" -> records.filter {
            getCategoryType(it.material) == "No aprovechable"
        }

        "Especiales" -> records.filter {
            getCategoryType(it.material) == "Especial"
        }

        else -> records
    }

    val totalPoints = records.size * 15

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(onClick = onBackClick) {
                    Text(
                        text = "Volver",
                        color = HistoryGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Mi historial",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryText
                )
            }

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
                            text = records.size.toString(),
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
                            text = totalPoints.toString(),
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

            Text(
                text = "Mis residuos",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FILTROS
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {

                    listOf("Todos", "Aprovechables").forEach { filter ->

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
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {

                    listOf("No aprovechables", "Especiales").forEach { filter ->

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
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // REGISTROS DEL USUARIO
            filteredRecords.forEach { record ->

                HistoryRecordCard(record)

                Spacer(modifier = Modifier.height(12.dp))
            }

            // ESTADO VACÍO
            if (filteredRecords.isEmpty()) {

                Text(
                    text = if (records.isEmpty()) {
                        "Todavía no tienes residuos registrados. ¡Realiza tu primer escaneo!"
                    } else {
                        "No hay registros en esta categoría."
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun HistoryRecordCard(record: ScanRecord) {

    val categoryType = getCategoryType(record.material)

    val date = remember(record.timestamp) {
        SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(Date(record.timestamp))
    }

    val container = getContainer(record.material)

    val badgeBackground = when (categoryType) {
        "Aprovechable" -> Color(0xFFE1F7E9)
        "Especial" -> Color(0xFFFFF0D9)
        else -> Color(0xFFFFE8E8)
    }

    val badgeTextColor = when (categoryType) {
        "Aprovechable" -> HistoryGreen
        "Especial" -> Color(0xFF936018)
        else -> Color(0xFFB3261E)
    }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    color = badgeBackground,
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Text(
                        text = categoryType.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeTextColor
                    )
                }

                Text(
                    text = date,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = record.material,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = HistoryText
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = container,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(13.dp))

            HorizontalDivider(color = HistoryBackground)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Confianza de la IA",
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Text(
                    text = "${(record.confidence * 100).toInt().coerceIn(0, 100)}%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryGreen
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    text = "+15 pts",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = HistoryGreen
                )
            }
        }
    }
}

// CLASIFICACIÓN DE LAS 10 CATEGORÍAS
private fun getCategoryType(material: String): String {

    return when (material.trim().lowercase()) {

        "plástico", "cartón", "papel", "vidrio", "metal" ->
            "Aprovechable"

        "basura" ->
            "No aprovechable"

        "baterías", "bombillos", "cartuchos de tinta", "medicamentos" ->
            "Especial"

        else ->
            "No aprovechable"
    }
}

// CONTENEDOR O LUGAR DE DISPOSICIÓN
private fun getContainer(material: String): String {

    return when (material.trim().lowercase()) {

        "plástico", "cartón", "papel", "vidrio", "metal" ->
            "Contenedor blanco"

        "basura" ->
            "Contenedor negro"

        "baterías" ->
            "Punto especial para baterías"

        "bombillos" ->
            "Punto de recolección especializado"

        "cartuchos de tinta" ->
            "Punto de recolección especializado"

        "medicamentos" ->
            "Punto de recolección de medicamentos"

        else ->
            "Consultar disposición adecuada"
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {

    MaterialTheme {
        HistoryScreen()
    }
}
