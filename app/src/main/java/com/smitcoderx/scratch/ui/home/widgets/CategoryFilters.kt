package com.smitcoderx.scratch.ui.home.widgets

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.ui.theme.Typography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CategoryFilters(
    categories: List<Category>,
    selectedCategory: Category?,
    selected: (Category?) -> Unit,
    editCategory: Category? = null,
    onAddFilter: () -> Unit = {},
    onLongClick: (Category) -> Unit = {},
) {
    val context = LocalContext.current
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        item {
            Row(Modifier.padding(top = 3.dp)) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Filter",
                    modifier = Modifier
                        .border(color = Color.Gray, shape = CircleShape, width = 1.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable { onAddFilter() }
                )
                Spacer(Modifier.padding(horizontal = 4.dp))
                VerticalDivider(modifier = Modifier.height(40.dp), thickness = 1.dp)
            }
        }
        itemsIndexed(categories) { index, filter ->
            val inputChipInteractionSource = remember { MutableInteractionSource() }
            Box {
                FilterChip(
                    interactionSource = inputChipInteractionSource,
                    selected = selectedCategory == filter,
                    onClick = {},
                    label = {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                            text = filter.name,
                            style = Typography.labelMedium
                        )
                    }, shape = RoundedCornerShape(30.dp),
                    trailingIcon = {
                        AnimatedVisibility(editCategory != null && index != 0 && editCategory == filter) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Selected Filter")
                            return@AnimatedVisibility
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .combinedClickable(
                            onLongClick = {
                                if (index != 0) onLongClick(filter)
                                else Toast.makeText(
                                    context,
                                    "Cannot edit this filter",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onClick = {
                                if (selectedCategory == filter) {
                                    selected(null)
                                } else {
                                    selected(filter)
                                }
                            },
                            interactionSource = inputChipInteractionSource,
                            indication = null,
                        )
                ) { }
            }
        }
    }
}
