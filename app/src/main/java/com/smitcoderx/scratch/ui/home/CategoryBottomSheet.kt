package com.smitcoderx.scratch.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.smitcoderx.scratch.data.home.Categories
import com.smitcoderx.scratch.data.home.Category
import com.smitcoderx.scratch.ui.theme.Typography
import kotlin.enums.EnumEntries

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CategoryBottomSheet(
    viewModel: CategoryViewModel,
    categories: EnumEntries<Categories>,
    onDismissRequest: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("") }
    var colorPicker by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Red.value) }
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Create new category",
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(color = Color(selectedColor), shape = CircleShape)
                        .clickable(onClick = { colorPicker = !colorPicker })
                )
                if (colorPicker) {
                    ColorDialog(
                        selectedColor = { selectedColor = it.value },
                        closeDialog = { colorPicker = !colorPicker })
                }
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = categoryName,
                minLines = 1,
                shape = RoundedCornerShape(20.dp),
                onValueChange = { categoryName = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = "Category? Drop the name here! 🤔",
                        style = Typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    )
                }
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                categories.forEach {
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        selected = selectedFilter == it.title,
                        label = { Text(text = it.title, style = Typography.labelSmall) },
                        onClick = { selectedFilter = it.title }
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                onClick = {
                    viewModel.addCategory(
                        Category(
                            (0..9999).random().toInt(), // TODO: Change this also 
                            categoryName,
                            selectedFilter,
                            selectedColor
                        )
                    )
                    onDismissRequest()
                }) {
                Icon(Icons.Default.Done, contentDescription = "Done")
                Text(
                    text = "Done",
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ColorDialog(selectedColor: (Color) -> Unit, closeDialog: () -> Unit) {
    var selected by remember { mutableStateOf("") }
    var selectedByUser by remember { mutableStateOf(false) }
    AlertDialog(onDismissRequest = closeDialog, confirmButton = {
        Text(
            "Done", style = Typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable(onClick = closeDialog, role = Role.Button)
        )
    }, title = {
        Column {
            Text("Pick Color", style = Typography.displayMedium)
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 10.dp),
                controller = rememberColorPickerController(),
                onColorChanged = {
                    selectedColor(it.color)
                    selected = "#${it.hexCode}"
                    selectedByUser = it.fromUser
                }
            )
            AnimatedVisibility(selectedByUser && selected.isNotEmpty()) {
                Text(
                    selected.toString(),
                    color = Color(selected.toColorInt()),
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    })
}
