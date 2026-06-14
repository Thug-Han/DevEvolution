package com.thughan.kotlin.firstline.chapter2_5

/**
 * @Date   : 2021/11/21
 * @Desc   : 2.5.2
 * 想要继承Person的话需要Person类前面加上open关键字
 **/
class Student() : Person() {
    var sno = ""
    var grade = 0
}

/**
 * 主构造函数如下,是比较常用的构造函数.
 * 每个类默认都有一个不带参数的主构造函数.当然也可以向下面一样显式地给它指明参数.
 * 不需要函数体,直接定义在类名后面括号中.
 * 在init结构体中,所有主构造函数中的逻辑都可以写在里面.
 * java中继承特性中的规定,子类中构造函数必须调用父类中的构造函数,所以kotlin中直接以Person()这种形式代表调用父类的构造函数.
 */
class Student1(val sno: String, val grade: Int) : Person() {
    init {
        println("sno is $sno")
        println("gradle is $grade")
    }
}

/**
 * 如果父类也有主构造函数的话可以如下面方式写.
 */
class Student2(val sno: String, val grade: Int, name: String, age: Int) : Person2(name, age) {
    init {
        println("sno is $sno")
        println("gradle is $grade")
    }
}

/**
 * 使用constructor来字义次构造函数
 */
class Student3(val sno: String, val grade: Int, name: String, age: Int) : Person2(name, age) {

    constructor(name: String, age: Int) : this("", 0, name, age) {

    }

    constructor() : this("", 0) {

    }

    init {
        println("sno is $sno")
        println("gradle is $grade")
        println("name is $name")
        println("age is $age")
    }
}

/**
 * 只包含次构造函数的类,使用super调用你类的构造函数.
 */
class Student4 : Person2 {
    constructor(name: String, age: Int) : super(name, age) {
        println("name is $name")
        println("age is $age")
    }
}

/**
 * java中使用extends implements,在kotlin中统一使用冒号.中间使用逗号进行分隔.
 * 如果Study接口中的方法doHomework已有默认实现,子类是就不是必须实现接口中的方法.
 */
class StudentInterface(name: String, age: Int) : Person2(name, age), Study {
    override fun readBooks() {
        println("$name is reading.")
    }
}