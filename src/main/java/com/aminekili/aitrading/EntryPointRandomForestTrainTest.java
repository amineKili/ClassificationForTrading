package com.aminekili.aitrading;

import com.aminekili.aitrading.model.implementation.RandomForestImpl;
import com.aminekili.aitrading.utils.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

public class EntryPointRandomForestTrainTest {
    public static void main(String... args) {
        try {
            Predicate<Double> filterMinute = val -> val == 20 || val == 40 || val == 0;
            var randomForestImpl = new RandomForestImpl(
                    "src/main/resources/AUD_train.csv", "src/main/resources/AUD_test.csv", "",
                    List.of(
                            new Pair<>("Minute", filterMinute)
                    )
            );
            randomForestImpl.train();
            randomForestImpl.test();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
