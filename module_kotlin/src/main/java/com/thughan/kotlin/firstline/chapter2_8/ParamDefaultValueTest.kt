package com.thughan.kotlin.firstline.chapter2_8

import com.thughan.kotlin.firstline.chapter2_5.Person2

/**
 * @Date   : 2021/12/23
 * @Desc   : 2.8.2 函数的参数默认值
 **/
object ParamDefaultValueTest {

    fun invoke() {
//        printParams(123)
//        printParams1("world")
        printParams1(str = "world", num = 123)
        printParams1(str = "world")
    }

    /**
     * 这里给printParams函数的第二个参数设定了一个默认值,这样当调用printParams()函数时可以选择给第二个参数传值,也可以选择不传,不传则使用默认值.
     * printParams(123)如此调用时,打印正常.
     * num is 123,str is hello
     * 如果将第一个设置默认值
     */
    fun printParams(num: Int, str: String = "hello") {
        println("num is $num,str is $str")
    }

    /**
     * 如果像如下这样,给第一个设置参数,调用
     * printParams1("world") 就会报错,第二个参数没有默认值,编译器会认为我们想把字符串赋值给第一个参数.
     * 我们可以像下面这样类似键值对的方式写.可以打乱顺序.kotlin可以准确的匹配.
     * printParams1(str = "world", num = 123)
     * 或者也可以不传入num值,因为num已经有默认值了.
     * printParams1(str = "world")
     *
     */
    fun printParams1(num: Int = 100, str: String) {
        println("num is $num,str is $str")
    }

    /**
     * 这个功能可以很大程序上替代次构造函数.可以只编写一个主构造函数,然后给参数设定默认值的试来实现.
     * 如下Student3可以使用Student4来代替.
     */

    class Student3(val sno: String, val grade: Int, name: String, age: Int) : Person2(name, age) {

        constructor(name: String, age: Int) : this("", 0, name, age) {

        }

        constructor() : this("", 0) {

        }

    }

    class Student4(val sno: String = "", val grade: Int = 0, name: String = "", age: Int = 0) : Person2(name, age) {

    }

}