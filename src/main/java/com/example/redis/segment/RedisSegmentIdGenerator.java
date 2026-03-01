package com.example.redis.segment;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class RedisSegmentIdGenerator {

    /**
     * 最大重试次数.
     */
    private final int maxRetry = 3;

    /**
     * 重试初始退避时间（毫秒）.
     */
    private final long retryDelay = 100;

    private final SegmentBuffer segmentBuffer;

    private final ExecutorService virtualThreadExecutor;

    public RedisSegmentIdGenerator(ExecutorService virtualThreadExecutor) {
        this.segmentBuffer = new SegmentBuffer();
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    private void triggerAsyncLoad() {
        if (segmentBuffer.trySetLoading()) {
            virtualThreadExecutor.execute(() -> {
                try {
                    loadSegmentSync(false);
                } catch (Exception e) {
                    log.error("Async segment load failed", e);
                }
            });
        }
    }

    private void loadSegmentSync(boolean isCurrent) {

    }

}
