
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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import me.andresdiaz.eco_guia.R


private val LoginGreen = Color(0xFF07583D)
private val LoginMint = Color(0xFF62F58C)
private val LoginBackground = Color(0xFFF7F6FC)
private val LoginField = Color(0xFFF0EFFF)
private val LoginText = Color(0xFF17223D)
private val LoginGray = Color(0xFF777B88)
private val LoginBorder = Color(0xFFE8E7EF)
private val LoginRed = Color(0xFFB3261E)

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth = remember { FirebaseAuth.getInstance() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(86.dp)
                .background(Color.White, CircleShape)
                .border(
                    width = 3.dp,
                    color = LoginMint.copy(alpha = 0.55f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ecoguia_logo),
                contentDescription = "Logo de EcoGuía",
                modifier = Modifier
                    .size(190.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Bienvenido de vuelta",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = LoginText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa a tu cuenta y continúa\nhaciendo la diferencia.",
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = LoginGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                LoginBorder
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(21.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = LoginText
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Ingresa tus datos para continuar.",
                    fontSize = 12.sp,
                    color = LoginGray
                )

                Spacer(modifier = Modifier.height(23.dp))

                Text(
                    text = "Correo electrónico",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LoginText
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                        successMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "correo@ejemplo.com",
                            fontSize = 13.sp,
                            color = LoginGray
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(13.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LoginGreen,
                        unfocusedBorderColor = LoginBorder,
                        focusedContainerColor = LoginField,
                        unfocusedContainerColor = LoginField,
                        cursorColor = LoginGreen
                    )
                )

                Spacer(modifier = Modifier.height(19.dp))

                Text(
                    text = "Contraseña",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LoginText
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                        successMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Ingresa tu contraseña",
                            fontSize = 13.sp,
                            color = LoginGray
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(13.dp),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        Text(
                            text = if (passwordVisible) "Ocultar" else "Ver",
                            modifier = Modifier
                                .clickable {
                                    passwordVisible = !passwordVisible
                                }
                                .padding(horizontal = 10.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LoginGreen
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LoginGreen,
                        unfocusedBorderColor = LoginBorder,
                        focusedContainerColor = LoginField,
                        unfocusedContainerColor = LoginField,
                        cursorColor = LoginGreen
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LoginGreen,
                        modifier = Modifier.clickable {
                            errorMessage = ""
                            successMessage = ""

                            val cleanEmail = email.trim()

                            when {
                                cleanEmail.isEmpty() -> {
                                    errorMessage =
                                        "Ingresa tu correo para recuperar tu contraseña."
                                }

                                !android.util.Patterns.EMAIL_ADDRESS
                                    .matcher(cleanEmail).matches() -> {
                                    errorMessage = "Ingresa un correo válido."
                                }

                                else -> {
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
                        }
                    )
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(9.dp))

                    Text(
                        text = errorMessage,
                        color = LoginRed,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                if (successMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(9.dp))

                    Text(
                        text = successMessage,
                        color = LoginGreen,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

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
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LoginGreen,
                        disabledContainerColor = LoginGreen.copy(alpha = 0.6f)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes una cuenta? ",
                fontSize = 13.sp,
                color = LoginGray
            )

            Text(
                text = "Regístrate",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = LoginGreen,
                modifier = Modifier.clickable {
                    onRegisterClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "ECOGUÍA",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = LoginGreen
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
