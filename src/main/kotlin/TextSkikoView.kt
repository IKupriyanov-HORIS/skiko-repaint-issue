import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skia.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoView
import java.awt.Dimension

data class Text(
    val text: String,
    val size: DoubleVector
)

class TextSkikoView : SkikoView {
    val skiaLayer: SkiaLayer = createSkiaLayer(this)

    private fun createSkiaLayer(view: TextSkikoView): SkiaLayer {
        return SkiaLayer().also {
            it.addView(view)
        }
    }

    private fun updateSkiaLayerSize(width: Int, height: Int) {
        skiaLayer.preferredSize = Dimension(width, height)
    }

    var text: Text = Text("", DoubleVector(0.0, 0.0))
        set(value) {
            field = value
            width = value.size.x.toInt()
            height = value.size.y.toInt()

            updateSkiaLayerSize(width, height)
            skiaLayer.needRedraw()
        }

    private var width: Int = 0
    private var height: Int = 0
    private val font = Font(null, 18f)

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val rect = Rect.makeXYWH(0f, 0f, width.toFloat(), height.toFloat())
        val apply = Paint().apply {
            color = Color.LightGray.toArgb()
        }

        canvas.drawRect(rect, apply)
        canvas.drawTextLine(TextLine.make(text.text, font), width / 2f, height / 2f, Paint())
    }
}