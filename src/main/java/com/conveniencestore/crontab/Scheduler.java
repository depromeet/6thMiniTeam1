package com.conveniencestore.crontab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.conveniencestore.dao.ProductDao;

@Component
public class Scheduler {

	private @Autowired ProductDao productDao;

	
	//@Scheduled(fixedRate = 60000)
	@Scheduled(cron="0 0 1 1 * ?")
	public void gs25ProductTimer() {
		Gs25ProductThread gs25ProductThread = new Gs25ProductThread(productDao);
		Thread thread = new Thread(gs25ProductThread);
		thread.start();
	}

}
