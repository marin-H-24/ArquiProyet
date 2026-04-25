package com.marin.arquiproyet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.components.ApartadoActionDialog
import com.marin.arquiproyet.ui.components.GlassCard
import com.marin.arquiproyet.ui.components.NewApartadoDialog
import com.marin.arquiproyet.ui.theme.ColorBeige
import com.marin.arquiproyet.ui.theme.ColorDeepTeal
import android.net.Uri // Asegúrate de importar esto
import com.marin.arquiproyet.ui.components.NotesAndIdeasDialog
import com.marin.arquiproyet.ui.components.ProjectIconBox

@Composable
fun DetailScreen(
    projectName: String,
    onBack: () -> Unit
) {
    // ... tus otros estados ...
    var projectIconUri by remember { mutableStateOf<Uri?>(null) }
    // Estados para controlar las ventanas flotantes
    var showNewApartado by remember { mutableStateOf(false) }
    var selectedApartado by remember { mutableStateOf<String?>(null) }

    // Lista de apartados (Dashboard siempre debe estar por defecto según tus reglas)
    var apartados by remember {
        mutableStateOf(listOf("Dashboard", "ENTRADA", "ESTADISTIC", "PRINCIPAL", "CARACTERIS"))
    }
    // Estados para Notas e Ideas
    var showNotas by remember { mutableStateOf(false) }
    var showIdeasFut by remember { mutableStateOf(false) }

    // Listas temporales en memoria
    var notasList by remember { mutableStateOf(listOf<String>()) }
    var ideasFutList by remember { mutableStateOf(listOf<String>()) }

    // 1. Diálogo para crear un Nuevo Apartado
    if (showNewApartado) {
        NewApartadoDialog(
            onDismiss = { showNewApartado = false },
            onAdd = { newName ->
                apartados = apartados + newName
                showNewApartado = false
            }
        )
    }

    if (showNotas) {
        NotesAndIdeasDialog(
            title = "NOTAS",
            itemsList = notasList,
            onDismiss = { showNotas = false },
            onAddItem = { nuevaNota -> notasList = notasList + nuevaNota }
        )
    }

    if (showIdeasFut) {
        NotesAndIdeasDialog(
            title = "IDEAS FUTURAS",
            itemsList = ideasFutList,
            onDismiss = { showIdeasFut = false },
            onAddItem = { nuevaIdea -> ideasFutList = ideasFutList + nuevaIdea }
        )
    }

    // 2. Diálogo para Ver/Editar/Copiar un Apartado existente
    selectedApartado?.let { apartado ->
        ApartadoActionDialog(
            apartadoName = apartado,
            initialContent = "", // Aquí conectaremos la Base de Datos en el futuro
            onDismiss = { selectedApartado = null },
            onSave = { newContent ->
                // Lógica de guardado en Base de Datos irá aquí
                selectedApartado = null
            }
        )
    }

    // --- ESTRUCTURA VISUAL PRINCIPAL ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorDeepTeal)
            .padding(16.dp)
    ) {
        // CABECERA: Icono + Botones (NUEVO AP, NOTAS, IDEAS FUT)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Objeto: Icono del Proyecto
            ProjectIconBox(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                selectedImageUri = projectIconUri,
                onImageSelected = { uri -> projectIconUri = uri }
            )

            // Objeto: Columna de Botones
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón NUEVO AP
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable { showNewApartado = true } // <-- Abre el diálogo
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "NUEVO AP", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }

                // Botón NOTAS
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable { showNotas = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "NOTAS", color = ColorBeige, fontWeight = FontWeight.Bold)
                        Text(text = "+", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }

                // Botón IDEAS FUT
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable { showIdeasFut = true }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "IDEAS FUT", color = ColorBeige, fontWeight = FontWeight.Bold)
                        Text(text = "+", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TÍTULO: APARTADOS
        Text(
            text = "APARTADOS",
            color = ColorBeige,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // CUADRÍCULA DE APARTADOS (2 Columnas)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(apartados.size) { index ->
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { selectedApartado = apartados[index] } // <-- Abre el diálogo de edición/ver
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "APARTADO",
                            color = ColorBeige.copy(alpha = 0.7f),
                            fontSize = 10.sp
                        )
                        Text(
                            text = apartados[index],
                            color = ColorBeige,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}