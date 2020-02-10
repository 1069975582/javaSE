package com.echo.waitNotify;

/**
 * 线程中 wait/notify/notifyAll 的协作
 */
public class WaitNotify{
    public static XiaoMi10 xiaoMi10 = new XiaoMi10();

    /**
     * 库存
     */
    static class XiaoMi10{
        // 剩余产品数量
        private int productNum = 0;

        // 是否下架
        private boolean down = false;
    }

    /**
     * 售货线程
     */
    static class saleClass extends Thread {
        @Override
        public void run() {
            // 未下架
            synchronized (xiaoMi10) {
                while (!xiaoMi10.down) {
                    if (xiaoMi10.productNum == 0) {
                        System.out.println("无货, 需要进货，暂停售卖!");
                        try {
                            xiaoMi10.wait();
                            System.out.println("已进货, 开始售卖!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        xiaoMi10.productNum--;
                    }
                }
                System.out.println("商品下架!");
            }
        }
    }

    static class AddStock extends Thread {
        @Override
        public void run() {
            while (!xiaoMi10.down) {
                synchronized (xiaoMi10) {
                    if (xiaoMi10.productNum > 5) {
                        System.out.println("剩余库存充足,暂不进货: " + xiaoMi10.productNum);
                    } else {
                        System.out.println("库存不足,进货 +10");
                        xiaoMi10.productNum = xiaoMi10.productNum + 10;
                        xiaoMi10.notifyAll();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("商品下架,库存剩余: " + xiaoMi10.productNum);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread sale = new saleClass();
        sale.start();

        Thread add = new AddStock();
        add.start();

        Thread.sleep(10000);
        down();
    }

    /**
     * 通知商品下架
     */
    public static void down() {
        synchronized (xiaoMi10) {
            xiaoMi10.down = true;
            xiaoMi10.notifyAll();
        }
    }
}
