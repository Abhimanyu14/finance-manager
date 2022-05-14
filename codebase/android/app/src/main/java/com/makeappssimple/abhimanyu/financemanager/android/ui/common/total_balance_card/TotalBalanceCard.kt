package com.makeappssimple.abhimanyu.financemanager.android.ui.common.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalBalanceCard(
    viewModel: TotalBalanceCardViewModel = hiltViewModel<TotalBalanceCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
    val totalBalanceAmount by viewModel.sourcesTotalBalanceAmountValue.collectAsState(
        initial = 0L,
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 16.dp,
                ),
            )
            .conditionalClickable(
                onClick = onClick,
            ),
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
            MyText(
                text = stringResource(
                    id = R.string.total_balance_card_title,
                ),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            )
            MyText(
                text = Amount(
                    value = totalBalanceAmount,
                ).toString(),
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
