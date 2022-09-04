fun main() {
    println("Enter the height of triangle")
    var h = readLine()!!.toDouble()
    println("Enter the base of triangle")
    var b = readLine()!!.toDouble()
    var a = 0.5*h*b
    println("The area of triangle is $a")
}