package com.aminekili.aitrading.model;

import java.io.IOException;
import java.net.URISyntaxException;

public interface BaseModel {

    void test() throws IOException, URISyntaxException;

    void train() throws IOException, URISyntaxException;

    void evaluateModelPrecision() throws IOException, URISyntaxException;

    void predict(double wap, double volume, double count, double minute, double tesla3, double tesla6, double tesla9);

    // TODO: implement
    default BaseModel read(String filePath) {
        return null;
    }

    // TODO: implement
    default void write(BaseModel model) {

    }

}