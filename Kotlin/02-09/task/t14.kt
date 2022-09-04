fun main() {
    print("Enter the number a : ")
    var a = readLine()!!.toInt()
    print("Enter the number b : ")
    var b = readLine()!!.toInt()
    print("Enter the number c : ")
    var c = readLine()!!.toInt()
    var max:Int
    max = if (a>b&&a>c) a else if(b>c&&b>a) b else c
    print("The max number is $max")

}