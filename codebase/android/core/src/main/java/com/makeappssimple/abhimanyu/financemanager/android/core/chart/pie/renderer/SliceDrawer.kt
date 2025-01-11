package com.makeappssimple.abhimanyu.financemanager.android.core.chart.pie.renderer

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.makeappssimple.abhimanyu.financemanager.android.core.chart.pie.PieChartItemData

public interface SliceDrawer {
    public fun drawSlice(
        drawScope: DrawScope,
        canvas: Canvas,
        area: Size,
        startAngle: Float,
        sweepAngle: Float,
        slice: PieChartItemData,
    )
}
