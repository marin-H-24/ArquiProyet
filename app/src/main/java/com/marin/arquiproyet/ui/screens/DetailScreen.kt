package com.marin.arquiproyet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.components.*
import com.marin.arquiproyet.ui.theme.DeepObsidian
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold
import com.marin.arquiproyet.ui.viewmodel.MainViewModel

@Composable
fun DetailScreen(
    projectName: String,
    viewModel: MainViewModel, // Recibe el cerebro de la app
    onBack: () -> Unit
) {
    // 1. Obtenemos el proyecto real de la memoria
    val project = viewModel.projects.find { it.name == projectName }

    // Si por alguna razón no encuentra el proyecto, regresa a la pantalla anterior
    if (project == null) {
        onBack()
        return
    }

    // 2. Estados para mostrar los diálogos
    var showNewApartado by remember { mutableStateOf(false) }
    var selectedApartadoName by remember { mutableStateOf<String?>(null) }
    var showNotas by remember { mutableStateOf(false) }
    var showIdeasFut by remember { mutableStateOf(false) }

    // --- DIÁLOGOS CONECTADOS AL VIEWMODEL ---

    if (showNewApartado) {
        NewApartadoDialog(
            onDismiss = { showNewApartado = false },
            onAdd = { nuevoApartado ->
                viewModel.addApartado(projectName, nuevoApartado)
                showNewApartado = false
            }
        )
    }

    if (showNotas) {
        NotesAndIdeasDialog(
            title = "NOTAS",
            itemsList = project.notas,
            onDismiss = { showNotas = false },
            onAddItem = { nuevaNota ->
                viewModel.addNotaToProject(projectName, nuevaNota)
            }
        )
    }

    if (showIdeasFut) {
        NotesAndIdeasDialog(
            title = "IDEAS FUTURAS",
            itemsList = project.ideasFuturas,
            onDismiss = { showIdeasFut = false },
            onAddItem = { nuevaIdea ->
                viewModel.addIdeaFuturaToProject(projectName, nuevaIdea)
            }
        )
    }

    // Diálogo para leer y guardar dentro de un apartado
    selectedApartadoName?.let { name ->
        ApartadoActionDialog(
            apartadoName = name,
            initialContent = project.apartados[name] ?: "",
            onDismiss = { selectedApartadoName = null },
            onSave = { newContent ->
                viewModel.updateApartadoContent(projectName, name, newContent)
                selectedApartadoName = null
            }
        )
    }

    // --- INTERFAZ VISUAL ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .padding(20.dp)
    ) {
        // Título
        Text(
            text = projectName.uppercase(),
            color = NeonGold,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 20.dp),
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icono del proyecto persistente
            ProjectIconBox(
                modifier = Modifier.weight(1f).aspectRatio(1f),
                selectedImageUri = project.iconUriString?.let { android.net.Uri.parse(it) },
                onImageSelected = { uri ->
                    viewModel.updateProjectIcon(projectName, uri.toString())
                }
            )

            // Botones de acción
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailActionButton(text = "NUEVO AP") { showNewApartado = true }
                DetailActionButton(text = "NOTAS", showPlus = true) { showNotas = true }
                DetailActionButton(text = "IDEAS FUT", showPlus = true) { showIdeasFut = true }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "APARTADOS",
            color = GlacierWhite.copy(alpha = 0.6f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Cuadrícula de Apartados (lee directamente desde el proyecto guardado)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(project.apartados.keys.toList()) { apartadoName ->
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable { selectedApartadoName = apartadoName },
                    cornerRadius = 16.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "APARTADO",
                            color = NeonGold.copy(alpha = 0.5f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = apartadoName,
                            color = GlacierWhite,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

// Componente reutilizable para los botones de la pantalla de detalle
@Composable
fun DetailActionButton(text: String, showPlus: Boolean = false, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() },
        cornerRadius = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = if (showPlus) Arrangement.SpaceBetween else Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, color = GlacierWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            if (showPlus) {
                Text("+", color = NeonGold, fontWeight = FontWeight.Black, fontSize = 18.sp)
            }
        }
    }
}