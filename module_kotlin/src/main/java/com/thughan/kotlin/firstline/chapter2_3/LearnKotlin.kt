package com.thughan.kotlin.firstline.chapter2_3

import kotlin.math.max

/**
 * @Date   : 2021/11/20
 * @Desc   : 变量和函数
 **/
fun main() {
//    main1()
//    main2()
    getLargerNumber()
}

/**
 * 2.3.1 变量
 * 定义一个val变量并打印
 */
fun main1() {
    val a = 10
    println("a = $a")
}

/**
 * 2.3.1 函数
 * 如果定义为val后面是无法再次修改它的值的,会报错,将它修改为var就可以输出a = 100了
 */
fun main2() {
//    val a:Int = 10
    var a: Int = 10
    a *= 10
    println("a = $a")
}

/**
 * 2.3.2 函数 定义函数的关键字,fun ,所有的函数都要由fun来声明
 * (fun 就是function) 函数翻译自function,方法翻译自method,两者并没有区别.
 * 函数在kotlin中普遍一些,java中普遍叫方法.
 * 如果不需要返回值就直接大括号就可以.
 * 下面两个注销的是largerNumber函数的几种语法糖
 */
fun largerNumber(num1: Int, num2: Int): Int {
    return max(num1, num2)
}

//fun largerNumber(num1: Int, num2: Int): Int = max(num1, num2)

//fun largerNumber(num1: Int, num2: Int) = max(num1, num2)

fun getLargerNumber() {
    val a = 37
    val b = 40
    val value = largerNumber(a, b)
    println("larger number is $value")
}



