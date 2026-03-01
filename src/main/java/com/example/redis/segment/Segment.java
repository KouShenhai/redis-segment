package com.example.redis.segment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 号段模型 — 表示一个从 Redis 申请到的连续 ID 区间.
 * [minId, maxId]
 *
 * @author laokou
 */
@Getter
final class Segment {

    private final long minId;
    private final long maxId;
    private final int step;
    private final int ref;
    private final AtomicLong cursor;

    public Segment(long maxId, int step, double loadFactor) {
        this.maxId = maxId;
        this.step = step;
        this.minId = maxId - step + 1;
        this.ref = (int) (loadFactor * step);
        this.cursor = new AtomicLong(this.minId);
    }

    public long nextId() {
        long id = this.cursor.getAndIncrement();
        return id > this.maxId ? -1 : id;
    }

    public List<Long> nextIds(int num) {
        int size = Math.min(this.step, num);
        List<Long> ids = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ids.add(this.cursor.getAndIncrement());
        }
        return ids;
    }

    public boolean isNext() {
        return this.cursor.get() >= this.ref + this.minId;
    }

    public static void main(String[] args) {
        Segment segment = new Segment(10, 10, 0.8);
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.nextId());
        System.out.println(segment.isNext());
    }

}
