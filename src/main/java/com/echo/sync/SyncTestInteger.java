package com.echo.sync;

/**
 * 测试synchronized关键字对Integer的同步
 */
public class SyncTestInteger extends Thread{
    private Integer intNumber;

    SyncTestInteger(Integer i) {
        this.intNumber = i;
    }
    @Override
    public void run() {
        addOne();
    }

    /**
     * 测试直接对intNumber对象加锁
     */
    public void addOne() {
        synchronized (intNumber) {
            Thread t = Thread.currentThread();
            System.out.println(t.getName() + " add before value: " + intNumber);
            intNumber++;
            System.out.println(t.getName() + " add after value: " + intNumber);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.getName() + " add sleep after value: " + intNumber);
        }
    }

    public static void main(String[] args) {
        Integer number = 1;

        for (int i = 0; i < 3; i++) {
            Thread t = new SyncTestInteger(number);
            t.start();
        }
    }
}