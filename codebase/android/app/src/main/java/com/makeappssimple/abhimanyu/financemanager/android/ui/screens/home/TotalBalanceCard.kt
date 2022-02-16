package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green700

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TotalBalanceCard(
    total: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(
            size = 16.dp,
        ),
        modifier = Modifier
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp,
            )
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Green700,
                )
                .padding(
                    all = 16.dp,
                ),
        ) {
            Text(
                text = "Total Balance",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Text(
                text = total,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}
