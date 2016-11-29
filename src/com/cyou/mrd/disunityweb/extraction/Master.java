package com.cyou.mrd.disunityweb.extraction;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cyou.mrd.disunityweb.core.domain.task.BaseTask;
import com.cyou.mrd.disunityweb.dal.DAL;

public class Master extends Thread {
	
	protected Queue<BaseTask> workQueue= new ConcurrentLinkedQueue<BaseTask>();
	
	private static Object key = new Object();
	private static Master instance;
	public static Master getInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new Master();
				}
			}
		}
		return instance;
	}
	
	private Master() {
		this.start();
	}
	
	//提交一个任务  
    public void submit(BaseTask job){  
        workQueue.add(job);
    } 
    
    @Override
    public void run() {
    	for (;;) {
    		try {
        		if (workQueue.isEmpty()) {
        			Thread.sleep(1000*5);
            	} else {
            		BaseTask bt = workQueue.poll();
            		DAL.tasks.put(String.valueOf(bt.getTaskId()), bt);// 将丢出来执行的任务，存到DAL层，方便查询任务结果
            		
            		System.out.println("执行任务！ TaskID ： "+bt.getTaskId());
            		bt.doTask();
            		
            		
            		
            	}

    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
	
}
