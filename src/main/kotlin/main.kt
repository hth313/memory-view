import io.nacular.doodle.application.Application
import io.nacular.doodle.application.application
import io.nacular.doodle.core.Display
import io.nacular.doodle.core.plusAssign
import io.nacular.doodle.drawing.TextMetrics
import io.nacular.doodle.drawing.text
import io.nacular.doodle.geometry.Size
import org.kodein.di.instance

class HelloWorld(display: Display, textMetrics: TextMetrics): Application {
    init {
        val stack = StackMemory(AddressRange(Address(0x100u), Address(0x1ffu)))
        stack.pointer = Address(0x1b0u)
        stack.unused = AddressRange(Address(0x100u), Address(0x101u))
        val view = StackView(stack, textMetrics)
        val height = 2 * display.size.height / 3
        val width = height / 4
        view.size = Size(width,height)
        display += view
    }

    override fun shutdown() {}
}

fun main() {
    application {
        HelloWorld(display = instance(), textMetrics = instance())
    }
}

//fun main() {
//    println("Hello World\n")
//}
