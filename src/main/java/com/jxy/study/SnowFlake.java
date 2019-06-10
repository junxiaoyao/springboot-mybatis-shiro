//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.jxy.study;
import java.util.HashMap;
import java.util.Map;


public class SnowFlake {
    private static Map<String, SnowFlake> map = new HashMap();
    private final long twepoch = 1420041600000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = 31L;
    private final long maxDatacenterId = 31L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public static long getNextId(Long workerId, Long datacenterId) {
        workerId = workerId == null ? 0L : workerId;
        datacenterId = datacenterId == null ? 0L : datacenterId;
        String key = workerId + "_" + datacenterId;
        SnowFlake snowFlake = (SnowFlake)map.get(key);
        if (snowFlake == null) {
            snowFlake = new SnowFlake(workerId, datacenterId);
            map.put(key, snowFlake);
        }

        return snowFlake.nextId();
    }

//    public static void main(String[] args) {
//        SnowFlake idWorker = new SnowFlake(0L, 0L);
//
//        for(int i = 0; i < 1000; ++i) {
//            long id = idWorker.nextId();
//            System.out.println(Long.toBinaryString(id));
//            System.out.println(id);
//        }
//
//    }

    private SnowFlake(long workerId, long datacenterId) {
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 31L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1420041600000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
