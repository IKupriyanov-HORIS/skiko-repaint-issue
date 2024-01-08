import java.awt.Rectangle
import javax.swing.JPanel

class TextView() : JPanel() {
    private var needUpdate: Boolean = false
    private var textSkikoViewHolder: TextSkikoViewHolder = TextSkikoViewHolder()

    var text: Text = Text("", DoubleVector(0.0, 0.0))
        set(value) {
            field = value
            needUpdate = true
        }

    var size: DoubleVector = DoubleVector(0.0, 0.0)
        set(v) {
            if (field == v) {
                return
            }

            rebuildTextPanel()
            field = v
            needUpdate = true
        }

    // Invoked on every recomposition. needUpdate suppresses unnecessary updates - e.g. when text and size are not changed.
    fun updateTextView() {
        if (!needUpdate) {
            return
        }

        needUpdate = false

        val textViewSize = text.size

        val textViewX = if (textViewSize.x >= size.x) 0.0 else {
            (size.x - textViewSize.x) / 2
        }
        val textViewY = if (textViewSize.y >= size.y) 0.0 else {
            (size.y - textViewSize.y) / 2
        }

        textSkikoViewHolder.bounds = Rectangle(
            textViewX.toInt(),
            textViewY.toInt(),
            textViewSize.x.toInt(),
            textViewSize.y.toInt(),
        )

        textSkikoViewHolder.text = text
    }

    private fun rebuildTextPanel() {
        if (componentCount == 1) {
            removeAll()
        }

        textSkikoViewHolder = TextSkikoViewHolder()
        add(textSkikoViewHolder)
    }
}