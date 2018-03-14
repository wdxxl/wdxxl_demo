package com.wdxxl.google.guava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

// Guava ListenableFuture介绍以及使用
// https://www.jianshu.com/p/e3da6d4cd08e
public class ListenableFutureDemo {
	public static void main(String[] args) {
		ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
		// 执行任务
		final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("新任务。。。");
				TimeUnit.SECONDS.sleep(1);
				// throw new RuntimeException("something wrong");
				return 7;
			}
		});
		// 任务完成回调函数
		final FutureCallback<Integer> futureCallback = new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				System.out.println("任务执行成功，对任务进行操作。" + result);
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务执行失败。" + t.getMessage());
			}
		};
		// 绑定任务以及回调函数
		Futures.addCallback(listenableFuture, futureCallback, MoreExecutors.directExecutor());
	}

}
