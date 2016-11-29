package com.cyou.x.disunityweb.task;

import com.cyou.x.disunityweb.core.domain.BaseTask;
import com.google.common.eventbus.EventBus;

public class NotificationCenter {

	private static NotificationCenter instance = new NotificationCenter();
	private EventBus eventBus = new EventBus();
	
	private NotificationCenter() {
		
	}
	
	public static NotificationCenter instance() {
		return instance;
	}
	
	public void registEventListener(BaseTask listener) {
		eventBus.register(listener);
	}
	
	public void removeEventListener(BaseTask listener) {
		eventBus.unregister(listener);
	}
	
	public void postEvent(Object event) {
		eventBus.post(event);
	}
	
}
