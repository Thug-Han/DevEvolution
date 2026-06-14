package com.thughan.designmode.template;


import com.thughan.designmode.template.concreteclass.ZhangSanFeng;
import com.thughan.designmode.template.concreteclass.ZhangWuji;

/**
 * 模板方法:
 * 使用场景:
 * 面对重要,复杂的算法,可以把核心算法设计为模板方法,周边相关细节功能则由各个子类实现.
 * 需要通过子类来决定子类算法中的某个步骤是否执行,实现子类对父类的反向控制.
 * 优点:
 * 模板方法模式通过把不变的行为搬移到父类,去除了子类中的重复代码.
 * 子类实现算法的某些细节,有助于算法的扩展.
 * 缺点:
 * 每个不同的实现都需要定义一个子类,这会导致类的个数增加,设计更加抽象.
 */
public class TemplateClient {
    public static void invoke() {
        ZhangWuji zhangWuji = new ZhangWuji();
        zhangWuji.fighting();
        ZhangSanFeng zhangSanFeng = new ZhangSanFeng();
        zhangSanFeng.fighting();
    }
}
