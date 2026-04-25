package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.marin.arquiproyet.ui.theme.ColorBeige
import com.marin.arquiproyet.ui.theme.ColorDeepTeal
import com.marin.arquiproyet.ui.theme.ColorLightTeal

@Composable
fun NewApartadoDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var apartadoName by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            cornerRadius = 24.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NUEVO APARTADO",
                    color = ColorBeige,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = apartadoName,
                    onValueChange = { apartadoName = it },
                    label = { Text("Nombre del apartado", color = ColorBeige.copy(alpha = 0.7f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorLightTeal,
                        unfocusedBorderColor = ColorBeige.copy(alpha = 0.5f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = ColorLightTeal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = ColorBeige.copy(alpha = 0.7f))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { if (apartadoName.isNotBlank()) onAdd(apartadoName) },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorLightTeal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Agregar", color = ColorDeepTeal, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}