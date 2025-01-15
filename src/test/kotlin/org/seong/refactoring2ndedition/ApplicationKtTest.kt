package org.seong.refactoring2ndedition

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.seong.refactoring2ndedition.dto.getInvoices
import org.seong.refactoring2ndedition.dto.getPlayMap

class ApplicationKtTest : BehaviorSpec({
    given("주어진 Json 입력값으로") {
        val invoice = getInvoices()[0]
        val playMap = getPlayMap()
        `when`("statement() 함수를 호출하면") {
            val result = statement(invoice, playMap)
            then("청구 내역 문자열을 반환한다.") {
                result shouldBe "청구 내역 (고객명: BigCo)\n" +
                        " Hamlet: \$650.0 (55석)\n" +
                        " As You Like It: \$580.0 (35석)\n" +
                        " Othello: \$500.0 (40석)\n" +
                        "총액: \$1730.0\n" +
                        "적립 포인트: 47점\n"
            }
        }
    }
})
