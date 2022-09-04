fun main() {
    print("Enter the number : ")
    val a = readLine()!!.toInt()
    if (a>0)
        print("The number is positive")
    else if (a<0)
        print("The number is negative")
    else
        print("The number is zero")
}