
package me.andresdiaz.eco_guia

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import me.andresdiaz.eco_guia.data.ScanDatabaseHelper
import me.andresdiaz.eco_guia.ui.screens.HomeScreen
import me.andresdiaz.eco_guia.ui.screens.ScannerScreen
import me.andresdiaz.eco_guia.ui.screens.ResultScreen
import me.andresdiaz.eco_guia.ui.screens.GuideScreen
import me.andresdiaz.eco_guia.ui.screens.HistoryScreen
import me.andresdiaz.eco_guia.ui.screens.ProfileScreen
import me.andresdiaz.eco_guia.ui.screens.LoginScreen
import me.andresdiaz.eco_guia.ui.screens.RegisterScreen
import me.andresdiaz.eco_guia.ui.screens.DetailScreen
import me.andresdiaz.eco_guia.ui.theme.ECO_GUIATheme
import me.andresdiaz.eco_guia.ui.theme.EcoGreen
import me.andresdiaz.eco_guia.ui.theme.EcoGray
import me.andresdiaz.eco_guia.ui.theme.EcoWhite

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ECO_GUIATheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val databaseHelper = remember {
        ScanDatabaseHelper(context.applicationContext)
    }

    val startDestination = remember {
        if (auth.currentUser != null) "home" else "login"
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarRoutes = listOf(
        "home",
        "scanner",
        "guide",
        "profile"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,

        bottomBar = {
            if (currentDestination?.route in bottomBarRoutes) {

                EcoBottomBar(
                    selectedRoute = currentDestination?.route ?: "",
                    onNavigate = { route ->

                        if (route != currentDestination?.route) {
                            navController.navigate(route) {
                                popUpTo("home") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {

            // INICIO DE SESIÓN
            composable("login") {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onRegisterClick = {
                        navController.navigate("register")
                    }
                )
            }

            // REGISTRO
            composable("register") {
                RegisterScreen(
                    onRegisterClick = {
                        navController.navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onLoginClick = {
                        navController.popBackStack()
                    }
                )
            }

            // HOME
            composable("home") {
                HomeScreen(
                    onIdentifyClick = {
                        navController.navigate("scanner")
                    },
                    onProfileClick = {
                        navController.navigate("profile") {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // ESCÁNER
            composable("scanner") {
                ScannerScreen(
                    onResultClick = { bitmap, category, confidence ->

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedImage", bitmap)

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("detectedCategory", category)

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("confidence", confidence)

                        navController.navigate("result")
                    }
                )
            }

            // RESULTADO
            composable("result") {

                val savedStateHandle = navController.previousBackStackEntry
                    ?.savedStateHandle

                val selectedImage = savedStateHandle
                    ?.get<Bitmap>("selectedImage")

                val detectedCategory = savedStateHandle
                    ?.get<String>("detectedCategory") ?: "Sin identificar"

                val confidence = savedStateHandle
                    ?.get<Float>("confidence") ?: 0f

                ResultScreen(
                    image = selectedImage,
                    category = detectedCategory,
                    confidence = confidence,

                    onSaveClick = {

                        val userId = auth.currentUser?.uid

                        if (userId != null) {
                            val result = databaseHelper.insertScan(
                                userId = userId,
                                material = detectedCategory,
                                confidence = confidence
                            )

                            if (result != -1L) {
                                navController.navigate("history")
                            }
                        }
                    },

                    onRescanClick = {
                        navController.navigate("scanner") {
                            popUpTo("scanner") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },

                    onGuideClick = { category ->
                        navController.navigate(
                            "detail/${Uri.encode(category)}"
                        )
                    }
                )
            }

            // GUÍA
            composable("guide") {
                GuideScreen(
                    onCategoryClick = { category ->
                        navController.navigate(
                            "detail/${Uri.encode(category)}"
                        )
                    }
                )
            }

            // DETALLE DE CATEGORÍA
            composable("detail/{category}") { backStackEntry ->

                val category = Uri.decode(
                    backStackEntry.arguments?.getString("category")
                        ?: "Aprovechables"
                )

                DetailScreen(
                    category = category,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // HISTORIAL
            composable("history") {
                HistoryScreen(
                    databaseHelper = databaseHelper,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // PERFIL
            composable("profile") {
                ProfileScreen(
                    databaseHelper = databaseHelper,
                    onLogoutClick = {

                        auth.signOut()

                        navController.navigate("login") {
                            popUpTo("home") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EcoBottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {

    Surface(
        color = EcoWhite,
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(76.dp)
                .padding(horizontal = 8.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            EcoBottomItem(
                symbol = "⌂",
                label = "Inicio",
                selected = selectedRoute == "home",
                onClick = { onNavigate("home") },
                modifier = Modifier.weight(1f)
            )

            EcoBottomItem(
                symbol = "▤",
                label = "Guía",
                selected = selectedRoute == "guide",
                onClick = { onNavigate("guide") },
                modifier = Modifier.weight(1f)
            )

            // Botón central de identificación
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        onNavigate("scanner")
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = EcoGreen,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "◎",
                        color = EcoWhite,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Identificar",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selectedRoute == "scanner") {
                        EcoGreen
                    } else {
                        EcoGray
                    }
                )
            }

            EcoBottomItem(
                symbol = "●",
                label = "Perfil",
                selected = selectedRoute == "profile",
                onClick = { onNavigate("profile") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun EcoBottomItem(
    symbol: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val itemColor = if (selected) EcoGreen else EcoGray

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = symbol,
            color = itemColor,
            fontSize = 25.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )

        Text(
            text = label,
            color = itemColor,
            fontSize = 10.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            }
        )
    }
}
