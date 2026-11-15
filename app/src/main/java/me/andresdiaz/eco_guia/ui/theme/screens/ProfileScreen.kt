
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
import com.google.firebase.firestore.FirebaseFirestore
import me.andresdiaz.eco_guia.data.ScanDatabaseHelper
import me.andresdiaz.eco_guia.data.ScanRecord

private val ProfileGreen = Color(0xFF075B3D)
private val ProfileLight = Color(0xFFE1F7E9)
private val ProfileBackground = Color(0xFFF7F6FC)
private val ProfileLavender = Color(0xFFE9EAFB)
private val ProfileText = Color(0xFF202438)

@Composable
fun ProfileScreen(
    databaseHelper: ScanDatabaseHelper? = null,
    onLogoutClick: () -> Unit = {}
) {
    var darkMode by remember { mutableStateOf(false) }
    var records by remember { mutableStateOf<List<ScanRecord>>(emptyList()) }

    // Datos reales del usuario
    var fullName by remember { mutableStateOf("Cargando perfil...") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val auth = remember { FirebaseAuth.getInstance() }
    val firestore = remember { FirebaseFirestore.getInstance() }

    val userId = auth.currentUser?.uid

    // Cargar información personal desde Firestore
    LaunchedEffect(userId) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            email = currentUser.email ?: ""

            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        fullName = document.getString("name")
                            ?: "Usuario EcoGuía"

                        username = document.getString("username")
                            ?: ""
                    } else {
                        fullName = "Usuario EcoGuía"
                    }
                }
                .addOnFailureListener {
                    fullName = "Usuario EcoGuía"
                }
        } else {
            fullName = "Usuario"
            username = ""
            email = ""
        }
    }

    // Cargar escaneos locales del usuario autenticado
    LaunchedEffect(databaseHelper, userId) {
        records = if (databaseHelper != null && userId != null) {
            databaseHelper.getAllScans(userId)
        } else {
            emptyList()
        }
    }

    val totalScans = records.size

    val recyclable = records.count {
        getCategoryType(it.material) == "Aprovechable"
    }

    val dangerous = records.count {
        getCategoryType(it.material) == "Peligroso"
    }

    val points = totalScans * 15

    val level = (points / 100) + 1
    val progress = (points % 100) / 100f
    val pointsRemaining = 100 - (points % 100)

    val levelName = when {
        level >= 4 -> "Eco Líder"
        level == 3 -> "Guardián Verde"
        level == 2 -> "Separador"
        else -> "Eco Aprendiz"
    }

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

            // INFORMACIÓN REAL DEL USUARIO
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
                                text = fullName.firstOrNull()
                                    ?.uppercase() ?: "U",
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProfileGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = fullName,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProfileText
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    if (username.isNotBlank()) {
                        Text(
                            text = "@$username",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Text(
                        text = email,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(13.dp))

                    Surface(
                        color = ProfileLight,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Nivel $level: $levelName",
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
                            text = "Nivel $level",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileText
                        )

                        Text(
                            text = "${(progress * 100).toInt()}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(9.dp),
                        color = ProfileGreen,
                        trackColor = ProfileLavender,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$pointsRemaining puntos para alcanzar el siguiente nivel.",
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
                    value = totalScans.toString(),
                    description = "Residuos identificados"
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = recyclable.toString(),
                    description = "Reciclables"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = points.toString(),
                    description = "Puntos acumulados"
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = dangerous.toString(),
                    description = "Residuos peligrosos"
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
                        text = "$totalScans de 50 clasificaciones registradas",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = {
                            (totalScans / 50f).coerceIn(0f, 1f)
                        },
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

private fun getCategoryType(material: String): String {
    return when (material) {
        "Plástico", "Cartón", "Papel", "Vidrio", "Metal" ->
            "Aprovechable"

        "Baterías", "Bombillos", "Cartuchos de tinta", "Medicamentos" ->
            "Peligroso"

        else -> "No aprovechable"
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
