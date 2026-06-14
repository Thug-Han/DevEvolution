package com.thughan.kotlin.firstline.chapter2_5

/**
 * @Date   : 2021/11/21
 * @Desc   : 2.5 面向对象编程
 **/
fun main() {
//    personEat()
//    student()
//    student3()
//    student4()
//    doStudy()
//    dataTest()
    singletonTest()
}

/**
 * 2.5.1 面向对象编程最基本的用法.
 */
fun personEat() {
    val p = Person()
    p.name = "Jack"
    p.age = 19
    p.eat()
}

/**
 * 2.5.2 继承与构造函数 主构造函数
 */
fun student() {
    val student = Student1("a123", 5)
}

/**
 * 2.5.2 次构造函数
 */
fun student3() {
    val student1 = Student3()
    val student2 = Student3("Jack", 19)
    val student3 = Student3("a123", 5, "Jack", 19)
}

/**
 * 次构造函数使用super调用你类构造函数
 */
fun student4() {
    val student = Student4("Jack", 19)
}

/**
 * 2.5.3 接口
 */
fun doStudy() {
    val student = StudentInterface("Jack", 18)
    student.readBooks()
    student.doHomework()
}

/**
 *
 * 函数可见性修饰符对照表
 * 修饰符         java                              kotlin
 * public       所有类可见                         所有类可见(默认)
 * private      当前类可见                           当前类可见
 * protected    当前类,子类,同一包路径下的类可见       当前类,子类可见
 * default      同一包路径下的类可见(默认)              无
 * internal     无                                同一模块中的类可见
 *
 * */


/**
 * 2.5.4 数据类
 * 输出如下:
 * CellPhone(brand=Samsung, price=1299.99)
 *   cellPhone1 equals cellphone2 true
 *   可以看到两个是相等的,完全将equals() hasCode() toString()生成了.
 */
fun dataTest() {
    val cellPhone1 = CellPhone("Samsung", 1299.99)
    val cellPhone2 = CellPhone("Samsung", 1299.99)
    println(cellPhone1)
    println("cellPhone1 equals cellphone2 " + (cellPhone1 == cellPhone2))
}

/**
 * 2.5.4 单例类只需要一个object关键字就可以创建一个单例类了,并且直接调用.
 */
fun singletonTest() {
    Singleton.singletonTest()
}