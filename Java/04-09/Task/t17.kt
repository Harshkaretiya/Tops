fun main() {
    println("Enter two number")
    val a = readLine()!!.toDouble()
    val b = readLine()!!.toDouble()
    println("Enter number according to operations : ")
    println("1 - addition \n2 - subtraction \n3 - multiplication \n4 - division")
    val num = readLine()!!.toInt()
    when (num) {
        1 -> {
            println("Addition is " + (a+b))
        }

        2 -> {
            println("Subtraction is " + (a - b))
        }

        3 -> {
            println("Multiplication is " + a * b)
        }

        4 -> {
            println("Disivion is " + a/b)
        }

        else -> {}
    }
}