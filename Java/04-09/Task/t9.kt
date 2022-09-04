fun main() {
    print("Enter the number a : ")
    var a = readLine()!!.toInt()
    print("Enter the number b : ")
    var b = readLine()!!.toInt()

    a=a+b
    b=a-b
    a=a-b

    print("Now a : $a & b : $b")
}