package function

fun main() {

}

fun sum(leftPort: Int, rightPort: Int): Int {
    return leftPort + rightPort
}

// block 을 생략했으며, 함수의 리턴 값을 알려주기 위해 return 대신 = 를 사용
fun sum1(leftPort: Int, rightPort: Int): Int = leftPort + rightPort
// 함수의 리턴 타입을 생략할 수도 있다.
fun sum2(leftPort: Int, rightPort: Int) = leftPort + rightPort