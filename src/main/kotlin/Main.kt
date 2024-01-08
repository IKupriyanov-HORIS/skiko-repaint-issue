import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    val repaintHack = remember { mutableStateOf(false) }
    val fixedRatio = remember { mutableStateOf(false) }

    MaterialTheme {
        Row {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(IntrinsicSize.Max)
            ) {
                Row {
                    Text(
                        text = "Fixed aspect ratio:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Checkbox(fixedRatio.value, onCheckedChange = { fixedRatio.value = it })
                }
                Row {
                    Text(
                        text = "Repaint hack:",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Checkbox(repaintHack.value, onCheckedChange = { repaintHack.value = it })
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
                ) {
                    TextView(repaintHack.value, fixedRatio.value)
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
