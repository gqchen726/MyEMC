package com.briup.send;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.briup.gather.GatherImpl;
import com.briup.gather.IGather;

public class ISendTest {
	@Test
	public void t1() {
		ISend send = new SendImpl();
		IGather gather = new GatherImpl();
		send.send(gather.gather());
	}

	public static void main(String[] args) {
		ISend send = new SendImpl();
		IGather gather = new GatherImpl();
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
		newScheduledThreadPool.scheduleAtFixedRate(() -> {
			send.send(gather.gather());
		}, 1, 5, TimeUnit.SECONDS);
	}
}
