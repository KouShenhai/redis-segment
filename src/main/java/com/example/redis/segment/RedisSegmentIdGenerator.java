package com.example.redis.segment;

public class RedisSegmentIdGenerator {

    private final int maxRetry = 3;
    private final SegmentBuffer segmentBuffer;

    public RedisSegmentIdGenerator() {
        this.segmentBuffer = new SegmentBuffer();
    }

    private void loadSegmentSync(boolean isCurrent) {

    }

}
