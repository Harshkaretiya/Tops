fun main() {
    print("Enter the principle amount : ")
    var p = readLine()!!.toDouble()
    print("Enter the rate amount in %: ")
    var r = readLine()!!.toDouble()
    print("Enter the time period in years: ")
    var t = readLine()!!.toDouble()
    println("The simple interest is " + (p*r*t/100))
}
