package com.marin.arquiproyet.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.components.*
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold
import com.marin.arquiproyet.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    // Sincronización con el cerebro de la app (ViewModel)
    val projects = viewModel.projects
    val globalIdeas = viewModel.globalIdeas

    // Estados de control de interfaz
    var showCreateDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showGlobalIdeasDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    // Lógica de filtrado reactiva
    val filteredProjects = if (selectedFilter == null) {
        projects
    } else {
        projects.filter { it.category == selectedFilter }
    }

    // Gestión de Diálogos
    if (showCreateDialog) {
        CreateProjectDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { name, cat ->
                val dateStr = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date())
                viewModel.addProject(name, cat, dateStr)
                showCreateDialog = false
            }
        )
    }

    if (showCategoryDialog) {
        CategoryFilterDialog(
            categories = projects.map { it.category }.distinct(),
            onCategorySelected = {
                selectedFilter = it
                showCategoryDialog = false
            },
            onDismiss = { showCategoryDialog = false }
        )
    }

    if (showGlobalIdeasDialog) {
        NotesAndIdeasDialog(
            title = "BÓVEDA DE IDEAS",
            itemsList = globalIdeas,
            onDismiss = { showGlobalIdeasDialog = false },
            onAddItem = { viewModel.addGlobalIdea(it) }
        )
    }

    // Diseño Principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        // Título con la X geométrica del boceto
        AppTitleX(modifier = Modifier.padding(bottom = 32.dp))

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // COLUMNA IZQUIERDA: Lista de Proyectos
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "proyectos",
                    color = GlacierWhite.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredProjects) { project ->
                        ProjectCard(
                            title = project.name,
                            date = project.date,
                            onClick = { onNavigateToDetail(project.name) }
                        )
                    }
                }
            }

            // COLUMNA DERECHA: Widgets Pro
            Column(modifier = Modifier.weight(1f)) {
                // Widget NUEVO PROYECTO: Ocupa el 1.8 del espacio disponible
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.8f)
                        .clickable { showCreateDialog = true },
                    cornerRadius = 20.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "NUEVO",
                            color = GlacierWhite,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            fontSize = 16.sp
                        )

                        // Bombillo con rayos de luz (rediseñado para llenar el espacio)
                        LampCanvas(modifier = Modifier.fillMaxWidth().weight(1f).padding(10.dp))

                        Text(
                            text = "PROYEC",
                            color = GlacierWhite,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Widget CATEGORÍA: Con las barras y círculos de tu dibujo
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.3f)
                        .clickable { showCategoryDialog = true },
                    cornerRadius = 20.dp
                ) {
                    CategoryWidget(
                        selectedFilter = selectedFilter,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Widget IDEAS GLOBALES: Acceso rápido a la bóveda
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .clickable { showGlobalIdeasDialog = true },
                    cornerRadius = 16.dp
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "IDEAS...",
                                color = GlacierWhite,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            if (globalIdeas.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "(${globalIdeas.size})",
                                    color = NeonGold,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}