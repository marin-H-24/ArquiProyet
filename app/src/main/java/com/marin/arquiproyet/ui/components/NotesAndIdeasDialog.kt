package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun NotesAndIdeasDialog(
    title: String,
    itemsList: List<String>,
    onDismiss: () -> Unit,
    onAddItem: (String) -> Unit
) {
    var newItemText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
            cornerRadius = 24.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = title.uppercase(),
                    color = NeonGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(itemsList) { item ->
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            cornerRadius = 12.dp
                        ) {
                            Text(
                                text = item,
                                color = GlacierWhite,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = newItemText,
                        onValueChange = { newItemText = it },
                        placeholder = { Text("Escribe aquí...", color = GlacierWhite.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGold,
                            unfocusedBorderColor = GlacierWhite.copy(alpha = 0.3f),
                            focusedTextColor = GlacierWhite,
                            unfocusedTextColor = GlacierWhite,
                            cursorColor = NeonGold
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (newItemText.isNotBlank()) {
                                onAddItem(newItemText)
                                newItemText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGold),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(52.dp)
                    ) {
                        Text(
                            text = "+",
                            color = DeepObsidian,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}