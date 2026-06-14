package com.thughan.kotlin.firstline.chapter2_7

import com.thughan.kotlin.firstline.chapter2_5.Study

/**
 * @Date   : 2021/12/22
 * @Desc   : 2.7 null point test
 **/
object NullPointTest {

    /**
     * 2.7.1
     * 如果方法像这样写,代表传入的值不能为空,在传入空时编译器会提示报红.
     */
    fun doStudy(study: Study) {
        study.readBooks()
        study.doHomework()
    }

    /**
     * 2.7.2
     * 将类型由Study改为Study?方法可以传入null,这里就判空一下就可以
     */
    fun doStudy1(study: Study?) {
        if (study != null) {
            study.readBooks()
            study.doHomework()
        }
    }

    /**
     * 2.7.2 判空辅助工具
     * 以下判空处理代码
     * if (a != null) {
     *      a.doSomething()
     * }
     * 可以在kotlin中简化成:
     *  a?.doSomething()
     *  那么上面doStudy1方法的代码可以简化成如下
     */
    fun doStudy2(study: Study?) {
        study?.readBooks()
        study?.doHomework()
    }


    /**
     * 2.7.2
     * 下面是?:操作符.
     * val c = if (a != null){
     *      a
     * } else {
     *      b
     * }
     * kotlin中简化后,其实这里我理解?:代表是kotlin中的三元表达式
     * val c = a ?: b
     *
     */
    fun getTextLength(text: String?): Int {
        if (text != null) {
            return text.length
        }
        return 0
    }

    /**
     * 上面代码可以简化成如下代码.
     */
    fun getTextLength1(text: String?) = text?.length ?: 0

    /**
     * 如下代码看着逻辑没什么问题,但是代码是无法运行的,因为printUpperCase函数并不知道外部已经对content变量进行了非空检查,所以无法编译通过.
     * 在content对面后面加上!!是非空断言工具,代表我非常确信这里的对象不会为空.所以不用kotlin来帮我做空指针检查,如果有问题你来出空指针异常,我来承担后果.
     * 这样编译是可以通过的.但是其实我是非常不推荐这样做的.使用了非空断言,有可能是一个潜在的空指针异常.
     */
    var content: String? = "hello"
    fun main() {
        if (content != null) {
            printUpperCase()
        }
    }

    fun printUpperCase() {
//        val upperCase = content.uppercase()
        val upperCase = content!!.uppercase()
        println(upperCase)
    }

    /**
     * let 函数 辅助工具
     * obj.let { obj2 ->
     *      // 编译写具体的业务逻辑
     * }
     *  这个函数提供了函数式API的编程接口,并将原始调用对象作为参数传递到Lambda表达式中.
     *  这里调用了obj对象的let函数,Lambda表达式中的代码就会立即执行,并且这个obj对象本身还会作为参数传递到Lambda中.
     *  为了防止变量重名,这里将参数名改成了obj2.但是实际上它们是同一个对象.这就是let函数的作用.
     */
    fun doStudy3(study: Study?) {
        study?.let { stu ->
            stu.readBooks()
            stu.doHomework()
        }
    }

    /**
     * 上面代表可以进一步简化,如果Lambda表达式的参数只有一个时,可以不用声明参数名,直接使用it关键字来代替.
     */
    fun doStudy4(study: Study?) {
        study?.let {
            it.readBooks()
            it.doHomework()
        }
    }

    /**
     * let函数是可以处理全局变量的判空问题,而if判断语句无法做到这一点,这也是前面提到过,在多线程下无法保证study是否为空.
     */
    var study: Study? = null
    fun doStudy5() {
        // 无法保证
//        if (study != null) {
//            study.readBooks()
//            study.doHomework()
//        }
        // 可以保证study不为空
        study?.let {
            it.readBooks()
            it.doHomework()
        }
    }

}