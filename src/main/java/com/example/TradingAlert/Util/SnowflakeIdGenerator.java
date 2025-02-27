package com.example.TradingAlert.Util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private static final SnowflakeIdGenerator INSTANCE = new SnowflakeIdGenerator();
    private static final long EPOCH = 1736985600000L; // 2025-01-16 00:00:00 UTC

    // 시퀀스설정
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    private SnowflakeIdGenerator() { }

    public static SnowflakeIdGenerator getInstance() {
        return INSTANCE;
    }
    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis() - EPOCH;

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards, refusing to generate id");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return (timestamp << TIMESTAMP_SHIFT) | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis() - EPOCH;
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis() - EPOCH;
        }
        return timestamp;
    }

}