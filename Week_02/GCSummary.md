# GC小结
### 对象存活判断
1. 引用计数法：缺点是无法解决循环依赖的问题。
2. 引用跟踪法

### 垃圾收集算法
1. 标记-清除算法
- 效率不高且碎片过多
2. 复制算法
- 适用于新生代对象的垃圾回收
3. 标记压缩算法
- 适用于老年代对象的垃圾回收
4. 分代收集算法
- 把Java堆分为新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法

### 垃圾收集器
#### 分类和特点
1. Serial收集器
- 包含的收集器：Serial（新生代）、SerialOld（老年代）
- 适用场景：单CPU或者内存不大的设备
2. Parallel收集器
- 包含的收集器：ParNew（新生代）、Parallel Scavenge（新生代）、ParallelOld（老年代）
- 适用场景：多CPU，关注系统吞吐量，STW时间相对Serial少
3. CMS(ConcurrentMarkSweep)收集器
- 包含的收集器：CMS（老年代）
- 适用场景：多CPU，最大程度保障减少STW时间，同时保证垃圾有效回收
4. G1收集器
- 包含的收集器：G1（新生代和老年代）
- 适用场景：多CPU，内存大于4G的

#### 用法
- -XX:+UseSerialGC 参数指定新生代和老年代使用串行收集器
- -XX:+UseParNew 新生代使用并行收集器，老年代使用串行收集器
- -XX:+UseParallelGC和-XX:+UseParallelOldGC 新生代使用并行收集器，老年代使用并行收集器（GC日志中出现的是ParOldGen）
- -XX:+UseConcMarkSweepGC 指定老年代使用CMS收集器，年轻代使用ParNew收集器
- -XX:+UseG1GC 指定使用G1收集器，YoungGC和MixedGC，一般不需要设置新生代的大小

### 压测
- 工具参数：wrk (threads:8 connections:100 duration:10s)
- jar包：gateway-server-0.0.1-SNAPSHOT.jar
#### SerialGC 
128m: 275792 requests in 10.08s, Requests/sec:27367.10, Transfer/sec:3.27MB
1g: 282480 requests in 10.09s, Requests/sec:27985.01, Transfer/sec:3.34MB
4g: 299051 requests in 10.08s, 35.70MB read, Requests/sec:  29660.56

#### ParallelGC 
128m: 275792 requests in 10.08s, Requests/sec:27367.10, Transfer/sec:3.27MB
1g: 282480 requests in 10.09s, Requests/sec:27985.01, Transfer/sec:3.34MB
4g: 317288 requests in 10.10s, 37.88MB read, Requests/sec:  31428.20

#### CMSGC 
128m: 249827 requests in 10.10s, Requests/sec:24743.57, Transfer/sec:2.95MB
1g: 285204 requests in 10.10s, Requests/sec:28248.70, Transfer/sec:3.37MB
4g: 254121 requests in 10.10s, Requests/sec:25166.20, Transfer/sec:3.00MB

### G1GC
128m: 250420 requests in 10.08s, Requests/sec:24850.05, Transfer/sec:2.97MB
1g: 273085 requests in 10.10s, Requests/sec:27044.20, Transfer/sec:3.23MB
4g: 254121 requests in 10.10s, Requests/sec:25166.20, Transfer/sec:3.00MB

### 压测结果
- 压测的结果是取的是稳定值，前三四次基本上稳步增长
- 可能和gateway程序以及笔记本上跑的程序有关，受影响较大
