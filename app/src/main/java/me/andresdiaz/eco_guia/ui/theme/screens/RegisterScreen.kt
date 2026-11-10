
package me.andresdiaz.eco_guia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val RegisterGreen = Color(0xFF075B3D)
private val RegisterBackground = Color(0xFFF7F6FC)
private val RegisterField = Color(0xFFE9EAFB)
private val RegisterText = Color(0xFF202438)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RegisterBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        Surface(
            modifier = Modifier.height(90.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "E",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = RegisterGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "¡Únete a EcoGuía!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = RegisterText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crea tu cuenta y comienza a cuidar\nel planeta con nosotros.",
            fontSize = 16.sp,
            color = Color(0xFF62626B),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                RegisterField(
                    label = "Nombre completo",
                    value = name,
                    placeholder = "Ingresa tu nombre",
                    onValueChange = { name = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                RegisterField(
                    label = "Usuario",
                    value = username,
                    placeholder = "Crea un nombre de usuario",
                    onValueChange = { username = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                RegisterField(
                    label = "Correo electrónico",
                    value = email,
                    placeholder = "correo@ejemplo.com",
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                RegisterField(
                    label = "Contraseña",
                    value = password,
                    placeholder = "Crea una contraseña",
                    onValueChange = { password = it },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityChange = {
                        passwordVisible = !passwordVisible
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                RegisterField(
                    label = "Confirmar contraseña",
                    value = confirmPassword,
                    placeholder = "Repite tu contraseña",
                    onValueChange = { confirmPassword = it },
                    isPassword = true,
                    passwordVisible = confirmPasswordVisible,
                    onVisibilityChange = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RegisterGreen
                    )
                ) {
                    Text(
                        text = "Crear cuenta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿Ya tienes una cuenta? ",
                fontSize = 15.sp,
                color = Color(0xFF62626B)
            )

            Text(
                text = "Inicia sesión",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RegisterGreen,
                modifier = Modifier.clickable {
                    onLoginClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Términos de servicio    •    Política de privacidad",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
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
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = RegisterText
        )

        Spacer(modifier = Modifier.height(7.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(placeholder)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPassword) {
                    Text(
                        text = if (passwordVisible) "Ocultar" else "Ver",
                        color = Color(0xFF62626B),
                        modifier = Modifier
                            .clickable { onVisibilityChange() }
                            .padding(horizontal = 8.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = RegisterField,
                unfocusedContainerColor = RegisterField,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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
