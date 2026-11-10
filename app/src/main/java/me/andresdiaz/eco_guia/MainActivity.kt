
package me.andresdiaz.eco_guia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.andresdiaz.eco_guia.ui.screens.HomeScreen
import me.andresdiaz.eco_guia.ui.screens.ScannerScreen
import me.andresdiaz.eco_guia.ui.screens.ResultScreen
import me.andresdiaz.eco_guia.ui.screens.GuideScreen
import me.andresdiaz.eco_guia.ui.screens.HistoryScreen
import me.andresdiaz.eco_guia.ui.screens.ProfileScreen
import me.andresdiaz.eco_guia.ui.screens.LoginScreen
import me.andresdiaz.eco_guia.ui.screens.RegisterScreen
import me.andresdiaz.eco_guia.ui.theme.ECO_GUIATheme

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
        bottomBar = {
            if (currentDestination?.route in bottomBarRoutes) {
                NavigationBar {

                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == "home"
                        } == true,
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {},
                        label = { Text("Inicio") }
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == "scanner"
                        } == true,
                        onClick = {
                            navController.navigate("scanner") {
                                popUpTo("home") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {},
                        label = { Text("Identificar") }
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == "guide"
                        } == true,
                        onClick = {
                            navController.navigate("guide") {
                                popUpTo("home") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {},
                        label = { Text("Guía") }
                    )

                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == "profile"
                        } == true,
                        onClick = {
                            navController.navigate("profile") {
                                popUpTo("home") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {},
                        label = { Text("Perfil") }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "login",
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
                    onResultClick = {
                        navController.navigate("result")
                    }
                )
            }

            // RESULTADO
            composable("result") {
                ResultScreen(
                    onSaveClick = {
                        navController.navigate("history")
                    },
                    onRescanClick = {
                        navController.navigate("scanner") {
                            popUpTo("scanner") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // GUÍA
            composable("guide") {
                GuideScreen()
            }

            // HISTORIAL
            composable("history") {
                HistoryScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // PERFIL
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
