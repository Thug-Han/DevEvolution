package com.thughan.designmode.singleton;

import java.io.ObjectStreamException;

/**
 * @author hanjinlong
 * @date 2020/7/31
 * @description
 */
public class Singleton {

    /**
     * 饿汉模式,这种方式在类加载时就完成了初始化,所以类加载较慢,但获取对象的速度快.这是基于类加载机制,避免了多线程同步问题.
     * 在类加载的时候就完成实例化,没有懒加载效果,如果从未使用这个实例,则会造成线程浪费.
     */
//    private static Singleton instance = new Singleton();
//
//    private Singleton() {
//
//    }
//
//    public static Singleton getInstance() {
//        return instance;
//    }

    /**
     * 懒汉模式(线程不安全)
     * 懒汉模式声明了一个静态对象,在用户第一次调用时初始化.这虽然节约了资源,但是第一次加载时需要实例化,反应稍慢,而且在多线程时不能正常工作.
     */
//    private static Singleton instance;
//    private Singleton(){
//
//    }
//    public static Singleton getInstance(){
//        if (instance==null){
//            instance = new Singleton();
//        }
//        return instance;
//    }


    /**
     * 懒汉模式(线程安全)
     * 这种写法能够在多线程中很好地工作,但是每次调用getInstance方法时都需要同步.这会千万不必要的同步开销,而且大部分时我们是用不到同步的.所以不建议.
     */
//    private static Singleton instance;
//
//    private Singleton() {
//
//    }
//
//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }


    /**
     * 双重检查模式(DCL double-checked-locking)
     * 对instance进行了两次判空;第一次是为了同步,第二次是instance=null的情况下才创建实例.
     * 使用volatile多少会影响性能 ,但是由于为了保证可见性和有序性还是值得的
     * 优点是资源利用率高,第一次执行getInstance时单例对象才被实例化,效率高.
     * 缺点是第一次加载反应慢些,高并发环境下有一定的缺陷.
     * 解决了资源的消耗和多余的同步.线程安全等问题,但还是某些情况下会失效(这句搜索了一下,貌似加了volatile后会解决失效的问题)
     */
//    private volatile static Singleton instance;
//
//    private Singleton() {
//
//    }
//
//    public static Singleton getInstance() {
//        if (instance == null) {
//            synchronized (Singleton.class) {
//                if (instance == null) {
//                    instance = new Singleton();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 静态内部类单例模式
     * 第一次加载Singleton类时并不会初始化sInstance,只有第一次调用getInstance方法时虚拟机加载SingletonHolder并初始化sInstance.
     * 这样不公能确保线程安全,也能保证Singleton类的唯一性.较推荐
     */
    private Singleton() {

    }

    public static Singleton getInstance() {
        return SingletonHolder.SINGLETON;
    }

    private static class SingletonHolder {
        private static final Singleton SINGLETON = new Singleton();
    }

    /**
     * 反序列化后打印地址不同
     * 防止反序列化后重新生成对象,另一种解释是加了是线拷贝,是之前对象的指针;不加是深拷贝,直接开辟新空间.地址就不相同了
     *
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        return SingletonHolder.SINGLETON;
    }

} 