import java.awt.Dimension
import java.awt.Label
import java.awt.Point
import java.awt.Rectangle
import javax.swing.JPanel

class TextSkikoViewHolder : JPanel() {
    private val skikoView = TextSkikoView()

    var text: Text = Text("", DoubleVector(0.0, 0.0))
        set(value) {
            field = value
            println("TextSkikoViewHolder.text = \"${value.text}\"")

            skikoView.text = value
            skikoView.skiaLayer.bounds = Rectangle(Point(0, 0), skikoView.skiaLayer.preferredSize)

        }
    init {
        this.text = text

        layout = null
        border = null
        background = java.awt.Color.ORANGE
        skikoView.skiaLayer.attachTo(this)
    }

    override fun getPreferredSize(): Dimension {
        return skikoView.skiaLayer.preferredSize
    }
}