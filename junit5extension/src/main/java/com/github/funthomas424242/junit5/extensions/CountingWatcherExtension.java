package com.github.funthomas424242.junit5.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CountingWatcherExtension implements TestWatcher, AfterAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountingWatcherExtension.class);

    private List<TestResultStatus> testResultsStatus = new ArrayList<>();

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        LOGGER.info(() -> "##### jetzt wars afterAll augerufen wurden (also pro Testklasse)");
    }

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

}