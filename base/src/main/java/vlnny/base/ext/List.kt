package vlnny.base.ext

fun <E> List<E>.updateFrom(
    list: List<E>,
    ruleBlock: (E, E) -> Boolean
): List<E> {
    val tmpList = mutableListOf<E>()

    forEach { item1 ->
        val item = list.find { item2 ->
            ruleBlock(item1, item2)
        } ?: item1

        tmpList.add(item)
    }
    return tmpList
}