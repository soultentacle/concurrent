package com.gw.lean.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dell on 2018/8/1.
 */
public class LockLearn {

    private Object lock = new Object();

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("线程：" + Thread.currentThread().getName() + ",开始");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程：" + Thread.currentThread().getName() + ",结束");
                }
            }
        },"线程1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("线程：" + Thread.currentThread().getName() + ",开始");
                    lock.notify();
                    System.out.println("线程：" + Thread.currentThread().getName() + ",结束");
                }
            }
        },"线程2").start();
    }

    public void test1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println("线程：" + Thread.currentThread().getName() + ",开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程：" + Thread.currentThread().getName() + ",结束");
                reentrantLock.unlock();
            }
        },"线程1").start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println("线程：" + Thread.currentThread().getName() + ",开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程：" + Thread.currentThread().getName() + ",结束");
                reentrantLock.unlock();
            }
        },"线程2").start();
    }
}
