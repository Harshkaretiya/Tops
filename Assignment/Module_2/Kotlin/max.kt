fun main() {
    println("Enter 3 numbers")
    var a  = readLine()!!.toInt()
    var b  = readLine()!!.toInt()
    var c  = readLine()!!.toInt()

    println("Max number of these 3 no. is ${maxOf(a,b,c)}")
}