package com.thughan.kotlin.firstline.chapter2_6

/**
 * @Date   : 2021/12/21
 * @Desc   : 2.6.1 集合的创建与遍历
 **/
object ListTest {

    /**
     * 第一种方式
     */
    fun getList() {
        val list = ArrayList<String>()
        list.add("Apple")
        list.add("Banana")
        list.add("Orange")
        list.add("Pear")
        list.add("Grape")
    }

    /**
     * kotlin中提供的listOf方法也可以获得一个list.
     * 但是这个方法生成的是一个不可变集合,该集合只能用来读取,无法进行添加修改或者删除操作.
     * mutableListOf 可以创建一个可变集合.
     */
    fun getList1(): List<String> {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
        return list
    }

    /**
     * 遍历集合
     */
    fun listTraversal() {
        for (fruit in getList1()) {
            println(fruit)
        }
    }

    /**
     * 使用mutableListOf方法创建一个可变集合.
     */
    fun getMutableList() {
        val list = mutableListOf("Apple", "Banana", "Orange", "Pear", "Grape")
        list.add("Watermelon")
        for (fruit in list) {
            println(fruit)
        }
    }

}