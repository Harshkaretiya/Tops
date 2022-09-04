fun main() {
    print("Enter the marks of maths : ")
    var a = readLine()!!.toDouble()
    print("Enter the marks of physics : ")
    var b = readLine()!!.toDouble()
    print("Enter the marks of chemistry : ")
    var c = readLine()!!.toDouble()
    print("Enter the marks of English : ")
    var d = readLine()!!.toDouble()
    print("Enter the marks of computer : ")
    var e = readLine()!!.toDouble()

    print("The percentage is : " + ((a+b+c+d+e)/5))
}