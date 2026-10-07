package org.example

fun main() {
        //массивы
    val codes = intArrayOf(101, 205, 310, 450)
    val names = arrayOf("Мышь", "Клавиатура", "Наушники", "Монитор")
    val price = intArrayOf(1000, 2500, 4000, 15000)
    val stocks = intArrayOf(5, 3, 4, 2)
    val requestsCode = intArrayOf(205, 310, 999, 101, 450, 205, 101, 450)
    val requestQuantities = intArrayOf(2, 3, 1, 0, 1, 2, 5, 2)

    //переменные в которых хранятся данные 
    var revenue = 0
    var rejected = 0
    var accepted = 0


    // этта переменая хранит количесьво продаж'
    val soldCount = intArrayOf(0, 0, 0, 0)

    
    var currentRequestIndex = 0

    //тут проходимся по всем заявкам, достаю код товара, и нужное количество
    while (currentRequestIndex < requestsCode.size) {
        val currentCode = requestsCode[currentRequestIndex]
        val currentQuantity = requestQuantities[currentRequestIndex]
        val orderNumber = currentRequestIndex + 1
        currentRequestIndex++
        
        if (currentQuantity <= 0) {
            println("Заявка номер: $orderNumber отклонена по причине: количество меньше или равно нулю")
            rejected++
            continue
        }

        var found = false
        var productIndex = 0

        //если код продукта из заявки совпадают с кодом товара found = true
        while (productIndex < codes.size && !found) {
            if (codes[productIndex] == currentCode) {
                found = true
            } else {
                productIndex++
            }
        }
        //если found не тру то возвращаем отказ
        if (!found) {
            rejected++
            println("Заявка номер: $orderNumber отклонена по причине: товар с таким кодом не найден")
        } else {
            //если текущее количество нужных товаров больше чем их количество на складе то отказ 
            if (stocks[productIndex] < currentQuantity) {
                rejected++
                println("Заявка номер: $orderNumber отклонена по причине: недостаточно товара на складе")
            } else {
                //иначе заявка успешно выполненк
                accepted++
                var total = price[productIndex] * currentQuantity
                //если общая цена твоаров в текущей заявке больше 10000 то прилагется скидка в 10 процентов от этой цены
                if (total >= 10000) {
                    val discount = total * 10 / 100
                    total = total - discount
                }
                revenue += total
                //тут обновялем данные после выполнение текущей заявки заяввки
                stocks[productIndex] -= currentQuantity
                soldCount[productIndex] += currentQuantity

                //вывод успешной заявке
                println("Номер успешной заявки: $orderNumber")
                println("Товар: ${names[productIndex]}")
                println("Оплата: $total")
            }
        }
    }

    println("\n     ИТОГОВЫЙ ОТЧЕТ     ")
    
    var currentProductIndex = 0

    //выводим отсток каждого товара и сколько их продано
    while (currentProductIndex < stocks.size) {
        println("${names[currentProductIndex]} | Остаток: ${stocks[currentProductIndex]} | Продано: ${soldCount[currentProductIndex]}")
        currentProductIndex++
    }

    println("\nВыручка: $revenue")
    println("Принято заявок: $accepted")
    println("Отклонено заявок: $rejected")

    var maxSold = 0
    var leaderIndex = 0
    var leaderSearchIndex = 0


    //тут ищем лидера продаж
    while (leaderSearchIndex < soldCount.size) {
        if (soldCount[leaderSearchIndex] > maxSold) {
            maxSold = soldCount[leaderSearchIndex]
            leaderIndex = leaderSearchIndex
        }
        leaderSearchIndex++
    }
    //а тут уже выводим
    if (maxSold > 0) {
        println("Лидер продаж: ${names[leaderIndex]} (Продано: $maxSold шт.)")
    } else {
        println("Продаж нет")
    }
}
