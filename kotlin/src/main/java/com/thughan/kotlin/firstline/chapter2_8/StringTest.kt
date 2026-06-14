package com.thughan.kotlin.firstline.chapter2_8

/**
 * @Date   : 2021/12/23
 * @Desc   : 2.8.1 字符串内嵌表达式
 **/
object StringTest {

    fun stringInsertion() {
        val brand = "Samsung"
        val price = 1299.99
        println("Cellphone(brand=" + brand + ",price=" + price + ")")
    }

    /**
     * 在kotlin中允许在字符串中嵌入${}这种语法结构的表达式,并在运行时使用表达式执行的结果替代这一部分内容.如下:
     * "hello, ${obj.name}. nice to meet you!"
     * 当表达式仅有一个变量的时候,可以将两边的大括号省略.如下:
     * "hello, $name. nice to meet you!"
     * 所以上面的代码可以简化成如下:
     */
    fun stringInsertion1() {
        val brand = "Samsung"
        val price = 1299.99
        println("Cellphone(brand=$brand,price=$price)")
    }

}