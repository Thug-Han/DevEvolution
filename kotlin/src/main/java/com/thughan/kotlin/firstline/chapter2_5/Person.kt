package com.thughan.kotlin.firstline.chapter2_5

/**
 * @Date   : 2021/11/21
 * @Desc   : 2.5 面向对象编程
 **/
open class Person {
    var name = ""
    var age = 0
    fun eat() {
        println("$name is eating. He is $age years old.")
    }
}


open class Person2(val name: String, val age: Int) {
    fun eat() {
        println("$name is eating. He is $age years old.")
    }
}