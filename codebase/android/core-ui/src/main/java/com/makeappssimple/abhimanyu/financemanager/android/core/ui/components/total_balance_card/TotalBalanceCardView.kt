package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.White
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

data class TotalBalanceCardViewData(
    val totalBalanceAmount: Long,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun TotalBalanceCardView(
    data: TotalBalanceCardViewData,
) {
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
                onClick = data.onClick,
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
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = R.string.total_balance_card_title,
                style = TextStyle(
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ),
            )
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = Amount(
                    value = data.totalBalanceAmount,
                ).toString(),
                style = TextStyle(
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}
