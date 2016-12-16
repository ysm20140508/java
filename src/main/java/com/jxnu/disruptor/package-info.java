/**
 * disruptor 高性能框架源码阅读的心得及体会
 *
 * 1: RingBuffer 在初始化时 直接根据factory生成event填充数组,减少new的实例
 * 2：Sequence   在控制序号生成时，直接使用java的UNSAVE(CAS机制)控制高并发
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
 *
 *
 * @author shoumiao_yao
 * @date 2016-12-16
 */
package com.jxnu.disruptor;