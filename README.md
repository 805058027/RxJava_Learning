# RxJava_Learning
Rxjava2
<--特别注意：2种方法的区别，即Subscriber 抽象类与Observer 接口的区别 -->
// 相同点：二者基本使用方式完全一致（实质上，在RxJava的 subscribe 过程中，Observer总是会先被转换成Subscriber再使用）
// 不同点：Subscriber抽象类对 Observer 接口进行了扩展，新增了两个方法：
// 1. onStart()：在还未响应事件前调用，用于做一些初始化工作
// 2. unsubscribe()：用于取消订阅。在该方法被调用后，观察者将不再接收 & 响应事件
// 调用该方法前，先使用 isUnsubscribed() 判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，如果引用不能及时释放，就会出现内存泄露
