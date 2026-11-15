
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import me.andresdiaz.eco_guia.data.ScanDatabaseHelper
import me.andresdiaz.eco_guia.data.ScanRecord

private val ProfileGreen = Color(0xFF07583D)
private val ProfileMint = Color(0xFF62F58C)
private val ProfileBackground = Color(0xFFF7F6FC)
private val ProfileLavender = Color(0xFFF0EFFF)
private val ProfileText = Color(0xFF17223D)
private val ProfileGray = Color(0xFF777B88)
private val ProfileBorder = Color(0xFFE8E7EF)
private val ProfileRed = Color(0xFFB33B3B)

@Composable
fun ProfileScreen(
    databaseHelper: ScanDatabaseHelper? = null,
    onLogoutClick: () -> Unit = {}
) {
    var records by remember {
        mutableStateOf<List<ScanRecord>>(emptyList())
    }

    var fullName by remember {
        mutableStateOf("Cargando perfil...")
    }

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val firestore = remember {
        FirebaseFirestore.getInstance()
    }

    val userId = auth.currentUser?.uid

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

                        username = document.getString("username") ?: ""
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

    val nonRecyclable = records.count {
        getCategoryType(it.material) == "No aprovechable"
    }

    val special = records.count {
        getCategoryType(it.material) == "Especial"
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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "MI ESPACIO",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = ProfileGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mi perfil",
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(20.dp))

            // TARJETA DE USUARIO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ProfileGreen
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(82.dp),
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = fullName.firstOrNull()
                                    ?.uppercase() ?: "U",
                                fontSize = 34.sp,
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
                        color = Color.White
                    )

                    if (username.isNotBlank()) {
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "@$username",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = email,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = ProfileMint,
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Nivel $level · $levelName",
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // PROGRESO
            ProfileSectionHeading(
                title = "Tu progreso",
                subtitle = "Sigue avanzando en tu camino ecológico."
            )

            Spacer(modifier = Modifier.height(13.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, ProfileBorder)
            ) {
                Column(
                    modifier = Modifier.padding(19.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Nivel $level",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProfileText
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = levelName,
                                fontSize = 12.sp,
                                color = ProfileGray
                            )
                        }

                        Text(
                            text = "${(progress * 100).toInt()}%",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProfileGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

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
                        color = ProfileGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // ESTADÍSTICAS
            ProfileSectionHeading(
                title = "Mi impacto positivo",
                subtitle = "El resultado de tus residuos identificados."
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = totalScans.toString(),
                    description = "Residuos identificados",
                    numberColor = ProfileGreen
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = recyclable.toString(),
                    description = "Aprovechables",
                    numberColor = ProfileGreen
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = points.toString(),
                    description = "Puntos acumulados",
                    numberColor = ProfileGreen
                )

                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    value = special.toString(),
                    description = "Residuos especiales",
                    numberColor = Color(0xFFB17A20)
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            ProfileStatCard(
                modifier = Modifier.fillMaxWidth(),
                value = nonRecyclable.toString(),
                description = "No aprovechables",
                numberColor = ProfileGray
            )

            Spacer(modifier = Modifier.height(26.dp))

            // INSIGNIAS
            ProfileSectionHeading(
                title = "Insignias ambientales",
                subtitle = "Desbloquea logros con tus acciones."
            )

            Spacer(modifier = Modifier.height(13.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, ProfileBorder)
            ) {
                Column(
                    modifier = Modifier.padding(19.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(15.dp),
                            color = ProfileLavender
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "50",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ProfileGreen
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(13.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Guardián del Barrio",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProfileText
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "50 clasificaciones registradas",
                                fontSize = 12.sp,
                                color = ProfileGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(17.dp))

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

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (totalScans >= 50) {
                            "¡Insignia desbloqueada!"
                        } else {
                            "${50 - totalScans} clasificaciones para desbloquearla."
                        },
                        fontSize = 12.sp,
                        color = ProfileGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // CONFIGURACIÓN
            ProfileSectionHeading(
                title = "Configuración",
                subtitle = "Administra tu sesión."
            )

            Spacer(modifier = Modifier.height(13.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, ProfileBorder)
            ) {
                Column(
                    modifier = Modifier.padding(17.dp)
                ) {
                    Text(
                        text = "Apariencia",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProfileText
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "EcoGuía utiliza una apariencia clara para facilitar la navegación.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = ProfileGray
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 14.dp),
                        color = ProfileBorder
                    )

                    TextButton(
                        onClick = onLogoutClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            color = ProfileRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
private fun ProfileSectionHeading(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = ProfileText
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = ProfileGray
        )
    }
}

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

@Composable
private fun ProfileStatCard(
    modifier: Modifier = Modifier,
    value: String,
    description: String,
    numberColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, ProfileBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = value,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = numberColor
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = ProfileGray
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
