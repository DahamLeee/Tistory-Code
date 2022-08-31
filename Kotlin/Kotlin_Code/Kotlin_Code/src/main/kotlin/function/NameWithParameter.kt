package function

fun main() {
    signup(age = 26, name = "왕탁이")
    signup(email = "admin@gmail.com")
    signup()
}

fun signup(name: String = "anonymous", email: String = "default", age: Int = 0) {
    println("이름 : ${name}, 이메일: ${email}, 나이: ${age}")
}

/**
 * 이름 : 왕탁이, 이메일: default, 나이: 26
 * 이름 : anonymous, 이메일: admin@gmail.com, 나이: 0
 * 이름 : anonymous, 이메일: default, 나이: 0
 */