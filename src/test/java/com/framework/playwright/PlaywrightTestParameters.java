package com.framework.playwright;

import com.framework.components.IterationOptions;
import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;

public class PlaywrightTestParameters {
	
	private String currentScenario;
	private String currentTestcase;
	private String currentTestInstance = "Instance1";
	private String currentTestDescription = "";
	
	private IterationOptions iterationMode = IterationOptions.RUN_ALL_ITERATIONS;
	private int startIteration = 1;
	private int endIteration = 1;
	
	private ExecutionMode executionMode = ExecutionMode.LOCAL;
	private Browser browser = Browser.CHROME;
	private String browserVersion;
	private boolean headless = false;
	private int slowMo = 0;
	private String viewport = "1920x1080";
	private boolean recordVideo = false;
	private boolean enableTracing = false;
	
	public PlaywrightTestParameters(String currentScenario, String currentTestcase) {
		this.currentScenario = currentScenario;
		this.currentTestcase = currentTestcase;
	}
	
	// Getters and Setters
	public String getCurrentScenario() {
		return currentScenario;
	}
	
	public void setCurrentScenario(String currentScenario) {
		this.currentScenario = currentScenario;
	}
	
	public String getCurrentTestcase() {
		return currentTestcase;
	}
	
	public void setCurrentTestcase(String currentTestcase) {
		this.currentTestcase = currentTestcase;
	}
	
	public String getCurrentTestInstance() {
		return currentTestInstance;
	}
	
	public void setCurrentTestInstance(String currentTestInstance) {
		this.currentTestInstance = currentTestInstance;
	}
	
	public String getCurrentTestDescription() {
		return currentTestDescription;
	}
	
	public void setCurrentTestDescription(String currentTestDescription) {
		this.currentTestDescription = currentTestDescription;
	}
	
	public IterationOptions getIterationMode() {
		return iterationMode;
	}
	
	public void setIterationMode(IterationOptions iterationMode) {
		this.iterationMode = iterationMode;
	}
	
	public int getStartIteration() {
		return startIteration;
	}
	
	public void setStartIteration(int startIteration) {
		this.startIteration = startIteration;
	}
	
	public int getEndIteration() {
		return endIteration;
	}
	
	public void setEndIteration(int endIteration) {
		this.endIteration = endIteration;
	}
	
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}
	
	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
	
	public String getBrowserVersion() {
		return browserVersion;
	}
	
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	
	public boolean isHeadless() {
		return headless;
	}
	
	public void setHeadless(boolean headless) {
		this.headless = headless;
	}
	
	public int getSlowMo() {
		return slowMo;
	}
	
	public void setSlowMo(int slowMo) {
		this.slowMo = slowMo;
	}
	
	public String getViewport() {
		return viewport;
	}
	
	public void setViewport(String viewport) {
		this.viewport = viewport;
	}
	
	public boolean isRecordVideo() {
		return recordVideo;
	}
	
	public void setRecordVideo(boolean recordVideo) {
		this.recordVideo = recordVideo;
	}
	
	public boolean isEnableTracing() {
		return enableTracing;
	}
	
	public void setEnableTracing(boolean enableTracing) {
		this.enableTracing = enableTracing;
	}
}