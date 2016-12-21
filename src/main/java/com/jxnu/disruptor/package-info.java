/**
 * disruptor高性能框架源码阅读的心得及体会
 *
 * 1: RingBuffer 在初始化时 直接根据factory生成event填充数组,减少new的实例
 * 2: Sequence   在控制序号生成时，直接使用java的UNSAVE(CAS机制)控制高并发
 * 3: ThreadInfo 当前线程的状态，threadMXBean.getThreadInfo(treadId)
 * 4: Sequencer 定序器获取next(n)源码解读
 *    //获取当前的序号
 *    long nextValue = this.nextValue;
 *    //n个之后的序号
      long nextSequence = nextValue + n;
      //n个之后这个环最低的序号
      long wrapPoint = nextSequence - bufferSize;
      //缓存消费者最低的序号
      long cachedGatingSequence = this.cachedValue;
      //如果消费者消费最低序号小于环最低的序号  或者 缓存消费者最低序号大于当前序号
      if (wrapPoint > cachedGatingSequence || cachedGatingSequence > nextValue)
      {
         cursor.setVolatile(nextValue);  // StoreLoad fence
         long minSequence;
         //如果消费者消费最低序号小于环最低的序号 一直在死循环 等待消费者消费
         while (wrapPoint > (minSequence = Util.getMinimumSequence(gatingSequences, nextValue)))
         {
          //通知消费者尽快消费队列数据
          waitStrategy.signalAllWhenBlocking();
          LockSupport.parkNanos(1L); // TODO: Use waitStrategy to spin?
         }
         //记录最小值
         this.cachedValue = minSequence;
      }
      //从新设置当前序号
      this.nextValue = nextSequence;
 * 5: EventProcessor 其实相当于一个```消费线程,根据用户的对应的```等待策略从环中获取数据,然后把消费的数据交给```EventHandler处理
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * @author shoumiao_yao
 * @date 2016-12-16
 */
package com.jxnu.disruptor;