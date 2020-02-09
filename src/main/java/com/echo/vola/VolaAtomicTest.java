package com.echo.vola;

/**
 * 测试 volatile 是否支持原子性操作
*/
public class VolaAtomicTest {
    private volatile int count = 0;

    static class countThread extends Thread {
        private VolaAtomicTest vola;

        public countThread(VolaAtomicTest vola) {
            this.vola = vola;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                vola.count++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolaAtomicTest vola = new VolaAtomicTest();

        Thread t1 = new countThread(vola);
        Thread t2 = new countThread(vola);

        t1.start();
        t2.start();

        Thread.sleep(2000);
        // 结果是否是20000？
        System.out.println(vola.count);
    }
}
