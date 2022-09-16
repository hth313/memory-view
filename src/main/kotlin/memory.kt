
data class Address(val value: ULong, val bank: UInt? = null, val prettyHex: String = value.toString(16)) {
    fun step (inc: Long) : Address {
        return Address(value + inc.toULong(), bank)
    }

    fun step (inc: ULong) : Address {
        return Address(value + inc, bank)
    }
}

data class AddressRange(val start: Address, val end: Address) {
    constructor(start: Address, size: ULong) : this(start, start.step(size - 1u))
    fun size() = if (end.value >= start.value) end.value - start.value + 1u else 0u
}

open class Memory (val range: AddressRange, val name: String) {
    fun size() = range.size()
}

class Inhabitor (val name: String)

open class StackMemory (range: AddressRange, name: String = "stack") : Memory(range, name) {
    var pointer: Address? = null
    var unused: AddressRange = range
    var inhabitor : Array<Pair<AddressRange,Inhabitor>>? = null
    fun top() = range.end.value
    fun bottom() = range.start.value
}
