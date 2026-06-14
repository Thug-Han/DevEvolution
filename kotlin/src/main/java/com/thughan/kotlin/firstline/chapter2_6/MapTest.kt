package com.thughan.kotlin.firstline.chapter2_6

/**
 * @Date   : 2021/12/22
 * @Desc   : 2.6.1 map test
 **/
object MapTest {
    /**
     *下面这种写法更类似java,kotlin中更推荐下标写法.会提示 map.put() should be converted to assignment
     * **/
    fun getMap1() {
        val map = HashMap<String, Int>()
        map.put("Apple", 1)
        map.put("Banana", 2)
        map.put("Orange", 3)
        map.put("Pear", 4)
        map.put("Grape", 5)
    }

    /**
     * kotlin更推荐这种写法.
     */
    fun getMap2() {
        val map = HashMap<String, Int>()
        map["Apple"] = 1
        map["Banana"] = 2
        map["Orange"] = 3
        map["Pear"] = 4
        map["Grape"] = 5
    }

    /**
     * kotlin中更简便的写法
     */
    fun getMap3(): Map<String, Int> {
        val map = mapOf("Apple" to 1, "Banana" to 2, "Orange" to 3, "Pear" to 4, "Grape" to 5)
        return map;
    }

    fun forMap() {
        val map = getMap3()
        for ((fruit, number) in map) {
            println("fruit is +$fruit, number is $number")
        }
    }

}