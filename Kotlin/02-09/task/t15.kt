fun main() {
    println("Enter marks of maths out of 100")
    val a = readLine()!!.toInt()
    println("Enter marks of english out of 100")
    val b = readLine()!!.toInt()
    println("Enter marks of science out of 100")
    val c = readLine()!!.toInt()
    println("Enter marks of hindi out of 100")
    val d = readLine()!!.toInt()
    println("Enter marks of gujarati out of 100")
    val e = readLine()!!.toInt()
    val sum = a + b + c + d + e
    val p = sum / 5
    println("The total marks is $sum")
    println("The percentage is" + sum / 5 + "%")

    if (p > 75) {
        print("Distinction")
    }
    else if (p > 60 && p <= 75) {
        print("First class")
    }
    else if (p > 50 && p <= 60) {
        print("Second class")
    }
    else if (p > 35 && p <= 50) {
        print("Pass class")
    }
    else {
        print("Fail")
    }
}