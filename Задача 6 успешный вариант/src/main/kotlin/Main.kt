package org.example

fun main() {
    val codes = intArrayOf(101, 205, 310, 450)
    val name = arrayOf("Мышь", "Клавиатура", "Наушники", "Монитор")
    val price = intArrayOf(1000, 2500, 4000, 15000)
    val remain = intArrayOf(5, 3, 4, 2)
    val applic = intArrayOf(205, 310, 999, 101, 450, 205, 101, 450)
    val quantity = intArrayOf(2, 3, 1, 0, 1, 2, 5, 2)

    var sold = intArrayOf(0, 0, 0, 0)
    var totalrevenue = 0
    var payment = 0


    var totalquantity = 0

    for (i in 0..7 ) {

       val code = applic[i]
        val quantity = quantity[i]

        var found = false
        var index = -1
        var j = 0

        for (j in 0..3) {
            if(codes[j] == code){
                found = true
                index = j
                break

            }
        }
        if (found == false){
            println("Заявка №${i + 1}: отказ — неизвестный код")
        }else if (quantity <= 0){
            println("Заявка №${i + 1}: отказ — количество не больше нуля")
        }else if (quantity > remain[index]){
            println("Заявка №${i + 1}: отказ — недостаточный остаток")
        }else{
            //Заявка успешна
            var sum = price[index]*quantity
            if (sum < 10000){
                val discount = sum * 10 / 100
                payment = sum - discount
            }else{
                payment = sum
            }
            //Обновление данных
            remain[index] -= quantity
            sold[index] += quantity


            println("Заявка №${i + 1}: успешно")
            println("Товар: ${name[index]}")
            println("Количество: $quantity")
            println("Оплата: $payment")



        }


    }

    //Выводим остатки для продаж
    println()
    println("===== ИТОГОВЫЙ ОТЧЁТ =====")

    for (i in 0 until 4) {
        println("${name[i]} | Остаток: ${remain[i]} | Продано: ${sold[i]}")
    }
    // Лидер
    totalrevenue += payment

    if (totalrevenue == 0){
        println("Продаж нет")
    }else{
        var liader = 0
        for (i in 1..3){
            if (sold[i] > sold[liader]){
                liader = i
                println("Лидер: ${name[liader]}")
            }
        }

    }

}

