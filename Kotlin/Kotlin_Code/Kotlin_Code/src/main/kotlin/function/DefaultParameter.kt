package function

fun main() {
    signup("왕탁이1", "admin@gmail.com")
    signup("왕탁이2")
}

fun signup(name: String, email: String = "default") {
    println("이름: ${name}, 이메일: ${email}")
}

/**
 * result
 * 이름: 왕탁이1, 이메일: admin@gmail.com
 * 이름: 왕탁이2, 이메일: default
 */