package com.importsource.util.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 示例：CountDownLatch的使用举例
 * 
 * @author hezf
 */
public class SimpleCountDownLatchDemo {
	private static final int N = 10;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(N);
		CountDownLatch startSignal = new CountDownLatch(1);// 开始执行信号

		for (int i = 1; i <= N; i++) {
			new Thread(new Worker(i,doneSignal, startSignal)).start();// 线程启动了
		}
		
		System.out.println("begin------------");
		startSignal.countDown();// 开始执行啦
		doneSignal.await();// 等待所有的线程执行完毕
		System.out.println("Ok");

	}

	static class Worker implements Runnable {
		private final CountDownLatch doneSignal;
		private final CountDownLatch startSignal;
		private int index;

		Worker(int index, CountDownLatch doneSignal, CountDownLatch startSignal) {
			this.index = index;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void run() {
			try {
				startSignal.await(); // 等待开始执行信号的发布
				System.out.println("sdfsdfsdf"+index);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				doneSignal.countDown();
			}
		}
	}
}
