fun main() {
    print("Enter the number ")
    var a = readLine()!!.toInt()
    var b = readLine()!!.toInt()
    var c = readLine()!!.toInt()

    if (a>b&&a>c)
        print("A is biggest")
    else
    {
        if (b>a&&b>c)
            print("B is biggest")
        else
            print("C is biggest")
    }
}