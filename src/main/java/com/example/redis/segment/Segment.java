package com.example.redis.segment;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Segment {

    private final long minId;
    private final long maxId;
    private final long ref;
    private final AtomicLong cursor;

    public Segment(long maxId, long step, double loadFactor) {
        this.maxId = maxId;
        this.minId = maxId - step + 1;
        this.ref = (long) (loadFactor * step);
        this.cursor = new AtomicLong(this.minId);
    }

    public long nextId() {
        long id = this.cursor.getAndIncrement();
        return id > maxId ? -1 : id;
    }

    public boolean isNext() {
        return cursor.get() >= ref + minId;
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
