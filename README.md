### Subscriber 抽象类与Observer 接口的区别  
相同点：二者基本使用方式完全一致（实质上，在RxJava的 subscribe 过程中，Observer总是会先被转换成Subscriber再使用）  
不同点：Subscriber抽象类对 Observer 接口进行了扩展，新增了两个方法：  
1. onStart()：在还未响应事件前调用，用于做一些初始化工作  
2. unsubscribe()：用于取消订阅。在该方法被调用后，观察者将不再接收 & 响应事件  
调用该方法前，先使用 isUnsubscribed() 判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，如果引用不能及时释放，就会出现内存泄露

### 观察者 Observer的subscribe()具备多个重载的方法
    public final Disposable subscribe() {}
    // 表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）
    public final Disposable subscribe(Consumer<? super T> onNext) {}
    // 表示观察者只对被观察者发送的Next事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {} 
    // 表示观察者只对被观察者发送的Next事件 & Error事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 & Complete事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<?      super Disposable> onSubscribe) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 、Complete事件 & onSubscribe事件作出响应
    public final void subscribe(Observer<? super T> observer) {}
    // 表示观察者对被观察者发送的任何事件都作出响应
    
### 可采用 Disposable.dispose() 切断观察者 与 被观察者 之间的连接

即观察者 无法继续 接收 被观察者的事件，但被观察者还是可以继续发送事件

### 基本操作符

1.create（）作用:完整创建1个被观察者对象（Observable）  
2.just（） 作用:快速创建1个被观察者对象（Observable）    发送事件的特点：直接发送 传入的事件      
3.fromArray（）  作用：快速创建1个被观察者对象（Observable）  发送事件的特点：直接发送 传入的数组数据 
4.fromIterable（）  作用：快速创建1个被观察者对象（Observable） 发送事件的特点：直接发送 传入的集合List数据  
5.defer（）   作用：直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件  
6.timer（）   作用：快速创建1个被观察者对象（Observable）     发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）  
7.interval（）  作用：快速创建1个被观察者对象（Observable）   发送事件的特点：每隔指定时间 就发送 事件（无限递增）  
8.intervalRange（）作用：快速创建1个被观察者对象（Observable）    发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量  
9.range（） 作用：快速创建1个被观察者对象（Observable）   发送事件的特点：连续发送 1个事件序列，可指定范围  
10.rangeLong（）  作用：类似range方法,但是只支持long类型  

### 变换操作符

1.Map（）作用：将被观察者发送的事件转换为任意的类型事件（数据类型转换）  
2.FlatMap（） 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送  
3.ConcatMap（） 作用：与FlatMap类似,就是发送事件顺序有序  
4.Buffer（） 作用：定期从被观察者（Obervable）需要发送的事件中获取一定数量的事件&放到缓存区中，最终发送
                    




