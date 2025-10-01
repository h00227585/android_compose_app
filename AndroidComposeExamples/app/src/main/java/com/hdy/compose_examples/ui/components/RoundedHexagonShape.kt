package com.hdy.compose_examples.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class RoundedHexagonShape(
    private val cornerRadius: Float = 0f,
    private val rotation: Float = 0f // 旋转角度，单位度
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()

        val radius = min(size.width, size.height) / 2f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        val angle = 60.0
        val radRotation = Math.toRadians(rotation.toDouble())

        // 注意: 默认平边在上, -30控制尖顶在上
        val points = (0 until 6).map { i ->
            val theta = Math.toRadians(angle * i/* - 30*/) + radRotation // ✅ 加上旋转
            Offset(
                (centerX + radius * cos(theta)).toFloat(),
                (centerY + radius * sin(theta)).toFloat()
            )
        }

        for (i in points.indices) {
            val current = points[i]
            val next = points[(i + 1) % points.size]

            val dir = Offset(next.x - current.x, next.y - current.y)
            val len = dir.getDistance()
            val norm = Offset(dir.x / len, dir.y / len)

            val start = Offset(
                current.x + norm.x * cornerRadius,
                current.y + norm.y * cornerRadius
            )
            val end = Offset(
                next.x - norm.x * cornerRadius,
                next.y - norm.y * cornerRadius
            )

            if (i == 0) path.moveTo(start.x, start.y)
            else path.lineTo(start.x, start.y)

            path.quadraticTo(next.x, next.y, end.x, end.y)
        }

        path.close()
        return Outline.Generic(path)
    }
}
