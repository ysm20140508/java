package com.jxnu.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author shoumiao_yao
 * @date 2016-12-15
 */
public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        //重写线程工厂
        DisruptorThreadFactory threadFactory=new DisruptorThreadFactory("disruptor");
        //业务实体工厂类
        LongEventFactory factory = new LongEventFactory();
        //环形缓冲区大小
        int bufferSize = 1024;
        //disruptor启动 生成环(ring)
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //生产者向环(ring)写入业务数据
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for (long l = 0; true; l++){
            producer.onData(l);
            Thread.sleep(1000);
        }
    }
}
