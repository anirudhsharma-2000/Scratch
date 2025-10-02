package com.smitcoderx.scratch.ui.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.ui.theme.Typography

@Composable
fun CategoryFilters(
    categories: List<Category>,
    selectedCategory: Category?,
    selected: (Category?) -> Unit
) {
    if (categories.isEmpty()) return
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) {
            FilterChip(
                selected = selectedCategory == it,
                onClick = {
                    if (selectedCategory == it) {
                        selected(null)
                    } else {
                        selected(it)
                    }
                },
                label = {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        text = it.name,
                        style = Typography.labelMedium
                    )
                }, shape = RoundedCornerShape(30.dp)
            )
        }
    }
}
