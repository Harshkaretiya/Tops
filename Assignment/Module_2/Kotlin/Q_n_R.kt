fun main() {
    print("Enter numerator ")
    var a = readLine()!!.toInt()
    print("Enter dinominator ")
    var b = readLine()!!.toInt()

    println("The Quotient is ${a/b}")
    println("The Remainder is ${a%b}")
}