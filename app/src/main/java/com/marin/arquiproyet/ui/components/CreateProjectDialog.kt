package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.marin.arquiproyet.ui.theme.DeepObsidian
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold

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
                    color = NeonGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Texto - CORREGIDO (Material 3)
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Nombre del Proyecto", color = GlacierWhite.copy(alpha = 0.7f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGold,
                        unfocusedBorderColor = GlacierWhite.copy(alpha = 0.3f),
                        focusedTextColor = GlacierWhite,
                        unfocusedTextColor = GlacierWhite,
                        cursorColor = NeonGold,
                        focusedLabelColor = NeonGold,
                        unfocusedLabelColor = GlacierWhite.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector de Categoría - CORREGIDO (Material 3)
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría", color = GlacierWhite.copy(alpha = 0.7f)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGold,
                            unfocusedBorderColor = GlacierWhite.copy(alpha = 0.3f),
                            focusedTextColor = GlacierWhite,
                            unfocusedTextColor = GlacierWhite,
                            focusedLabelColor = NeonGold,
                            unfocusedLabelColor = GlacierWhite.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable), // Versión estable
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(DeepObsidian)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category, color = GlacierWhite) },
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
                        Text("Cancelar", color = GlacierWhite.copy(alpha = 0.7f))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (projectName.isNotBlank() && selectedCategory != "Seleccionar Categoría") {
                                onCreate(projectName, selectedCategory)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Crear", color = DeepObsidian, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}