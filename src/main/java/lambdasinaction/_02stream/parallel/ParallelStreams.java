package lambdasinaction._02stream.parallel;

import java.util.stream.*;

public class ParallelStreams {
    //기존 For Loop => 4 msecs
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }
    //순차 스트림 : Stream과 iterate() (크기가 고정되지 않음) 사용 => 129 msecs
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    //병렬 스트림 : Stream과 iterate() (크기가 고정되지 않음) 사용 => 342 msecs
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }
    
    //순차 스트림 : LongStream과 rangeClosed() (크기가 고정됨) 사용 => 23 msecs
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    //병렬 스트림 : LongStream과 rangeClosed() (크기가 고정됨) 사용 => 4 msecs
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    //순차스트림  정상 => 32 msecs
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }
    //병렬 스트림 계산이 정상적으로 되지 않음 => 54 msecs
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}
