package com.jxnu.disruptor;

/**
 * @author shoumiao_yao
 * @date 2016-12-15
 */
public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
