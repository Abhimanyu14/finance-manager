package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue100
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class SearchBarData(
    val searchText: String,
    val placeholderText: String,
    val updateSearchText: (updatedSearchText: String) -> Unit,
)

@Composable
fun SearchBar(
    data: SearchBarData,
) {
    Box(
        modifier = Modifier
            .background(
                color = Surface,
            )
            .fillMaxWidth(),
    ) {
        TextField(
            value = data.searchText,
            onValueChange = {
                data.updateSearchText(it)
            },
            shape = RoundedCornerShape(
                percent = 50,
            ),
            singleLine = true,
            colors = TextFieldDefaults
                .textFieldColors(
                    backgroundColor = Blue100,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = if (data.searchText.isNotBlank()) {
                {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(
                                shape = CircleShape,
                            )
                            .clickable {
                                data.updateSearchText("")
                            },
                    )
                }
            } else {
                null
            },
            placeholder = {
                Text(
                    text = data.placeholderText,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                    ),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
        )
    }
}