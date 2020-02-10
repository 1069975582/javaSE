package com.echo.waitNotify;

/**
 * 《wait/notify实现生产者和消费者程序》
 * 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，生产者线程是一个压入线程，
 * 它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 */
public class Gun {
    /**
     * 子弹数量
     */
    private int bullet;

    /**
     * 射击
     */
    private boolean shoot;

    public void setBullet(int bullet) {
        this.bullet = bullet;
    }

    public int getBullet() {
        return bullet;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean getShoot() {
        return this.shoot;
    }

    /**
     * 射出一颗子弹
     */
    public void shoot() {
        System.out.println("我射出了一颗子弹:" + bullet--);
    }

    public static void main(String[] args) {
        // 获得一把枪
        Gun gun = new Gun();
        gun.setShoot(true);

        Producer producer = new Producer(gun);
        Consumer consumer = new Consumer(gun);

        producer.start();
        consumer.start();
    }
}

/**
 * 生产子弹
 */
class Producer extends Thread {
    private Gun gun;

    Producer(Gun gun) {
        this.gun = gun;
    }
    @Override
    public void run() {
        synchronized (gun) {
            while (gun.getShoot()) {
                if (gun.getBullet() == 0) {
                    System.out.println(Thread.currentThread().getName() + "压入了10颗子弹");
                    gun.setBullet(10);
                    // 通知其他线程装弹完毕
                    gun.notifyAll();
                } else {
                    // 子弹够用
                    try {
                        gun.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("停止射击");
    }
}

/**
 * 射出子弹
 */
class Consumer extends Thread {
    private Gun gun;

    Consumer(Gun gun) {
        this.gun = gun;
    }

    @Override
    public void run() {
        synchronized (gun) {
            while (gun.getShoot()) {
                if (gun.getBullet() == 0) {
                    // 通知其他线程没有子弹了
                    try {
                        System.out.println("子弹不足,等待装弹!");
                        gun.notifyAll();
                        gun.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    gun.shoot();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("停止射击");
    }
}