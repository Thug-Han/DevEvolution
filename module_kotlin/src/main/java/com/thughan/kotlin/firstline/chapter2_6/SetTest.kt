package com.thughan.kotlin.firstline.chapter2_6

/**
 * @Date   : 2021/11/22
 * @Desc   : 2.6.1 set test
 **/
object SetTest {

    /**
     * Set集合的用法与List集合的用法几乎一样,只是函数换成了setOf() 和 mutableSetOf().
     */
    fun getAndPrintSet() {
        val set = setOf("Apple", "Banana", "Orange", "Pear", "Grape")
        for (fruit in set) {
            println(fruit)
        }
    }

}