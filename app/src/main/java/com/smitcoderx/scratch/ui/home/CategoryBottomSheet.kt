package com.smitcoderx.scratch.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    modifier: Modifier = Modifier,
    isSheetOpen: Boolean,
    category: Category? = null,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(isSheetOpen) {
        ModalBottomSheet(modifier = Modifier, sheetState = sheetState, onDismissRequest = {
            onDismiss()
            coroutineScope.launch {
                sheetState.hide()
            }
        }) {
            Column(
                Modifier.padding(horizontal = 8.dp).padding(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextInput(hint = "Enter Name", value = category?.name) { }
                TextInput(hint = "Enter Type", value = category?.type) { }
                Row(
                    modifier = Modifier.height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {},
                        shape = ShapeDefaults.ExtraLarge.copy(CornerSize(20.dp))
                    ) {
                        Text("Done", style = Typography.labelMedium)
                    }
                    if (category != null) {
                        OutlinedButton(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onClick = {},
                            shape = ShapeDefaults.ExtraLarge.copy(CornerSize(20.dp))
                        ) {
                            Text("Delete", style = Typography.labelMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    hint: String,
    value: String? = null,
    onValueChange: (String) -> Unit
) {
    var input by remember { mutableStateOf(value ?: "") }
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = input,
        minLines = 1,
        shape = RoundedCornerShape(20.dp),
        onValueChange = { input = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onValueChange(input) }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = hint,
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            )
        }
    )
}
