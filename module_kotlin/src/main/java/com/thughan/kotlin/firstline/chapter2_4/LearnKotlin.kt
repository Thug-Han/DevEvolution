package com.thughan.kotlin.firstline.chapter2_4

/**
 * @Date   : 2021/11/20
 * @Desc   : 2.4 程序的逻辑控制
 **/
fun main() {
//    val num = 10L
//    checkNumber(num)

    forExample()
}

/**
 * 2.4.1 if语句
 * 在kotlin中if语句比java有一个额外的功能,它是有返回值的.所以注销掉的代码也可以使用下面的代替.
 */
//fun largerNumber(num1: Int, num2: Int): Int {
//    var value = 0
//    if (num1 > num2) {
//        value = num1
//    }else{
//        value = num2
//    }
//    return value
//}

//fun largerNumber(num1: Int, num2: Int): Int {
//    var value = 0
//    value = if (num1 > num2) {
//        num1
//    }else{
//        num2
//    }
//    return value
//}

// 也可以直接返回if代码
//fun largerNumber(num1: Int, num2: Int): Int {
//    return if (num1 > num2) {
//        num1
//    }else{
//        num2
//    }
//}

//fun largerNumber(num1: Int, num2: Int) = if (num1 > num2) {
//    num1
//} else {
//    num2
//}

fun largerNumber(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

/**
 * 2.4.2 when条件语句
 * when语句同样也是有返回值.
 * 格式为: 匹配值 -> {执行逻辑}
 *
 * **/
fun getScore(name: String) = when (name) {
    "Tom" -> 86
    "Jim" -> 77
    "Jack" -> 95
    "Lily" -> 100
    else -> 0
}

/**
 * when还可以进行类型匹配,使用is关键字.Int Long Float Double与数字相关的都是Number的子类.
 */
fun checkNumber(num: Number) {
    when (num) {
        is Int -> print("number is Int")
        is Double -> print("number is Double")
        else -> print("number not support")
    }
}

/**
 * when 可以不带参数,适用于某些需要单独再特殊判断的地方.
 * 在kotlin中 == 关键字可以判断字符串相等.
 */
fun getScore1(name: String) = when {
    name.startsWith("Tom") -> 86
    name == "Jim" -> 77
    name == "Jack" -> 95
    name == "Lily" -> 100
    else -> 0
}

/**
 * 2.4.3 循环语句
 */
fun forExample() {
    // 如下可以表示0-10的区间.使用for循环可以打印出0到10
    // val range = 0..10
    // 代表为0到10的左闭右开区间,数学表达式为[0,10) 使用for循环能打印出0到9
    val range = 0 until 10
    for (i in range) {
        println(i)
    }
    /* 使用step可以跳过其中一些元素.这样可以打印出0 2 4 6 8相当于i =i+2的效果 */
    for (i in range step 2) {
        println(i)
    }
    /* 这相当于创建了一个[10,1]的降序区间*/
    for (i in 10 downTo 1) {
        println(i)
    }
}