## 1장 예제 프로그램 작성 후

> 프로그램이 새로운 기능을 추가하기에 편한 구조가 아니라면, 먼저 기능을 추가하기 쉬운 형태로 리팩터링하고 나서 원하는 기능을 추가한다.

일단 당장 수정하고 싶은 것들이 보입니다.

첫째는 변수의 이름입니다. 특히 `perf`가 가장 맘에 안드네요. 이런 축약어는 당장 봤을 때 `performance`가 연상되지는 않습니다. 그리고 개인적인 부분일 수도 있는데 코드를 작성할 때 마다 `pref`라고 계속 오타를 남발하게 되네요.

둘째는 switch 문입니다. 1.2 소감 부분에서도 나오는 말이지만 새로운 극 종류가 들어올 때마다 switch(저는 코틀린으로 작성했으니까 when) 문은 점점 길어지게 될겁니다. 종류가 아주 한정적이라면 switch 문으로 간결하게 처리할 수 있지만, 끝도 없이 늘어난다면 다른 방법을 사용할 필요가 있습니다.

셋째는 의미있는 이름이 없는 점입니다. 주석으로 아래 로직이 어떤 일을 하는지 알려주고 있지만 썩 맘에 들지는 않습니다. 눈에 잘 들어오지 않습니다. 사실 입사하고 기존에 만들어져 있던 코드의 많은 부분이 이런 느낌이었습니다. 실제로 `if()` 내부에 복잡한 조건들이 들어있었는데 이를 함수로 빼기보다는 주석으로 설명하고 있었습니다. 처음에는 이걸 내가 건들여도 되나 망설였는데 요즘에는 테스트 작성 후 함수로 최대한 빼버리려고 노력하고 있습니다.

### 1.3 리팩터링의 첫 단계

> 리팩터링하기 전에 제대로 된 테스트부터 마련한다. 테스트는 반드시 자가진단하도록 만든다.

저자는 리팩터링의 첫 단계는 테스트 작성이라고 말합니다. 저 또한 여기에 동의합니다. 실무를 접하면서 리팩터링이 필요할 때는 우선 주석의 내용을 이름으로 한 테스트를 먼저 작성합니다. 그리고 이를 통해 리팩터링 전, 후의 기능이 동일하게 잘 작동하는지 검증합니다.

예를 들면 이런 식으로요.
```java
// recipe가 수정해야할 값에 포함되었을 때
if(input.recipe() != null && !Objects.equals(originalRecipe, input.recipe())) {
    ...    
}

// 테스트 작성
@Nested
@DisplayName("recipe가 수정해야할 값에 포함되었다면")
class WhenUpdateInputContainsRecipe {
    @Test
    @DisplayName("~의 recipe 값을 수정한다.")
    void something() {
        // ...
    }
}

```

테스트를 작성하는데 오랜 시간이 걸리긴 합니다. 기존에 만들어진 테스트도 얼마 없고, 의존하고 있는 객체들도 너무 많습니다. 그래도 리팩터링을 위해서 mockito와 직접 테스트 더블을 구현하면서 어떻게든 테스트를 작성합니다.

### 1.4 statement() 함수 쪼개기

> 리팩터링은 프로그램 수정을 작은 단계로 나눠 진행한다. 그래서 중간에 실수하더라도 버그를 쉽게 찾을 수 있다.

저자가 그러하듯 저 또한 최대한 자주 테스트를 실행시키는 것을 선호합니다. 어떻게든 작성해놓은 테스트는 이러한 개선을 더욱 적극적으로 시도할 수 있는 기반이 되어주고 마음의 안식처가 되어줍니다.

> 컴퓨터가 이해하는 코드는 바보도 작성할 수 있다. 사람이 이해하도록 작성하는 프로그래머가 진정한 실력자다.

저자는 변수의 이름을 바꾸는데 망설이지 말라고 합니다. 좋은 코드라면 하는 일이 무엇인지 명확히 코드에 드러나야하며, 변수 이름은 커다란 역할을 한다고 말합니다.

#### play 변수 제거하기 & 적립 포인트 계산 코드 추출하기 & format 변수 제거하기

저자는 `함수로 추출하기`를 통해서 지역변수를 적극적으로 제거합니다. 지역변수가 줄어들어 코드가 더 깔끔해지긴 하지만 함수가 복잡한 일을 수행한다면 고민이 필요한 부분입니다.

책을 따라가면서 조금 버거운 것은 저자가 중첩 함수라는 것을 적극적으로 사용한다는 것입니다. 코틀린이 함수 내부에 함수를 선언할 수 있도록 지원하기에 망정이지 자바로 진행했다면 책의 예제 프로그램을 어떻게 구현해야했을지 까마득합니다.

#### volumeCredits 변수 제거하기

**반복문 쪼깨기 & 문장 슬라이드하기 & 함수로 추출하기 & 변수 인라인하기**의 조합은 꽤 인상적입니다. 반복문은 성능상의 문제가 생길까봐 별도의 메서드로 추출하기 꺼려지는데 저자는 대담하게 함수로 추출해버립니다. 저자는 이 정도 반복문은 쪼개도 얼마 성능차이가 나지 않을 것이라 말합니다. 대부분의 성능 문제는 몇몇 작은 부분에서 결정되며 설령 성능상 문제가 발생하더라도 잘 다듬어진 코드는 성능 개선 작업 또한 수월하다고 덧붙입니다. **"특별한 경우가 아니라면 일단 무시하라."**

생각해보니 저도 얼마전 이런 선택을 한 적이 있었습니다. 원격의 저장소에서 파일을 읽고 저장하는 배치 작업을 수행해야 했습니다. 그 때 하나의 `List`만 사용하면서 반복적인 객체의 생성을 억제하고 싶었습니다. 다음과 같이요.

```java
String line;
List<Data> data = new ArrayList<>();
while((line = br.nextLine()) != null) {
    data.add(readValue(line));
    if(data.size() == BATCH_SIZE) {
        this.repository.saveAll(data);
        data.clear();
    }
}
```

이것만 뚝 떼놓고 보면 간단해보이지만 다른 로직들과 복잡하게 엉켜있을 때는 가독성이 떨어졌습니다. 결국 생각하기를 `List` 몇 개 더 만들어진다고해서 성능상 별로 영향이 없을 거라고 판단했습니다. 가비지 컬렉터가 알아서 처리해줄 거라고 생각하고 다음과 같이 개선했습니다.

```java
while(true) {
    List<Data> data = readUntilBatchSize(br);
    this.repository.saveAll(data);
    if(isReadUntilEndOfLine(data)) {
        break;
    }
}

private List<Data> readUntilBatchSize(BufferedReader br) {
    List<Data> result = new ArrayList<>();
    for (int i = 0; i < BATCH_SIZE; i++) {
        if ((line = br.nextLine()) == null) {
            break;
        }
        result.add(readValue(line));
    }
    return result;
}

private boolean isReadUntilEndOfLine(List<Data> data) {
    return data.isEmpty() || data.size() != BATCH_SIZE;
}
```

이전보다 메서드가 늘어나 코드 길이가 길어졌습니다. 그러나 이전보다는 좀 더 읽기 쉬워졌습니다.

#### 1.5 중간 점검: 난무하는 중첩 함수

지금까지 예제 코드를 kotlin으로 작성한 결과입니다.

```kotlin
fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    fun playFor(aPerformance: Performance): Play? {
        return plays[aPerformance.playID]
    }

    fun amountFor(
        aPerformance: Performance
    ): Int {
        var result = 0
        when (playFor(aPerformance)?.type) {
            "tragedy" -> { // 비극
                result = 40000
                if (aPerformance.audience > 30) {
                    result += 1000 * (aPerformance.audience - 30)
                }
            }

            "comedy" -> { // 희극
                result = 30000;
                if (aPerformance.audience > 20) {
                    result += 10000 + 500 * (aPerformance.audience - 20);
                }
                result += 300 * aPerformance.audience
            }

            else -> {
                throw Exception("알 수 없는 장르: ${playFor(aPerformance)?.type}")
            }
        }
        return result
    }

    fun volumeCreditsFor(aPerformance: Performance): Int {
        var result = Math.max(aPerformance.audience - 30, 0)
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if ("comedy" == playFor(aPerformance)?.type)
            result += Math.floor((aPerformance.audience / 5).toDouble()).toInt();
        return result;
    }

    fun usd(aNumber: Int): String {
        return String.format("$${aNumber / 100.0}")
    }

    fun totalAmount(): Int {
        var result = 0
        for (perf in invoice.performances) {
            result += amountFor(perf)
        }
        return result
    }

    fun totalVolumeCredits(): Int {
        var result = 0
        for (perf in invoice.performances) {
            result += volumeCreditsFor(perf)
        }
        return result;
    }


    var result = "청구 내역 (고객명: ${invoice.customer})\n"
    for (perf in invoice.performances) {
        // 청구 내역을 출력한다.
        result += " ${playFor(perf)?.name}: ${usd(amountFor(perf))} (${perf.audience}석)\n"

    }
    result += "총액: ${usd(totalAmount())}\n"
    result += "적립 포인트: ${totalVolumeCredits()}점\n"
    return result
}
```
주력 언어는 Java인데 사용안하길 다행이라고 생각합니다. 중첩 함수를 어떻게 처리해야할지 꽤나 골치아파았을 것 간습니다.

#### 1.6 계산 단계와 포맷팅 단계 분리하기

이 단계에서는 중간 데이터 구조라는 이름의 데이터 전달 객체를 사용합니다. 계산 단계에서 값을 계산하여 중간 데이터 구조에 모두 집어넣고 포맷팅 단계에 인수로 넘겨주기 위한 작업을 수행합니다.

#### 1.7 중간 점검: 두 파일(과 두 단계)로 분리됨

저자는 다음과 같이 말합니다.

"간결함이 지혜의 정수일지 몰라도, 프로그래밍에서만큼은 명료함이 진화할 수 있는 소프트웨어의 정수다."

코드의 길이는 길어졌습니다만, 이제 각 부분이 최초의 코드보다 어떤 일을 하는지 명확해졌습니다. 

> 캠핑자들에게는 "도착했을 때보다 깔끔하게 정돈하고 떠난다"는 규칙이 있다. 프로그래밍도 마찬가지다. 항시 코드베이스를 작업 시작 시간 전보다 건강하게(healthy) 만들어놓고 떠나야 한다.

```kotlin
data class StatementData(
    var customer: String = "",
    var performances: List<Performance> = emptyList(),
    var totalAmount: Int = 0,
    var totalVolumeCredits: Int = 0
) {
    companion object {
        fun createStatementData(plays: Map<String, Play>, invoice: Invoice): StatementData {
            fun playFor(aPerformance: Performance): Play? {
                return plays[aPerformance.playID]
            }

            fun amountFor(
                aPerformance: Performance
            ): Int {
                var result = 0
                when (aPerformance.play?.type) {
                    "tragedy" -> { // 비극
                        result = 40000
                        if (aPerformance.audience > 30) {
                            result += 1000 * (aPerformance.audience - 30)
                        }
                    }

                    "comedy" -> { // 희극
                        result = 30000;
                        if (aPerformance.audience > 20) {
                            result += 10000 + 500 * (aPerformance.audience - 20);
                        }
                        result += 300 * aPerformance.audience
                    }

                    else -> {
                        throw Exception("알 수 없는 장르: ${aPerformance.play?.type}")
                    }
                }
                return result
            }

            fun volumeCreditsFor(aPerformance: Performance): Int {
                var result = Math.max(aPerformance.audience - 30, 0)
                // 희극 관객 5명마다 추가 포인트를 제공한다.
                if ("comedy" == aPerformance.play?.type)
                    result += Math.floor((aPerformance.audience / 5).toDouble()).toInt();
                return result;
            }

            fun totalVolumeCredits(data: StatementData): Int =
                data.performances.map { it.volumeCredits }
                    .reduce { total, volumeCredits -> total + volumeCredits }

            fun totalAmount(data: StatementData): Int =
                data.performances.map { it.amount }
                    .reduce { total, amount -> total + amount }

            fun enrichPerformance(performance: Performance): Performance {
                performance.play = playFor(performance)
                performance.amount = amountFor(performance)
                performance.volumeCredits = volumeCreditsFor(performance)
                return performance
            }

            val statementData =
                StatementData(invoice.customer, invoice.performances.map { enrichPerformance(it) })
            statementData.customer = invoice.customer
            statementData.performances = invoice.performances.map {
                enrichPerformance(it)
            }
            statementData.totalAmount = totalAmount(statementData)
            statementData.totalVolumeCredits = totalVolumeCredits(statementData)
            return statementData
        }
    }
}


class Application

fun main() {
    val invoices = getInvoices()
    val playMap = getPlayMap()

    for (invoice in invoices) {
        val result = statement(invoice, playMap)
        println(result)
    }
}

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderHtml(StatementData.createStatementData(plays, invoice))
}

private fun renderHtml(
    data: StatementData
): String {

    fun usd(aNumber: Int): String {
        return String.format("$${aNumber / 100.0}")
    }

    val result = StringBuilder()
    result.append("<h1>청구 내역 (고객명: ${data.customer})</h1>\n")
    result.append("<table>\n")
    result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n")
    for (perf in data.performances) {
        result.append("  <tr><td>${perf.play?.name}</td><td>(${perf.audience}석)</td>")
        result.append("<td>${usd(perf.amount)}</td></tr>\n")
    }
    result.append("</table>\n")
    result.append("<p>총액: <em>${usd(data.totalAmount)}</em></p>\n")
    result.append("<p>적립 포인트: <em>${data.totalVolumeCredits}</em>점</p>\n")
    return result.toString()
}
```

### 1.8 다형성을 활용해 계산 코드 재구성하기

#### 공연료 계산기를 다형성 버전으로 만들기

자바스크립트가 그러하듯 코틀린 역시 생성자를 호출했을 때 서브 클래스를 반환하도록 만들수 없습니다. 따라서 정적 팩토리 메서드를 추가하여 서브 조건에 따라 서브 클래스를 반환하는 로직을 추가했습니다.

```kotlin
open class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {
    companion object {
        fun create(
            performance: Performance,
            play: Play
        ): PerformanceCalculator {
            return when (play.type) {
                "tragedy" -> TragedyCalculator(performance, play)
                "comedy" -> ComedyCalculator(performance, play)
                else -> throw Exception("알 수 없는 장르: ${play.type}")
            }
        }
    }
    
    // ...
}
```

또한, 자바스크립트는 추상 메서드라는 개념이 없어보이지만 코틀린과 자바는 추상 메서드라는 개념을 활용할 수 있습니다. 그러니 super 클래스인 `PerformanceCalculator`에 `amount()`의 구현부를 유지하기 보다 `PerformanceCalculator`를 추상 클래스로 변경, `amount()`를 추상 메서드로 변경하여 선언부만 남겨두었습니다.

```kotlin
abstract class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {
    // ...
    abstract fun amount(): Int
    // ...
}
```

### 1.9 상태 점검: 다형성을 활용하여 데이터 생성하기

기존에 switch, if 문을 이용해 연극 종류별 로직을 돌릴 때보다 훨씬 깔끔해졌습니다. 다형성을 활용해 각 객체들의 자신의 역할에 맞는 계산을 수행하게 되었습니다.

### 1.10 마치며

저자가 리팩터링하는 방식은 실무를 접하면서 조금씩 무뎌져가고 있던 기억을 되살려줬습니다. 우리는 테스트를 통해 변경을 두려워하지 않게 됩니다. 변경 후에는 기능이 잘 작동하는지 빠르게 검증하고, 잘 작동하지 않으면 이전으로 되돌리면 그만입니다. 

저자는 아주 작은 커밋단위로 나누어 코드를 개선하고, 검증하고, 커밋하는 반복을 보여주었습니다. 이러한 반복을 통해 주석으로 정보를 얻어야만 했던 코드는 이름을 가진 작은 함수들로 쪼개지고, 다형성을 통해 기존의 코드를 수정하지 않고도 새로운 기능을 추가할 수 있게 변했습니다.

다음은 1장을 마무리짓은 소주제에서 꼭꼭 씹어먹으면 좋을 것 같은 문장 일부를 발췌한 것입니다.

"리팩터링은 대부분 코드가 하는 일을 파악하는 데서 시작한다. 그래서 코드를 읽고, 개선점을 찾고, 리팩터링 작업을 통해 개선점을 코드에 반영하는 식으로 진행한다. 그 결과 코드가 명확해지고 이해하기 더 쉬워진다."

> 좋은 코드를 가늠하는 확실한 방법은 '얼마나 수정하기 쉬운가'다.

"건강한 코드베이스는 생산성을 극대화하고, 고객에게 필요한 기능을 더 빠르고 저렴한 비용으로 제공하도록 해준다. 코드를 건강하게 관리하려면 프로그래밍 팀의 현재와 이상의 차이에 항상 신경 쓰면서, 이상에 가까워지도록 리팩터링해야한다."
