package com.lr12.test.listeners;

import java.util.logging.Logger;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {
    private static final Logger logger = Logger.getLogger(RetryListener.class.getName());
    private static final int MAX_RETRY = 1;
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess() && retryCount < MAX_RETRY) {
            retryCount++;
            logger.warning("ПОВТОР ТЕСТА: " + result.getMethod().getMethodName() + 
                " (Попытка " + (retryCount + 1) + ")");
            return true;
        }
        return false;
    }
}
