fun main() {
    println("Enter the number according to operations")
    println("1 - Area of Triangle\n2 - Area of rectangle\n3 - Area of circle")
    val num = readLine()!!.toInt()
    if (num == 1) {
        println("Enter Height and base length")
        val h = readLine()!!.toInt()
        val b = readLine()!!.toInt()
        println("Area of triangle is " + 0.5 * h * b)
    }
    else if (num == 2) {
        println("Enter length and width")
        val l = readLine()!!.toInt()
        val w = readLine()!!.toInt()
        println("Area of rectangle is " + l * w)
    }
    else if (num == 3) {
        println("Enter radius")
        val r = readLine()!!.toInt()
        println("Area of circle is " + 3.14 * r * r)
    }
    else {
        println("you have entered wrong value")
    }
}