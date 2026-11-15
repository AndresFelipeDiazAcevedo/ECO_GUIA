
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val DarkGreen = Color(0xFF075B3D)
private val LightGreen = Color(0xFF62F58C)
private val BackgroundColor = Color(0xFFF7F6FC)
private val FieldColor = Color(0xFFE9EAFB)
private val TextColor = Color(0xFF202438)

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth = remember { FirebaseAuth.getInstance() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(65.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .border(4.dp, Color(0xFFCFF8DD), CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "E",
                color = DarkGreen,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "¡Bienvenido de vuelta!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa a tu cuenta para continuar\nclasificando y cuidando el planeta.",
            fontSize = 17.sp,
            color = Color(0xFF62626B),
            textAlign = TextAlign.Center,
            lineHeight = 25.sp
        )

        Spacer(modifier = Modifier.height(65.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Correo electrónico",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(7.dp))

                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                        successMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("correo@ejemplo.com")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = FieldColor,
                        unfocusedContainerColor = FieldColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Contraseña",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(7.dp))

                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                        successMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("••••••••")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        Text(
                            text = if (passwordVisible) "Ocultar" else "Ver",
                            color = Color(0xFF62626B),
                            modifier = Modifier
                                .clickable {
                                    passwordVisible = !passwordVisible
                                }
                                .padding(horizontal = 8.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = FieldColor,
                        unfocusedContainerColor = FieldColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = {
                                rememberMe = it
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = DarkGreen
                            )
                        )

                        Text(
                            text = "Recordarme",
                            fontSize = 14.sp,
                            color = Color(0xFF62626B)
                        )
                    }

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 13.sp,
                        color = DarkGreen,
                        modifier = Modifier.clickable {

                            errorMessage = ""
                            successMessage = ""

                            val cleanEmail = email.trim()

                            if (cleanEmail.isEmpty()) {
                                errorMessage =
                                    "Ingresa tu correo para recuperar tu contraseña."
                            } else if (!android.util.Patterns.EMAIL_ADDRESS
                                    .matcher(cleanEmail).matches()) {
                                errorMessage = "Ingresa un correo válido."
                            } else {

                                isLoading = true

                                auth.sendPasswordResetEmail(cleanEmail)
                                    .addOnCompleteListener { task ->

                                        isLoading = false

                                        if (task.isSuccessful) {
                                            successMessage =
                                                "Te enviamos un enlace para restablecer tu contraseña."
                                        } else {
                                            errorMessage =
                                                "No se pudo enviar el correo. " +
                                                        (task.exception?.localizedMessage
                                                            ?: "Inténtalo nuevamente.")
                                        }
                                    }

                                onForgotPasswordClick()
                            }
                        }
                    )
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = errorMessage,
                        color = Color(0xFFB3261E),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }

                if (successMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = successMessage,
                        color = DarkGreen,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {

                        if (isLoading) return@Button

                        errorMessage = ""
                        successMessage = ""

                        val cleanEmail = email.trim()

                        when {
                            cleanEmail.isEmpty() -> {
                                errorMessage = "Ingresa tu correo electrónico."
                            }

                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(cleanEmail).matches() -> {
                                errorMessage = "Ingresa un correo válido."
                            }

                            password.isEmpty() -> {
                                errorMessage = "Ingresa tu contraseña."
                            }

                            else -> {

                                isLoading = true

                                auth.signInWithEmailAndPassword(
                                    cleanEmail,
                                    password
                                ).addOnCompleteListener { task ->

                                    isLoading = false

                                    if (task.isSuccessful) {
                                        onLoginClick()
                                    } else {

                                        errorMessage = when (
                                            (task.exception as? com.google.firebase.auth.FirebaseAuthException)
                                                ?.errorCode
                                        ) {
                                            "ERROR_USER_NOT_FOUND" ->
                                                "No existe una cuenta con este correo."

                                            "ERROR_WRONG_PASSWORD",
                                            "ERROR_INVALID_CREDENTIAL" ->
                                                "El correo o la contraseña son incorrectos."

                                            "ERROR_USER_DISABLED" ->
                                                "Esta cuenta ha sido deshabilitada."

                                            "ERROR_INVALID_EMAIL" ->
                                                "El correo electrónico no es válido."

                                            "ERROR_TOO_MANY_REQUESTS" ->
                                                "Demasiados intentos. Inténtalo más tarde."

                                            else ->
                                                task.exception?.localizedMessage
                                                    ?: "No se pudo iniciar sesión."
                                        }
                                    }
                                }
                            }
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    )
                ) {

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Iniciar sesión  →",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(95.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes una cuenta? ",
                fontSize = 16.sp,
                color = Color(0xFF62626B)
            )

            Text(
                text = "Regístrate",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier.clickable {
                    onRegisterClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Términos de servicio    •    Política de privacidad",
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}
