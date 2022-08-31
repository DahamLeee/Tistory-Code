package function

fun main() {
    printNumbers(1, 2, 3)
    printNumbers(1, 2, 3, 4)

    val array = intArrayOf(1, 2, 3, 4)
    printNumbers(*array)
}

fun printNumbers(vararg numbers: Int) {
    for (number in numbers) {
        print("${number} ")
    }
    println()
}