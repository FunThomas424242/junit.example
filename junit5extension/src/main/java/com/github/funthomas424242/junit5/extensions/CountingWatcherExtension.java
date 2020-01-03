package com.github.funthomas424242.junit5.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Optional;

class CounterSingleton {

    private static class SingletonHelper {
        private static final CounterSingleton INSTANCE = new CounterSingleton();
    }

    public static CounterSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }


    private volatile int aborted;
    private volatile int skipped;
    private volatile int success;
    private volatile int failed;

    private CounterSingleton() {
        aborted = 0;
        skipped = 0;
        success = 0;
        failed = 0;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown Hook is running ! \n CounterSingleton{" +
                    "aborted=" + aborted +
                            ", skipped=" + skipped +
                            ", success=" + success +
                            ", failed=" + failed +
                            '}');
            }
        });
    }

    @Override
    public String toString() {
        return "CounterSingleton{" +
                "aborted=" + aborted +
                ", skipped=" + skipped +
                ", success=" + success +
                ", failed=" + failed +
                '}';
    }

    public void incAborted() {
        aborted++;
    }

    public void incSkipped() {
        skipped++;
    }

    public void incSuccess() {
        success++;
    }

    public void incFailed() {
        failed++;
    }
}


public class CountingWatcherExtension implements TestWatcher, TestInstancePostProcessor, BeforeAllCallback, AfterAllCallback {

    public static final int KEY_COUNTER = 1;


    private static final Logger LOGGER = LoggerFactory.getLogger(CountingWatcherExtension.class);


    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        final CounterSingleton counter = getCounter(extensionContext);
        if (counter == null) {
            final ExtensionContext.Store rootStore = getRootStore(extensionContext);
            final CounterSingleton newCounter = CounterSingleton.getInstance();
            rootStore.put(KEY_COUNTER, newCounter);
            LOGGER.info(() -> String.format("### Counter im RootScope abgelegt: %s", newCounter));
        } else {
            LOGGER.info(() -> String.format("### Counter im RootScope gefunden: %s", counter));
        }
    }

    private CounterSingleton getCounter(ExtensionContext extensionContext) {
        final ExtensionContext.Store rootStore = getRootStore(extensionContext);
        return rootStore.get(KEY_COUNTER, CounterSingleton.class);
    }

    private ExtensionContext.Store getRootStore(ExtensionContext extensionContext) {
        final ExtensionContext rootContext = extensionContext.getRoot();
        return rootContext.getStore(ExtensionContext.Namespace.create(CountingWatcherExtension.class));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        LOGGER.info(() -> String.format("### aktueller Zählerstand: %s" + getCounter(extensionContext)));
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        getCounter(context).incSkipped();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        getCounter(context).incSuccess();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        getCounter(context).incAborted();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        getCounter(context).incFailed();
    }

    @Override
    public void postProcessTestInstance(Object testObjektinstanz, ExtensionContext context) throws Exception {
        LOGGER.info(() -> String.format("### %s", getCounter(context)));
    }
}