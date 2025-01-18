package org.seong.refactoring2ndedition

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.seong.refactoring2ndedition.ch1.Statement
import org.seong.refactoring2ndedition.ch1.dto.getInvoices
import org.seong.refactoring2ndedition.ch1.dto.getPlayMap

class ApplicationKtTest : BehaviorSpec({
    given("주어진 Json 입력값으로") {
        val invoice = getInvoices()[0]
        val playMap = getPlayMap()
        `when`("statement() 함수를 호출하면") {
            val result = Statement.statement(invoice, playMap)
            then("청구 내역 문자열을 반환한다.") {
                result shouldBe
"""<h1>청구 내역 (고객명: BigCo)</h1>
<table>
<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>
  <tr><td>Hamlet</td><td>(55석)</td><td>${'$'}650.0</td></tr>
  <tr><td>As You Like It</td><td>(35석)</td><td>${'$'}580.0</td></tr>
  <tr><td>Othello</td><td>(40석)</td><td>${'$'}500.0</td></tr>
</table>
<p>총액: <em>${'$'}1730.0</em></p>
<p>적립 포인트: <em>47</em>점</p>
"""
            }
        }
    }
})
