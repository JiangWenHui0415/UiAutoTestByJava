package com.purang.yyt_uiautotest.core.webdriver;

import java.util.Properties;

import com.purang.yyt_uiautotest.utils.ParseProperties;

public class DriverManager {
	
	private String browserMode;
	private static final ParseProperties CONFIG = new ParseProperties("config/config.properties");
	
	public String getBrowserMode(){
		String _browser = null;
		Properties property = System.getProperties();
		if (property.containsKey("BROWSER")) {
			_browser = property.getProperty("BROWSER");
		} else {
			_browser = CONFIG.get("runBrowser");	
		}
		if (null != _browser && "" != _browser){
			return _browser.toLowerCase();
		}else{
			return null;
		}
	}
	
	public DriverController getController(){
		browserMode = getBrowserMode();
		if (browserMode == null || browserMode.contains("ie")) {
			return new IEDriverController();
		} else if (browserMode.contains("chrome")) {
			return new ChromeDriverController();
		} else if (browserMode.contains("firefox")) {
			return new FirefoxDriverController();
		} else {
			throw new RuntimeException("unsupported dirver type used, please extend it yourself!");
		}		
	}
}
