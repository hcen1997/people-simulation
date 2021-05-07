package com.gistone.demomybatis;

import org.springframework.util.StopWatch;

import java.util.concurrent.ThreadLocalRandom;

public class SpeadTest {
    public static void main(String[] args) {

        int ARRAY_SIZE = 50000;
        int[] data = new int[ARRAY_SIZE];
        int DATA_STRIDE = 256;

        for (int c = 0; c < ARRAY_SIZE; ++c) {
            data[c] = ThreadLocalRandom.current().nextInt() % DATA_STRIDE;
        }

//        Arrays.sort(data); // 7.56 s
        // 没有sort 18 s

        {  // 测试部分
            StopWatch stopWatch = new StopWatch();
            long sum = 0;
            stopWatch.start();
            for (int i = 0; i < 100000; ++i) {
                for (int c = 0; c < ARRAY_SIZE; ++c) {
                    if (data[c] >= 128) sum += data[c];
                }
            }
            stopWatch.stop();
            double elapsedTime = stopWatch.getTotalTimeSeconds();

            System.out.println(elapsedTime);
            System.out.println("sum = " + sum);
        }
    }
}
