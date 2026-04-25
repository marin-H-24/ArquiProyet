package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, category: String) -> Unit
) {
    var projectName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Seleccionar Categoría") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "Finanzas", "Ventas / E-commerce", "Gestión de Tiempo", "Productividad",
        "Educación", "Salud / Fitness", "Entretenimiento", "Redes Sociales",
        "Herramientas / Utilidades", "Inteligencia Artificial", "Viajes / Turismo",
        "Gastronomía", "Inmobiliaria", "Logística", "Personal / Diario", "Videojuegos"
    )

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
                    text = "NUEVO PROYECTO",
                    color = ColorBeige,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Texto para el Nombre corregido
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Nombre del Proyecto", color = ColorBeige.copy(alpha = 0.7f)) },
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

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de Categoría corregido
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría", color = ColorBeige.copy(alpha = 0.7f)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ColorLightTeal,
                            unfocusedBorderColor = ColorBeige.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        // Corrección de la deprecación de menuAnchor
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(ColorDeepTeal)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category, color = ColorBeige) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }

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
                        onClick = {
                            if (projectName.isNotBlank() && selectedCategory != "Seleccionar Categoría") {
                                onCreate(projectName, selectedCategory)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorLightTeal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Crear", color = ColorDeepTeal, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}