# WeakHashMap介绍详解



## 1.介绍

本文讨论*java.util*包下`WeakHashMap` ,为了理解它数据结构，先用简单缓存来举例，这只是理解*map*如何工作原理，用它来创建缓存不是个好主意，`WeakHashMap` 是用哈希表来实现*Map*接口

，key是`WeakReference` 类型，`WeakHashMap`中元素可以被自动删除当key没有被使用时候，

同时意味没有变量引用key，垃圾回收会回收这种key，元素可以被高效删除，`WeakHashMap`方法不同 与其他*Map*接口实现

## 2.强引用 软引用 和弱引用

理解`WeakHashMap`工作原理，需要查看`WeakReference` ，该类是构成`WeakHashMap` key，

java中有3中类型

### 2.1. 强引用

强引用是最常见类型在编程中：

>  Integer prime = 1;

变量prime是强引用Integer对象并且值为1，任何对象被强引用不会被垃圾器收回

### 2.2.软引用

对象被软引用不会被垃圾回收器收回知道jvm内存不够时候，看下面例子：

```java
Integer prime = 1;  
SoftReference<Integer> soft = new SoftReference<Integer>(prime); 
prime = null;
```

prime是强引用，接下来把prime强引用封装到一个软引用中，随后强引用置为空，prime

对象会被垃圾器回收当jvm内存不够时候

### 2.3 弱引用

对象是弱引用时候，垃圾回收器回收对象下次周期回收，回收器不会等到内存不够时候，下面是例子：

```java
Integer prime = 1; 
WeakReference soft = new WeakReference(prime); 
prime = null;
```

设置prime为空时候，prime对象会被垃圾回收器下个周期回收，这里没有强引用指向prime，弱引用被用于`WeakHashMap` key



##3. `WeakHashMap`可以作为一个高效缓存

创建缓存保存大量图片对象作为值，图片名称是key，想选责合适*map*来解决问题，使用

`HashMap`不是很好选择因为值对象占用很多内存，不会被垃圾回收器自动回收没有用对象，当对象没有被使用时候自动被垃圾回收器删除，当key大对象没用被使用，*map*中值会被删除，幸好，`WeakHashMap`  符合上面情况，下面例子：

```java
WeakHashMap<UniqueImageName, BigImage> map = new WeakHashMap();

BigImage bigImage = new BigImage("image_id");

UniqueImageName imageName = new UniqueImageName("name_of_big_image");    

map.put(imageName, bigImage);
assertTrue(map.containsKey(imageName));

imageName = null;
System.gc();

await().atMost(10, TimeUnit.SECONDS).until(map::isEmpty);
```

创建`WeakHashMap`存储*BigImage*对象，增加*BigImage* 作为值和imageName作为key，imageName存储在`WeakHashMap`  是弱引用类型下一步设置 imageName为空，没有其他

引用bigImage，`WeakHashMap`默认会被垃圾回收器回收没有被引用对象在下个回收器周期，因此元素会被删除，调用 *System.gc()* 触发垃圾回收，垃圾回收之后，`WeakHashMap`

为空

```java
WeakHashMap<UniqueImageName, BigImage> map = new WeakHashMap();
BigImage bigImageFirst = new BigImage("foo");
UniqueImageName imageNameFirst = new UniqueImageName("name_of_big_image");
 
BigImage bigImageSecond = new BigImage("foo_2");
UniqueImageName imageNameSecond = new UniqueImageName("name_of_big_image_2");
 
map.put(imageNameFirst, bigImageFirst);
map.put(imageNameSecond, bigImageSecond);
  
assertTrue(map.containsKey(imageNameFirst));
assertTrue(map.containsKey(imageNameSecond));
 
imageNameFirst = null;
System.gc();
 
await().atMost(10, TimeUnit.SECONDS)
  .until(() -> map.size() == 1);
await().atMost(10, TimeUnit.SECONDS)
  .until(() -> map.containsKey(imageNameSecond));

```

注意*imageNameFirst*设置为空，imageNameSecond保存不变，垃圾回收之后，*map*保存一个元素bigImageSecond

## 4.总结

本介绍java中引用类型更好理解`java.util.WeakHashMap` 工作原理，用`WeakHashMap`创建缓存来测试

