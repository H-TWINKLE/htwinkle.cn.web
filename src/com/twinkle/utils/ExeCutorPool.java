package com.twinkle.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ExeCutorPool {

	INSTANCE;
	
	private ThreadPoolExecutor initExeCutorPool() {
		
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(20);		
		return new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, workQueue);		
	}
	
	public void AddToExeCutorPool(Runnable r) {
		
		initExeCutorPool().execute(r);
	}
	
	
	

}
