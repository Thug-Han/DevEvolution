package com.thughan.kotlin.firstline.chapter2_6

/**
 * @Date   : 2021/11/22
 * @Desc   : 2.6.3 java函数式api使用  下面的例子仅限于在kotlin中调用java定义的单接口方法.
 *              kotlin有专门的高阶函数来实现更加强大的自定义函数式API功能.所以不需要像java这样借助单抽象方法接口来实现.
 **/
object FunctionalApiTest {

    /**
     * java中写法:
     * new Thread(new Runnable() {
     *   @Override
     *   public void run() {
     *      System.out.println("Thread is running");
     * }}).start();
     *
     * kotlin中完全舍弃了new关键字,因此创建匿名类实例的时候就不能再使用new了,而是使用object关键字.
     *
     */
    fun threadRun() {
        Thread(object : Runnable {
            override fun run() {
                println("Thread is running")
            }
        }).start()
    }

    fun threadRun1() {
        Thread(Runnable {
            println("Thread is running")
        }).start()
    }

    fun threadRun2() {
        Thread({
            println("Thread is running")
        }).start()
    }

    /**
     * 在kotlin中上面的java代码最终可以简化成这样写.
     */
    fun threadRun3() {
        Thread {
            println("Thread is running")
        }.start()
    }

    /**
     * 当调用java单抽象方法时可以参数下面方式写.
     */
    fun functionApi() {
        this.setOnClick {
            println("点击后方法.")
        }
    }

    private lateinit var mCustomOnClickListener: CustomOnClickListener
    private fun setOnClick(customOnClickListener: CustomOnClickListener) {
        mCustomOnClickListener = customOnClickListener
    }


}