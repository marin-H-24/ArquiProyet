package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.marin.arquiproyet.ui.theme.DeepObsidian
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold

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
            modifier = Modifier.fillMaxWidth().height(450.dp).padding(16.dp),
            cornerRadius = 24.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp)
            ) {
                Text(
                    text = apartadoName.uppercase(),
                    color = NeonGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isEditing) {
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGold,
                            unfocusedBorderColor = GlacierWhite.copy(alpha = 0.3f),
                            focusedTextColor = GlacierWhite,
                            unfocusedTextColor = GlacierWhite,
                            cursorColor = NeonGold
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    GlassCard(modifier = Modifier.fillMaxWidth().weight(1f), cornerRadius = 12.dp) {
                        Text(
                            text = content.ifEmpty { "No hay contenido. Presiona editar para agregar." },
                            color = GlacierWhite,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isEditing) {
                        TextButton(onClick = { isEditing = false; content = initialContent }) {
                            Text("Cancelar", color = GlacierWhite.copy(alpha = 0.7f))
                        }
                        Button(
                            onClick = { onSave(content); isEditing = false },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonGold)
                        ) {
                            Text("Guardar", color = DeepObsidian, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        TextButton(onClick = { clipboardManager.setText(AnnotatedString(content)) }) {
                            Text("Copiar", color = NeonGold)
                        }
                        Row {
                            TextButton(onClick = { isEditing = true }) {
                                Text("Editar", color = GlacierWhite)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDismiss,
                                colors = ButtonDefaults.buttonColors(containerColor = NeonGold)
                            ) {
                                Text("Cerrar", color = DeepObsidian, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}