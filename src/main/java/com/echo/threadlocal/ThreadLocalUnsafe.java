package com.echo.threadlocal;

/**
 * threadLocal 错误使用导致线程不安全
 * 例如本例,threadLocal中保存的对象是static的,所以每个线程获取到的都是以同一个对象的引用,而不是单独的副本。
 */
public class ThreadLocalUnsafe implements Runnable{
    private ThreadLocal<Number> threadLocal = new ThreadLocal<>();

    private static Number number = new Number(0);

    @Override
    public void run() {
        try {
            // count加1
            number.setCount(number.getCount() + 1);
            // 存储到threadLocal
            threadLocal.set(number);
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get().getCount());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe()).start();
        }
    }



    static class Number {
        private Integer count;

        public Number(int initValue) {
            this.count = initValue;
        }
        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getCount() {
            return count;
        }
    }
}
