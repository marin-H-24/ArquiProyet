package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold

@Composable
fun CategoryFilterDialog(
    categories: List<String>,
    onCategorySelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            cornerRadius = 24.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "FILTRAR POR",
                    color = NeonGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        CategoryItem("Todos los Proyectos") { onCategorySelected(null) }
                    }
                    items(categories) { category ->
                        CategoryItem(category) { onCategorySelected(category) }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cerrar", color = GlacierWhite.copy(alpha = 0.7f))
                }
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        cornerRadius = 12.dp
    ) {
        Text(
            text = name,
            color = GlacierWhite,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Medium
        )
    }
}