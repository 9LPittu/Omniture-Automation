package supportlibraries;

public enum ToolName {
	
	Appium("Appium"),
	Selenium_Desktop("Selenium_Desktop"), 
	Seetest("Seetest"),
	MobileLabs("MobileLabs"),
	RemoteWebDriver("RemoteWebDriver"), 
	Perfecto("Perfecto"),
	Selenium("Selenium"),
	Others("Others");
	
	private String value;
	
	ToolName(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
