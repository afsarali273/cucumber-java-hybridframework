package com.framework.playwright;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.framework.reusable.WebPWReusableComponents;
import com.framework.report.Status;

/**
 * Generic Base Step Definition using functional programming concepts
 */
public abstract class BaseStepDefinition extends WebPWReusableComponents {
    
    /**
     * Generic page action executor with error handling
     * @param <T> Page type
     * @param pageClass Page class
     * @param action Action to perform on page
     */
    protected <T extends BasePage> void executePageAction(Class<T> pageClass, Consumer<T> action) {
        try {
            T page = PageManager.getPage(pageClass);
            action.accept(page);
        } catch (Exception e) {
            addTestLog("Page Action", "Failed to execute action: " + e.getMessage(), Status.FAIL);
            throw e;
        }
    }
    
    /**
     * Simple page getter - application-agnostic
     * @param <T> Page type
     * @param pageClass Page class
     * @return Page instance
     */
    protected <T extends BasePage> T page(Class<T> pageClass) {
        return PageManager.getPage(pageClass);
    }
    
    /**
     * Generic page validation with custom predicate
     * @param <T> Page type
     * @param pageClass Page class
     * @param validation Validation predicate
     * @param successMessage Success message
     * @param failureMessage Failure message
     */
    protected <T extends BasePage> void validatePage(Class<T> pageClass, 
                                                   Predicate<T> validation,
                                                   String successMessage, 
                                                   String failureMessage) {
        T page = PageManager.getPage(pageClass);
        if (validation.test(page)) {
            addTestLog("Validation", successMessage, Status.PASS);
        } else {
            addTestLog("Validation", failureMessage, Status.FAIL);
        }
    }
    
    /**
     * Generic page data retrieval
     * @param <T> Page type
     * @param <R> Return type
     * @param pageClass Page class
     * @param dataExtractor Function to extract data from page
     * @return Extracted data
     */
    protected <T extends BasePage, R> R getPageData(Class<T> pageClass, Function<T, R> dataExtractor) {
        T page = PageManager.getPage(pageClass);
        return dataExtractor.apply(page);
    }
    
    /**
     * Conditional page action execution
     * @param <T> Page type
     * @param pageClass Page class
     * @param condition Condition to check
     * @param action Action to perform if condition is true
     * @param elseAction Action to perform if condition is false
     */
    protected <T extends BasePage> void conditionalPageAction(Class<T> pageClass,
                                                            Predicate<T> condition,
                                                            Consumer<T> action,
                                                            Consumer<T> elseAction) {
        T page = PageManager.getPage(pageClass);
        if (condition.test(page)) {
            action.accept(page);
        } else if (elseAction != null) {
            elseAction.accept(page);
        }
    }
    
    /**
     * Retry mechanism for flaky operations
     * @param <T> Return type
     * @param operation Operation to retry
     * @param maxRetries Maximum retry attempts
     * @param retryDelay Delay between retries in milliseconds
     * @return Operation result
     */
    protected <T> T retryOperation(Supplier<T> operation, int maxRetries, long retryDelay) {
        Exception lastException = null;
        
        for (int i = 0; i <= maxRetries; i++) {
            try {
                return operation.get();
            } catch (Exception e) {
                lastException = e;
                if (i < maxRetries) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }
        
        throw new RuntimeException("Operation failed after " + maxRetries + " retries", lastException);
    }
    
    /**
     * Performance measurement wrapper
     * @param <T> Return type
     * @param operation Operation to measure
     * @param operationName Name for logging
     * @return Operation result
     */
    protected <T> T measurePerformance(Supplier<T> operation, String operationName) {
        long startTime = System.currentTimeMillis();
        try {
            T result = operation.get();
            long duration = System.currentTimeMillis() - startTime;
            addTestLog("Performance", operationName + " completed in " + duration + "ms", Status.PASS);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            addTestLog("Performance", operationName + " failed after " + duration + "ms", Status.FAIL);
            throw e;
        }
    }
    
    /**
     * Simple assertion helper
     * @param condition Condition to check
     * @param successMessage Success message
     * @param failureMessage Failure message
     */
    protected void assertThat(boolean condition, String successMessage, String failureMessage) {
        if (condition) {
            addTestLog("Assertion", successMessage, Status.PASS);
        } else {
            addTestLog("Assertion", failureMessage, Status.FAIL);
        }
    }
}