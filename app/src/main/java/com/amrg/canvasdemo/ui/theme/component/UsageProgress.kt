package com.amrg.canvasdemo.ui.theme.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun UsageProgress(
    progressValue: Int = 0,
    maxValue: Int = 100,
    size: Dp = 300.dp
) {
    val pxSize = with(LocalDensity.current) { size.toPx() }

    var safeProgressValue by remember {
        mutableStateOf(0)
    }
    safeProgressValue = if (progressValue <= maxValue) progressValue else maxValue

    val percentage = (safeProgressValue.toFloat() / maxValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000),
        label = ""
    )

    val textValue by animateIntAsState(
        targetValue = safeProgressValue,
        animationSpec = tween(1000),
        label = ""
    )

    Box(
        modifier = Modifier.size(size),
    ) {
        Canvas(
            modifier = Modifier
                .padding(0.dp)
                .align(Alignment.Center)
        ) {
            val drawerSize = pxSize / 1.25f
            background(
                size = Size(drawerSize, drawerSize),
                color = Color.Gray.copy(alpha = .2f),
                strokeWidth = 100f
            )
            foreground(
                sweepAngle = sweepAngle,
                size = Size(drawerSize, drawerSize),
                color = Color.Cyan.copy(alpha = .8f),
                strokeWidth = 100f
            )
        }

        UsageMetaData(
            modifier = Modifier.align(Alignment.Center),
            usageValue = textValue,
            maxUsageValue = maxValue
        )
    }

}

fun DrawScope.background(
    size: Size,
    color: Color,
    strokeWidth: Float
) {
    drawArc(
        size = size,
        color = color,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (this.size.width - size.width) / 2,
            y = (this.size.height - size.height) / 2
        )
    )
}

fun DrawScope.foreground(
    sweepAngle: Float,
    size: Size,
    color: Color,
    strokeWidth: Float
) {
    drawArc(
        size = size,
        color = color,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (this.size.width - size.width) / 2,
            y = (this.size.height - size.height) / 2
        )
    )
}

@Composable
fun UsageMetaData(
    modifier: Modifier = Modifier,
    usageValue: Int,
    maxUsageValue: Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Remaining",
            color = Color.Gray,
            fontSize = TextUnit(14f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "$usageValue GB",
            color = if (usageValue == 0) Color.Gray else Color.Black,
            fontSize = TextUnit(40f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "out of $maxUsageValue GB",
            color = Color.Gray,
            fontSize = TextUnit(14f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun UsageProgressPreview(
) {
    UsageProgress()
}