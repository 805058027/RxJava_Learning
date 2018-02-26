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

### 组合操作符

1.concat（）  作用：组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
2.concatArray（） 和1的区别：即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个  
3.merge（）  作用：组合多个被观察者一起发送数据，合并后 按时间线并行执行  
4.mergeArray（） 和3的区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个  
5.concatDelayError（） mergeDelayError（） 防止若其中一个观察者出现OnError事件,立即中断所有继续发送事件  
6.Zip（） 作用：合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送  Tip:事件组合方式 = 严格按照原先事件序列 进行对位合并最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量  
8.combineLatest（） 作用：当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据  
9.combineLatestDelayError（） 作用：类似于concatDelayError（） / mergeDelayError（） ，即错误处理  
10.reduce（） 作用：把被观察者需要发送的事件聚合成1个事件 & 发送  本质：聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推  
11.collect（） 作用：将被观察者Observable发送的数据事件收集到一个数据结构里
                    




