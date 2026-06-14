package com.thughan.kotlin.firstline.chapter2_6

import java.util.*

/**
 * @Date   : 2021/12/22
 * @Desc   : 2.6.2 集合的函数式API
 **/
object FunctionalApiForSets {

    /**
     * 用下面代码找出长主最长的水果单词.
     */
    fun printList() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        var maxLengthFruit = ""
        for (fruit in list) {
            if (fruit.length > maxLengthFruit.length) {
                maxLengthFruit = fruit
            }
        }
        println("max length fruit is $maxLengthFruit")
    }

    /**
     * 下面是在kotlin中使用函数式API,代码明显简洁了很多.
     */
    fun printList1() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val maxLengthFruit = list.maxByOrNull { it.length }
        println(maxLengthFruit)
    }

    /**
     * lambda表达式的语法结构:
     * {参数名1:参数类型,参数名2,参数类型 -> 函数体}
     * 下面展示了如何由复杂的一段话推导出上面简单的一句.
     */
    fun lambdaTest() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val lambda = { fruit: String -> fruit.length }
        val maxLengthFruit = list.maxByOrNull(lambda)
        /* 上面2句可以简化成下面一句.*/
        val maxLengthFruit1 = list.maxByOrNull() { fruit: String -> fruit.length }
        /* kotlin中如果lambda参数是函数的唯一一个参数的话,可以将函数的括号省略*/
        val maxLengthFruit2 = list.maxByOrNull { fruit: String -> fruit.length }
        /* 由于kotlin中出色的类型推导机制,大多数情况下不必声明参数类型.*/
        val maxlengthFruit3 = list.maxByOrNull { fruit -> fruit.length }
        /* 如果当lambda表达式中的参数列表中只有一个参数时,也不必声明参数,而是使用it关键字代替.*/
        val maxlengthFruit4 = list.maxByOrNull { it.length }
    }

    /**
     * .map{} 函数 作用是遍历list,
     * 传入将所有字符串全变成大写的lambda
     */
    fun listMap() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val newList = list.map { it.uppercase(Locale.getDefault()) }
        for (fruit in newList) {
            println(fruit)
        }
    }

    /**
     * .filter{} 函数 过滤list.
     */
    fun listFilterMap() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val newList = list.filter { it.length <= 5 }
            .map { it.uppercase() }
        for (fruit in newList) {
            println(fruit)
        }
    }

    /**
     * .any{} 判断集合中是否至少存在一个元素满足指定条件
     * .all{} 判断集合中是否所有元素都满足指定条件
     */
    fun listAnyAndAll() {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val anyResult = list.any { it.length <= 5 }
        val allResult = list.all { it.length <= 5 }
        println("anyResult is $anyResult, allResult is $allResult")
    }

}