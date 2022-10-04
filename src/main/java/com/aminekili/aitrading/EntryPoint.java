package com.aminekili.aitrading;

import com.aminekili.aitrading.model.logic.RandomForestImpl;

import java.io.IOException;
import java.net.URISyntaxException;

public class EntryPoint {
    public static void main(String... args) {
        try {
            var randomForestImpl = new RandomForestImpl("src/main/resources/AUD_train.csv", "src/main/resources/AUD_test.csv", "");
            randomForestImpl.evaluateModelPrecision();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
