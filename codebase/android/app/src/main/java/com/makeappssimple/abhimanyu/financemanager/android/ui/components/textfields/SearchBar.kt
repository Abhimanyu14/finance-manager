package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue100
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class SearchBarData(
    val searchText: String,
    val placeholderText: String,
    val updateSearchText: (updatedSearchText: String) -> Unit,
)

@Composable
fun SearchBarContainer(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = Surface,
            )
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
    ) {
        content()
    }
}

@Composable
fun SearchBar(
    data: SearchBarData,
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
                containerColor = Blue100,
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
                        }
                        .padding(
                            all = 8.dp,
                        ),
                )
            }
        } else {
            null
        },
        placeholder = {
            MyText(
                text = data.placeholderText,
                style = TextStyle(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
    )
}
