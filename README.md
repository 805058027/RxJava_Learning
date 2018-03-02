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
![map操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/map.png)  
1.Map（）作用：将被观察者发送的事件转换为任意的类型事件（数据类型转换）  
![FlatMap操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/flotMap.png)  
2.FlatMap（） 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送  
![ConcatMap操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/ConcatMap.png)  
3.ConcatMap（） 作用：与FlatMap类似,就是发送事件顺序有序  
![Buffer操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/Buffer.png)  
![Buffer操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/Buffer1.png)  
4.Buffer（） 作用：定期从被观察者（Obervable）需要发送的事件中获取一定数量的事件&放到缓存区中，最终发送

### 组合操作符

1.concat（）  作用：组合多个被观察者一起发送数据，合并后 按发送顺序串行执行  
2.concatArray（） 和1的区别：即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个  
3.merge（）  作用：组合多个被观察者一起发送数据，合并后 按时间线并行执行  
4.mergeArray（） 和3的区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个  
5.concatDelayError（） mergeDelayError（） 防止若其中一个观察者出现OnError事件,立即中断所有继续发送事件  
6.Zip（） 作用：合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送  Tip:事件组合方式 = 严格按照原先事件序列 进行对位合并最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量  
7.combineLatest（） 作用：当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据  
8.combineLatestDelayError（） 作用：类似于concatDelayError（） / mergeDelayError（） ，即错误处理  
9.reduce（） 作用：把被观察者需要发送的事件聚合成1个事件 & 发送  本质：聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推  
10.collect（） 作用：将被观察者Observable发送的数据事件收集到一个数据结构里

### 发送事件前追加发送事件

startWith（） / startWithArray（）   在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者

### 统计发送事件数量

count（）  统计被观察者发送事件的数量

### 线程调度

操作符:subscribeOn（）和 observeOn（）  
详细讲解参考：[Android RxJava：细说 线程控制（切换 / 调度 ）（含Retrofit实例讲解）](https://www.jianshu.com/p/5225b2baaecd)

### 延迟操作

delay（）  作用：使得被观察者延迟一段时间再发送事件
<pre><code>
// 1. 指定延迟时间
// 参数1 = 时间；参数2 = 时间单位
delay(long delay,TimeUnit unit)

// 2. 指定延迟时间 & 调度器
// 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
delay(long delay,TimeUnit unit,mScheduler scheduler)

// 3. 指定延迟时间  & 错误延迟
// 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
// 参数1 = 时间；参数2 = 时间单位；参数3 = 错误延迟参数
delay(long delay,TimeUnit unit,boolean delayError)

// 4. 指定延迟时间 & 调度器 & 错误延迟
// 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError): 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟
</code></pre>

 ### 在事件的生命周期中操作
 
 do（） 作用：在某个事件的生命周期中调用
 
![do操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/do.png)

 ### 错误处理
 
 ![错误操作符介绍](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/error.png)  
 1.onErrorReturn（）  作用:遇到错误时，发送1个特殊事件 & 正常终止  可捕获在它之前发生的异常  
 2.onErrorResumeNext（）  作用:遇到错误时，发送1个新的Observable  拦截的错误 = Throwable  
 3.onExceptionResumeNext()  作用:拦截的错误 = Throwable 拦截的错误 = Exception  
 4.retry（） 作用：重试，即当出现错误时，让被观察者（Observable）重新发射数据,接收到 onError（）时，重新订阅 & 发送事件
Throwable 和 Exception都可拦截
<pre><code><-- 1. retry（） -->
// 作用：出现错误时，让被观察者重新发送数据
// 注：若一直错误，则一直重新发送

<-- 2. retry（long time） -->
// 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
// 参数 = 重试次数
 
<-- 3. retry（Predicate predicate） -->
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
// 参数 = 判断逻辑

<--  4. retry（new BiPredicate<Integer, Throwable>） -->
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
// 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）

<-- 5. retry（long time,Predicate predicate） -->
// 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
// 参数 = 设置重试次数 & 判断逻辑
</code></pre>  
5.retryUntil（） 作用：出现错误后，判断是否需要重新发送数据,若需要重新发送 & 持续遇到错误，则持续重试作用类似于retry（Predicate predicate）  
6.retryWhen（） 作用：遇到错误时，将发生的错误传递给一个新的被观察者（Observable），并决定是否需要重新订阅原始被观察者（Observable）& 发送事件

### 重复发送

1.repeat（） 作用：无条件地、重复发送 被观察者事件  具备重载方法，可设置重复创建次数  
2.repeatWhen（） 作用：有条件地、重复发送 被观察者事件  将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable），以此决定是否重新订阅 & 发送原来的  Observable

### 过滤操作符
![条件过滤操作符](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/tiaojian.png)
1.Filter（） 作用：过滤 特定条件的事件  
2.ofType（） 作用：过滤 特定数据类型的数据  
3.skip（） / skipLast（） 作用：跳过某个事件  
4.distinct（） / distinctUntilChanged（） 作用：过滤事件序列中重复的事件 / 连续重复的事件  
5.take（）/ takeLast（）作用：指定观察者最多能接收到的事件数量/指定观察者只能接收到被观察者发送的最后几个事件  
![时间过滤操作符](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/time.png)
![throttleFirst（）/ throttleLast（）过滤操作符](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/timedetail.png)
6.throttleFirst（）/ throttleLast（）  作用：在某段时间内，只发送该段时间内第1次事件 / 最后1次事件  
7.Sample（） 作用：在某段时间内，只发送该段时间内最新（最后）1次事件与 throttleLast（） 操作符类似  
8.throttleWithTimeout （） / debounce（） 作用：发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据  
![位置过滤操作符](https://github.com/805058027/RxJava_Learning/raw/master/screenshots/element.png)
9.firstElement（） / lastElement（） 作用：仅选取第1个元素 / 最后一个元素  
10.elementAt（） 作用：指定接收某个元素（通过 索引值 确定） 注：允许越界，即获取的位置索引 ＞ 发送事件序列长度  
11.elementAtOrError（） 作用：在elementAt（）的基础上，当出现越界情况（即获取的位置索引 ＞ 发送事件序列长度）时，即抛出异常



