# Система учета продаж и остатков на складе

Программа на Kotlin для обработки заказов, автоматического расчета скидок, списания товаров со склада и формирования итогового отчета по продажам.

---

## Что делает эта программа?

1. **Хранит каталог товаров**: коды, названия, цены и текущее количество на складе.
2. **Обрабатывает поступающие заявки**: проверяет, правильное ли количество указано, существует ли такой товар и хватит ли его на складе.
3. **Считает скидки**: если сумма заказа в заявке выходит на 10 000 рублей или больше, автоматически применяется скидка 10%.
4. **Обновляет данные**: уменьшает остаток товара на складе и увеличивает счетчик проданных штук.
5. **Выводит отчеты**: покажет успешные и отклоненные заявки, итоговые остатки на складе, общую выручку и определит лидера продаж.

---

## Переменные и структуры данных

### Данные о товарах и заявках (Массивы)
val codes = intArrayOf(101, 205, 310, 450)
val names = arrayOf("Мышь", "Клавиатура", "Наушники", "Монитор")
val price = intArrayOf(1000, 2500, 4000, 15000)
val stocks = intArrayOf(5, 3, 4, 2)
val requestsCode = intArrayOf(205, 310, 999, 101, 450, 205, 101, 450)
val requestQuantities = intArrayOf(2, 3, 1, 0, 1, 2, 5, 2)
val soldCount = intArrayOf(0, 0, 0, 0)

* `codes` — коды товаров в каталоге.
* `names` — названия товаров.
* `price` — стоимость одной штуки.
* `stocks` — текущий остаток товара на складе.
* `requestsCode` — список кодов товаров из поступающих заявок.
* `requestQuantities` — запрашиваемое количество штук для каждой заявки.
* `soldCount` — массив для подсчета количества проданных единиц каждого товара.

### Переменные для учета и статистики
var revenue = 0
var rejected = 0
var accepted = 0

* `revenue` — общая выручка со всех успешных продаж.
* `rejected` — количество отклоненных заявок.
* `accepted` — количество успешно выполненных заявок.

### Индексы для циклов
var currentRequestIndex = 0
var productIndex = 0
var currentProductIndex = 0
var leaderSearchIndex = 0

* `currentRequestIndex` — счетчик для прохода по заявкам.
* `productIndex` — индекс товара при поиске по коду.
* `currentProductIndex` — счетчик для вывода итогового остатка на складе.
* `leaderSearchIndex` — счетчик для поиска лидера продаж.

---

## Пошаговый алгоритм работы программы с примерами кода

### Step 1: Проход по заявкам и извлечение данных
Берем каждую заявку по очереди, достаем код товара, запрашиваемое количество и увеличиваем индекс:

while (currentRequestIndex < requestsCode.size) {
    val currentCode = requestsCode[currentRequestIndex]
    val currentQuantity = requestQuantities[currentRequestIndex]
    val orderNumber = currentRequestIndex + 1
    currentRequestIndex++

### Step 2: Проверка на корректность количества
Если запрашиваемое количество меньше или равно нулю — отказываем и переходим к следующей заявке:

if (currentQuantity <= 0) {
    println("Заявка номер: $orderNumber отклонена по причине: количество меньше или равно нулю")
    rejected++
    continue
}

### Step 3: Поиск товара по коду
Ищем совпадение кода из заявки с каталогом товаров:

var found = false
var productIndex = 0

while (productIndex < codes.size && !found) {
    if (codes[productIndex] == currentCode) {
        found = true
    } else {
        productIndex++
    }
}

### Step 4: Проверка наличия и оформление продажи
Если товар найден и на складе достаточно остатка — рассчитываем стоимость с учетом скидки и обновляем остатки:

if (!found) {
    rejected++
    println("Заявка номер: $orderNumber отклонена по причине: товар с таким кодом не найден")
} else {
    if (stocks[productIndex] < currentQuantity) {
        rejected++
        println("Заявка номер: $orderNumber отклонена по причине: недостаточно товара на складе")
    } else {
        accepted++
        var total = price[productIndex] * currentQuantity
        
        // Расчет скидки 10%
        if (total >= 10000) {
            val discount = total * 10 / 100
            total = total - discount
        }
        revenue += total

        // Обновление остатков на складе и проданных штук
        stocks[productIndex] -= currentQuantity
        soldCount[productIndex] += currentQuantity

        println("Номер успешной заявки: $orderNumber")
        println("Товар: ${names[productIndex]}")
        println("Оплата: $total")
    }
}

### Step 5: Итоговый отчет по складам
Проходим по массиву товаров и выводим остаток и количество продаж для каждого из них:

println("\n=== ИТОГОВЫЙ ОТЧЕТ ===")

var currentProductIndex = 0
while (currentProductIndex < stocks.size) {
    println("${names[currentProductIndex]} | Остаток: ${stocks[currentProductIndex]} \vert{} Продано: ${soldCount[currentProductIndex]}")
    currentProductIndex++
}

println("\nВыручка: $revenue")
println("Принято заявок: $accepted")
println("Отклонено заявок: $rejected")

### Step 6: Поиск лидера продаж
Находим индекс товара с максимальным значением в массиве soldCount:

var maxSold = 0
var leaderIndex = 0
var leaderSearchIndex = 0

while (leaderSearchIndex < soldCount.size) {
    if (soldCount[leaderSearchIndex] > maxSold) {
        maxSold = soldCount[leaderSearchIndex]
        leaderIndex = leaderSearchIndex
    }
    leaderSearchIndex++
}

if (maxSold > 0) {
    println("Лидер продаж: ${names[leaderIndex]} (Продано: $maxSold шт.)")
} else {
    println("Продаж нет")
}

---

## Как запустить проект?

1. Открой проект в IntelliJ IDEA или VS Code.
2. Убедись, что файл Main.kt находится в папке src/main/kotlin/org/example/.
3. Запусти файл Main.kt через кнопку Run в IDE.
