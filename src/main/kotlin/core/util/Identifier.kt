package core.util

fun Collection<Int>.generateUniqueId(range: Int = 100_000): Int {
    var id = (0..range).random()
    while (this.contains(id)) {
        id = (0..range).random()
    }
    return id
}