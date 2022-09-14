package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class JunitCalculatorTest {

    @DisplayName("덧셈 테스트")
    @Test
    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then (단언문)
        assertThat(calculator.number).isEqualTo(8)
    }

    @DisplayName("뺄셈 테스트")
    @Test
    fun minusTest() {
        val calculator = Calculator(5)

        calculator.minus(3)

        assertThat(calculator.number).isEqualTo(2)
    }

    @DisplayName("곱셈 테스트")
    @Test
    fun multiplyTest() {
        val calculator = Calculator(5)

        calculator.multiply(3)

        assertThat(calculator.number).isEqualTo(15)
    }

    @DisplayName("나눗셈 테스트")
    @Test
    fun divideTest() {
        val calculator = Calculator(5)

        calculator.divide(2)

        assertThat(calculator.number).isEqualTo(2)
    }

    @DisplayName("0으로 나누었을 경우 예외 테스트")
    @Test
    fun divideZeroExceptionTest() {
        val calculator = Calculator(5)

        assertThatIllegalArgumentException()
            .isThrownBy { calculator.divide(0) }
            .withMessage("0으로 나눌 수 없습니다.")
    }

}