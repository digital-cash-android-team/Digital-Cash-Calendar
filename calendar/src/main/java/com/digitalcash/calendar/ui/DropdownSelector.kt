package com.digitalcash.split.ui.main.calendar.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropdownSelector(
    modifier: Modifier = Modifier,
    menuShape: Shape = RoundedCornerShape(25.dp),
    shadowElevation: Dp = 0.dp,
    tonalElevation: Dp = 0.dp,
    menuOffset: DpOffset = DpOffset(0.dp, 0.dp),
    menuStyle: TextStyle = MaterialTheme.typography.labelSmall,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    label: @Composable () -> Unit = { Text(text = "Label") },
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    optionLabel: (T) -> String = { it.toString() },
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clickable { expanded = true }
            .padding(vertical = 8.dp),
    ) {
        label()

        Row(
            modifier = Modifier
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = optionLabel(selectedOption),
                style = MaterialTheme.typography.bodyLarge,
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier,
            containerColor = containerColor,
            shape = menuShape, shadowElevation = shadowElevation, tonalElevation = tonalElevation,
            offset = menuOffset,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(text = optionLabel(option), style = menuStyle) },
                )
            }
        }
    }
}