package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.marin.arquiproyet.ui.theme.ColorBeige
import com.marin.arquiproyet.ui.theme.ColorDeepTeal
import com.marin.arquiproyet.ui.theme.ColorLightTeal

@Composable
fun ApartadoActionDialog(
    apartadoName: String,
    initialContent: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf(initialContent) }
    val clipboardManager = LocalClipboardManager.current

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier.fillMaxWidth().height(400.dp).padding(16.dp),
            cornerRadius = 24.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text(
                    text = apartadoName.uppercase(),
                    color = ColorBeige,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ColorLightTeal,
                            unfocusedBorderColor = ColorBeige.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    GlassCard(modifier = Modifier.fillMaxWidth().weight(1f), cornerRadius = 12.dp) {
                        Text(
                            text = content.ifEmpty { "No hay contenido. Presiona editar para agregar." },
                            color = ColorBeige,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isEditing) {
                        TextButton(onClick = { isEditing = false; content = initialContent }) {
                            Text("Cancelar", color = ColorBeige.copy(alpha = 0.7f))
                        }
                        Button(
                            onClick = { onSave(content); isEditing = false },
                            colors = ButtonDefaults.buttonColors(containerColor = ColorLightTeal)
                        ) {
                            Text("Guardar", color = ColorDeepTeal)
                        }
                    } else {
                        TextButton(onClick = { clipboardManager.setText(AnnotatedString(content)) }) {
                            Text("Copiar", color = ColorLightTeal)
                        }
                        Row {
                            TextButton(onClick = { isEditing = true }) {
                                Text("Editar", color = ColorBeige)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDismiss,
                                colors = ButtonDefaults.buttonColors(containerColor = ColorLightTeal)
                            ) {
                                Text("Cerrar", color = ColorDeepTeal)
                            }
                        }
                    }
                }
            }
        }
    }
}