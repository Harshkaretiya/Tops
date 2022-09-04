fun main() {
    print("Enter the year number : ")
    var a = readLine()!!.toInt();
    if (a%4==0)
        print("The entered year is leap year")
    else
        print("The year is not a leap year")
}