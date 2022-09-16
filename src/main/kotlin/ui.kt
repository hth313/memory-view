import io.nacular.doodle.core.View
import io.nacular.doodle.drawing.*
import io.nacular.doodle.drawing.Color.Companion.Black
import io.nacular.doodle.drawing.Color.Companion.Blue
import io.nacular.doodle.drawing.Color.Companion.Darkgray
import io.nacular.doodle.drawing.Color.Companion.Green
import io.nacular.doodle.drawing.Color.Companion.Lightgray
import io.nacular.doodle.drawing.Color.Companion.Red
import io.nacular.doodle.drawing.Color.Companion.White
import io.nacular.doodle.geometry.Point
import io.nacular.doodle.geometry.Rectangle
import io.nacular.doodle.layout.Insets
import io.nacular.doodle.text.StyledText
import kotlin.math.truncate

class StackView(val stack: StackMemory, textMetrics: TextMetrics) : View() {
    private val caption = StyledText(stack.name)
    private val captionSize = textMetrics.size(caption)
    private val captionHeight = captionSize.height
    private val marginBottom = 2 + captionHeight
    private val spLabel = StyledText("sp")
    private val spLabelSize = textMetrics.size(spLabel)
    private val topAddress = StyledText(stack.range.end.prettyHex)
    private val botAddress = StyledText(stack.range.start.prettyHex)
    private val topAddressSize = textMetrics.size(topAddress)
    private val botAddressSize = textMetrics.size(botAddress)
    private val marginRight  = maxOf(40.0, topAddressSize.width.toDouble(), botAddressSize.width.toDouble())

    override fun render(canvas: Canvas) {
        val height = bounds.bottom - marginBottom
        val width = bounds.width - marginRight

        // draw the memory area rectangle
        val outline = Rectangle(0.0, 0.0, width, height)
        val inline = outline.inset(2.0)
	    canvas.rect(outline, Black.paint)
        canvas.rect(inline, White.paint)

        val remains = (stack.unused.size().toDouble() / stack.size().toDouble())
        val remainsY = height * remains

        // visualize the unused area green, color it red if we are getting low
        if (remainsY > 2) {
            val remainsPaint = if (remains > 0.08) Green.paint else Red.paint
            canvas.rect(Rectangle(2.0, height - remainsY, width - 4, remainsY - 2), remainsPaint)
        }

        val sp = stack.pointer
        if (sp != null) {
            val y = height * (stack.top() - sp.value).toDouble() / stack.size().toDouble()

            // Used at some point but now is light gray unless we are out of stack, and we
            // consider one left is empty
            val usedPaint = if (stack.unused.size() > 1u) Lightgray.paint else Red.paint
            canvas.rect(Rectangle(2.0, y, width - 4,height - y - remainsY - 1), usedPaint)

            // stack pointer line with "sp"
            canvas.line(Point(2, y), Point(bounds.width - 2, y), Stroke(Darkgray.paint))
            canvas.text(spLabel, Point(bounds.width - spLabelSize.width, y - spLabelSize.height - 1))
        }

        // mark address ranges
        canvas.text(topAddress, Point(bounds.width - topAddressSize.width, 0))
        canvas.text(botAddress, Point(bounds.width - botAddressSize.width, height - botAddressSize.height))

        // caption the picture with the name of the stack
        val captionOffset = (bounds.width - (marginRight + captionSize.width)) / 2
        canvas.text(caption, Point(captionOffset , bounds.bottom - captionHeight))
    }
}
