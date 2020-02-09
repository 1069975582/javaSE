package com.echo.vola;

/**
 * volatile 可见性测试
 */
public class VolatileTestVisibility extends Thread {
    private static volatile boolean stopFlag = false;
    private static int number = 1;

    static class ShowVolatileVar extends Thread {
        @Override
        public void run() {
            while (!stopFlag);
            System.out.println(Thread.currentThread().getName() + " I need stop now!, number = " + number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new ShowVolatileVar();
        t.start();

        Thread.sleep(1000);
        number = 55;
        stopFlag = true;
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " stop");
    }
}
