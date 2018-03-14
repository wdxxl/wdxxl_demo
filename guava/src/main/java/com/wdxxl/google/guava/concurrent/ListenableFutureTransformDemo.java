package com.wdxxl.google.guava.concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

// http://www.cnblogs.com/whitewolf/p/4113860.html
// Guava - 并行编程Futures
public class ListenableFutureTransformDemo {

	public static void main(String[] args) {
		ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

		ListenableFuture<Integer> future1 = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("Call future 1.");
				return 1;
			}
		});

		ListenableFuture<Integer> future2 = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("Call future 2.");
				TimeUnit.SECONDS.sleep(1);
				// throw new RuntimeException("something wrong");
				return 2;
			}
		});

		final ListenableFuture<List<Integer>> allFutures = Futures.allAsList(future1, future2);
		AsyncFunction<List<Integer>, String> asyncFunction = new AsyncFunction<List<Integer>, String>() {
			@Override
			public ListenableFuture<String> apply(List<Integer> input) throws Exception {
				return Futures.immediateFuture(String.format("success future:%s", input.toString()));
			}
		};

		final ListenableFuture<String> transform = Futures.transformAsync(allFutures, asyncFunction,
				MoreExecutors.directExecutor());
		final FutureCallback<String> callback = new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.printf("success with: %s%n", result);
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.printf("onFailure%s%n", t.getMessage());
			}
		};

		Futures.addCallback(transform, callback, MoreExecutors.directExecutor());
	}

}
