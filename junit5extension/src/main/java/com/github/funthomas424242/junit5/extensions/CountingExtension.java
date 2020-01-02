package com.github.funthomas424242.junit5.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Method;
import java.util.logging.Logger;


class CounterSingleton {

    private static class SingletonHelper {
        private static final CounterSingleton INSTANCE = new CounterSingleton();
    }

    public static CounterSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }


    private volatile int junit4SuccessCount;
    private volatile int junit4FailedCount;
    private volatile int junit5SuccessCount;
    private volatile int junit5FailedCount;

    private CounterSingleton() {
        junit4SuccessCount = 0;
        junit4FailedCount = 0;
        junit5SuccessCount = 0;
        junit5FailedCount = 0;
    }

    public void incJunit4Success() {
        junit4SuccessCount++;
    }

    public void incJunit4Failed() {
        junit4FailedCount++;
    }

    public void incJunit5Success() {
        junit5SuccessCount++;
    }

    public void incJunit5Failed() {
        junit5FailedCount++;
    }
}

/**
 * https://junit.org/junit5/docs/current/user-guide/#extensions-registration
 * https://www.codeflow.site/de/article/junit-5-extensions
 */
public class CountingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = Logger.getLogger(CountingExtension.class.getName());

    // Multiserverinstanzen: Das Feld evtl. in den ExtensionContext rein?
    private CounterSingleton counterSingleton;


    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        counterSingleton=CounterSingleton.getInstance();

        getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        counterSingleton=CounterSingleton.getInstance();
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;

        logger.info(() ->
                String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
    }

    private Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

}


