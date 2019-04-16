package com.conveniencestore.crontab;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.conveniencestore.dao.ProductDao;
import com.conveniencestore.model.Product;

public class Gs25ProductThread implements Runnable{

	private ProductDao productDao;

	public Gs25ProductThread(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void run() {
		//productDao.save(new Product("test", "test", "test", "test", "test", new Date()));
		System.setProperty("webdriver.chrome.driver", "C:\\app\\chromedriver_win32 (1)\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://gs25.gsretail.com/gscvs/ko/products/event-goods;");

		for (int i = 2; i < 50; i++) {
			boolean staleElement = true;
			while (staleElement){
				try {
					WebElement prod_list = driver.findElement(By.className("prod_list"));
					List<WebElement> title  = prod_list.findElements(By.className("tit"));
					List<WebElement> cost  = prod_list.findElements(By.className("cost"));
					List<WebElement> img  = prod_list.findElements(By.tagName("img"));
					if (title == null){
						break;
					}
					for (int j = 0; j < title.size(); j++) {
						//System.out.println("title2"+j+":::"+title.get(j).getText());
						//System.out.println("cost2"+j+":::"+cost.get(j).getText());
						//System.out.println("img2"+j+":::"+img.get(j).getAttribute("src"));
						productDao.save(new Product(title.get(j).getText(), "Gs25", cost.get(j).getText(), "1+1", img.get(j).getAttribute("src"), new Date()));
					}
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("goodsPageController.movePage("+(i)+")");
					staleElement = false;
				} catch (StaleElementReferenceException e) {
					staleElement = true;
				}
			}
		}
	}
}
