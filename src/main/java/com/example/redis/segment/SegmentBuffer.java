package com.example.redis.segment;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 双 Buffer 号段容器。
 * 使用 A/B 两个 Buffer 实现无中断切换：
 * - 当前 Buffer 消耗到阈值时异步加载下一个 Buffer
 * - 当前 Buffer 耗尽时原子切换到已加载的备用 Buffer
 *
 * @author laokou
 */
final class SegmentBuffer {

    private final Segment[] segments = new Segment[2];
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AtomicBoolean nextReady = new AtomicBoolean(false);
    private final ReentrantReadWriteLock switchLock = new ReentrantReadWriteLock();
    private final Lock readLock = switchLock.readLock();
    private final Lock writeLock = switchLock.writeLock();

    public int getNextIndex() {
        return 1 - currentIndex.get();
    }

}
