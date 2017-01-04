/**
 * disruptor高性能框架源码阅读的心得及体会
 * 1: RingBuffer
 *    主要属性两个  object[] entries :初始时全部填充用户指定的对象 大小是：bufferSize+2* 128字节  注意: Unsafe使用及获取指定数据指定位置的对象
 *                      sequencer : 通过定序器获取可得到的下一个序号
 *
 *    RingBuffer 在初始化时 直接根据factory生成event填充数组,减少new的实例
 *
 * 2: sequencer定序器接口 :获取下一个可以填充RingBuffer的序号
 *    两个实现类
 *       [1] SingleProducerSequencer 单个生产者
 *       [2] MultiProducerSequencer  多个生产者
 *
 *       单个生产者Sequencer 定序器获取next(n)源码解读  注意 多个生产者通过Sequence序号的cas控制并发获取序号
 *       //获取当前的序号
 *       long nextValue = this.nextValue;
 *       //n个之后的序号
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
           //如果消费者消费最低序号小于环最低的序号 等待消费者消费
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

    3: Sequence   在控制序号生成时，直接使用java的UNSAVE(CAS机制)控制高并发
 *
 *  4: Disruptor  启动类,统一配置入口
 *
 *  5: ConsumerRepository  提供sequence和Consumer,eventHandler和Consumer对应关系
 *
 *  6: SequenceBarrier  跟踪,协调生产者序号和消费者序号
 *
 *  7: WaitStrategy  下一个可得到序号等待策略

 *  8: EventProcessor 其实相当于一个消费线程,根据用户的对应的等待策略从环中获取数据,然后把消费的数据交给EventHandler处理
 *
 *  9: EventHandler 消费event
 *
 * 10：ConsumerInfo 消费者相关信息接口 (EventProcessors,SequenceBarrier)
 *     有两个实现类 WorkerPoolInfo,EventProcessorInfo
 *
 * 11: WorkerPool   EventProcessors和RingBuff建立关系的地方  (RingBuff的定序器 记录有哪些消费者在消费数据)
 *                  开启一组EventProcessors和EventHandler线程的地方
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