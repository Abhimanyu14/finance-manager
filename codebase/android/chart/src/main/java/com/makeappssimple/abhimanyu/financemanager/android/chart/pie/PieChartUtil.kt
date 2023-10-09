package com.makeappssimple.abhimanyu.financemanager.android.chart.pie

internal object PieChartUtil {
    fun calculateAngle(
        sliceLength: Float,
        totalLength: Float,
        progress: Float,
    ): Float {
        return 360.0F * (sliceLength * progress) / totalLength
    }
}
