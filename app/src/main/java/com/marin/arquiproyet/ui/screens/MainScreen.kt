package com.marin.arquiproyet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa Box, Column, Row, etc.
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.components.GlassCard
import com.marin.arquiproyet.ui.components.LampCanvas
import com.marin.arquiproyet.ui.components.ProjectCard
import com.marin.arquiproyet.ui.theme.ColorBeige
import com.marin.arquiproyet.ui.theme.ColorDeepTeal

@Composable
fun MainScreen(
    onNavigateToDetail: () -> Unit,
    onOpenNewProject: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorDeepTeal)
            .padding(16.dp)
    ) {
        // Objeto 1: Título
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(text = "ARQUIPROYET", color = ColorBeige, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text(text = " X", color = ColorBeige, fontSize = 28.sp, fontWeight = FontWeight.Light)
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Columna Izquierda: Proyectos
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Text(text = "proyectos", color = ColorBeige, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(4) { index ->
                    ProjectCard(
                        title = listOf("HORIZONT", "IDBOS", "WSFINANZ", "E.COMERCE")[index],
                        date = listOf("10/02/26", "02/10/25", "01/09/25", "03/05/25")[index],
                        onClick = onNavigateToDetail
                    )
                }
            }

            // Columna Derecha: Widgets
            Column(modifier = Modifier.weight(1f)) {
                // Nuevo Proyecto (Bombillo)
                GlassCard(modifier = Modifier.fillMaxWidth().weight(1.5f)) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "NUEVO", color = ColorBeige, fontWeight = FontWeight.Bold)
                        LampCanvas(modifier = Modifier.weight(1f).fillMaxWidth())
                        Text(text = "PROYEC", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Categoría
                GlassCard(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Categoria", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ideas
                GlassCard(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "IDEAS...", color = ColorBeige, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}