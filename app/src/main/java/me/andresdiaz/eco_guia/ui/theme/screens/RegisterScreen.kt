
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.andresdiaz.eco_guia.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.Image

private val RegisterGreen = Color(0xFF07583D)
private val RegisterBackground = Color(0xFFF7F6FC)
private val RegisterField = Color(0xFFF0EFFF)
private val RegisterText = Color(0xFF17223D)
private val RegisterGray = Color(0xFF777B88)
private val RegisterBorder = Color(0xFFE8E7EF)
private val RegisterRed = Color(0xFFB3261E)

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth = remember { FirebaseAuth.getInstance() }
    val firestore = remember { FirebaseFirestore.getInstance() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RegisterBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.ecoguia_logo),
            contentDescription = "Logo de EcoGuía",
            modifier = Modifier.size(155.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Únete a EcoGuía",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = RegisterText
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Crea tu cuenta y comienza a cuidar\nel planeta con nosotros.",
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = RegisterGray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(23.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                RegisterBorder
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(21.dp)
            ) {
                Text(
                    text = "Crear una cuenta",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = RegisterText
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Completa tus datos para comenzar.",
                    fontSize = 12.sp,
                    color = RegisterGray
                )

                Spacer(modifier = Modifier.height(21.dp))

                RegisterField(
                    label = "Nombre completo",
                    value = name,
                    placeholder = "Ingresa tu nombre",
                    onValueChange = {
                        name = it
                        errorMessage = ""
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterField(
                    label = "Usuario",
                    value = username,
                    placeholder = "Crea un nombre de usuario",
                    onValueChange = {
                        username = it
                        errorMessage = ""
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterField(
                    label = "Correo electrónico",
                    value = email,
                    placeholder = "correo@ejemplo.com",
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterField(
                    label = "Contraseña",
                    value = password,
                    placeholder = "Crea una contraseña",
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityChange = {
                        passwordVisible = !passwordVisible
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegisterField(
                    label = "Confirmar contraseña",
                    value = confirmPassword,
                    placeholder = "Repite tu contraseña",
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },
                    isPassword = true,
                    passwordVisible = confirmPasswordVisible,
                    onVisibilityChange = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = errorMessage,
                        color = RegisterRed,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        if (isLoading) return@Button

                        errorMessage = ""

                        val cleanName = name.trim()
                        val cleanUsername = username.trim()
                        val cleanEmail = email.trim().lowercase()

                        when {
                            cleanName.isEmpty() ||
                                    cleanUsername.isEmpty() ||
                                    cleanEmail.isEmpty() ||
                                    password.isEmpty() ||
                                    confirmPassword.isEmpty() -> {
                                errorMessage = "Completa todos los campos."
                            }

                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(cleanEmail).matches() -> {
                                errorMessage = "Ingresa un correo electrónico válido."
                            }

                            cleanUsername.contains(" ") -> {
                                errorMessage = "El usuario no debe contener espacios."
                            }

                            password.length < 6 -> {
                                errorMessage =
                                    "La contraseña debe tener al menos 6 caracteres."
                            }

                            password != confirmPassword -> {
                                errorMessage = "Las contraseñas no coinciden."
                            }

                            else -> {
                                isLoading = true

                                auth.createUserWithEmailAndPassword(
                                    cleanEmail,
                                    password
                                ).addOnCompleteListener { task ->

                                    if (task.isSuccessful) {
                                        val firebaseUser = auth.currentUser

                                        if (firebaseUser == null) {
                                            isLoading = false
                                            errorMessage =
                                                "No se pudo obtener la cuenta creada."
                                        } else {
                                            val userData = hashMapOf(
                                                "uid" to firebaseUser.uid,
                                                "name" to cleanName,
                                                "username" to cleanUsername,
                                                "email" to cleanEmail,
                                                "createdAt" to System.currentTimeMillis(),
                                                "points" to 0,
                                                "level" to 1
                                            )

                                            firestore.collection("users")
                                                .document(firebaseUser.uid)
                                                .set(userData)
                                                .addOnSuccessListener {
                                                    isLoading = false
                                                    onRegisterClick()
                                                }
                                                .addOnFailureListener { exception ->
                                                    isLoading = false
                                                    errorMessage =
                                                        "La cuenta se creó, pero no se pudo guardar el perfil: " +
                                                                (exception.localizedMessage
                                                                    ?: "Error de Firestore")
                                                }
                                        }
                                    } else {
                                        isLoading = false

                                        errorMessage = when (
                                            task.exception?.javaClass?.simpleName
                                        ) {
                                            "FirebaseAuthUserCollisionException" ->
                                                "Este correo ya tiene una cuenta registrada."

                                            "FirebaseAuthWeakPasswordException" ->
                                                "La contraseña es demasiado débil."

                                            "FirebaseAuthInvalidCredentialsException" ->
                                                "El correo electrónico no es válido."

                                            else ->
                                                task.exception?.localizedMessage
                                                    ?: "No se pudo crear la cuenta."
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
                        containerColor = RegisterGreen,
                        disabledContainerColor = RegisterGreen.copy(alpha = 0.6f)
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
                            text = "Crear cuenta",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿Ya tienes una cuenta? ",
                fontSize = 13.sp,
                color = RegisterGray
            )

            Text(
                text = "Inicia sesión",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = RegisterGreen,
                modifier = Modifier.clickable {
                    onLoginClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "ECOGUÍA",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = RegisterGreen
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun RegisterField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {}
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = RegisterText
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 13.sp,
                    color = RegisterGray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(13.dp),
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPassword) {
                    Text(
                        text = if (passwordVisible) "Ocultar" else "Ver",
                        color = RegisterGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable {
                                onVisibilityChange()
                            }
                            .padding(horizontal = 10.dp)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RegisterGreen,
                unfocusedBorderColor = RegisterBorder,
                focusedContainerColor = RegisterField,
                unfocusedContainerColor = RegisterField,
                cursorColor = RegisterGreen
            )
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen()
    }
}
