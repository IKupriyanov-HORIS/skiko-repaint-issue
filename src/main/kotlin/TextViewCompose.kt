/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Suppress("FunctionName")
@Composable
fun TextView(repaintHack: Boolean, fixedRatio: Boolean) {
    val size = remember { MutableStateFlow(DoubleVector(0.0, 0.0)) }
    var actualSize by remember { mutableStateOf(DoubleVector(0.0, 0.0)) }

    // Update density on each recomposition to handle monitor DPI changes (e.g. drag between HIDPI/regular monitor)
    val density = LocalDensity.current.density.toDouble()

    LaunchedEffect(size) {
        size
            .debounce(500)
            .mapLatest {
                println("size.debounce - $it")
                actualSize = DoubleVector(it.x, it.y)
            }
            .collect()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                size.value = DoubleVector(it.width / density, it.height / density)
            }
    ) {

        if (repaintHack) {
            Column(
                modifier = Modifier
                    .size(0.dp, 0.dp) // Make Compose Canvas not visible
            ) {
                Canvas(
                    modifier = Modifier
                        .size(width = actualSize.x.dp, height = actualSize.y.dp) // if zero - hask doesn't work, no repaint. May work with a half size.
                ) {
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            SwingPanel(
                background = Color.White,
                factory = {
                    TextView()
                },
                update = { textViewContainer ->
                    val s = if (fixedRatio) {
                        val d = minOf(actualSize.x, actualSize.y)
                        DoubleVector(d, d)
                    } else {
                        actualSize
                    }

                    textViewContainer.text = Text("Skiko canvas, size=(${actualSize.x}, ${actualSize.y})", s)
                    textViewContainer.size = actualSize
                    textViewContainer.updateTextView()
                }
            )
        }
    }
}
