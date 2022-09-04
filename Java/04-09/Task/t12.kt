fun main() {
    print("Enter the number : ")
    var a = readLine()!!.toInt()
    var i : Int
//    for (i in 2..(a/2)-1) {
//        if (a % i == 0) {
//            print("The number is not prime number")
//            break
//        }
//    }

        i = 2
        while (i < a / 2) {
            if (a % i == 0) {
                println("not a prime number")
                break
            }
            i++
        }

//    var i : Int
//
    if (a % i != 0) {
        print("The Number is prime number")
    }

}