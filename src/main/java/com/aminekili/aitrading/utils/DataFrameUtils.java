package com.aminekili.aitrading.utils;


import com.aminekili.aitrading.service.CsvReader;
import smile.data.DataFrame;
import smile.data.formula.Formula;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * This class handles the data set operations
 * Merging two data sets
 * Splitting a data set into two data sets
 * Normalizing a data set
 */
public class DataFrameUtils {

    public DataFrame merge(DataFrame dataframe1, DataFrame dataframe2) {
        return dataframe1.merge(dataframe2);
    }

    public DataFrame[] split(DataFrame dataframe, double ratio) {
        DataFrame[] dataFrames = new DataFrame[2];
        int count = dataframe.size();
        int splitIndex = (int) (count * ratio);
        dataFrames[0] = dataframe.slice(0, splitIndex);
        dataFrames[1] = dataframe.slice(splitIndex, count);
        return dataFrames;
    }

    public DataFrame normalize(DataFrame dataframe) {
        Map<Integer, Double> min = new HashMap<>();
        Map<Integer, Double> max = new HashMap<>();

        // Iterate over columns
        // Find min and max values for each column
        for (int i = 0; i < dataframe.ncols(); i++) {
            double[] column = dataframe.column(i).toDoubleArray();
            double minVal = Double.MAX_VALUE;
            double maxVal = Double.MIN_VALUE;
            for (double val : column) {
                if (val < minVal) {
                    minVal = val;
                }
                if (val > maxVal) {
                    maxVal = val;
                }
            }
            min.put(i, minVal);
            max.put(i, maxVal);
        }

        // Iterate over columns
        // Normalize each column
        // Create a copy of dataframe
        // Put normalized values in the copy
        double[][] normalizedData = new double[dataframe.nrows()][dataframe.ncols()];
        String[] names = dataframe.names();
        for (int i = 0; i < dataframe.ncols(); i++) {
            double[] column = dataframe.column(i).toDoubleArray();
            double minVal = min.get(i);
            double maxVal = max.get(i);
            for (int j = 0; j < column.length; j++) {
                double normalizedVal = (column[j] - minVal) / (maxVal - minVal);
                normalizedData[j][i] = normalizedVal;
            }
        }

        return DataFrame.of(normalizedData, names);
    }

    /**
     * This method reads the data set from the given path
     *
     * @param paths
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public DataFrame mergeMultipleFiles(DataFrame[] paths) throws IOException, URISyntaxException {
        // Dataframe must be normalized before merging, otherwise it will cause inconsistency in the data

        DataFrame merged = paths[0];
        var normalized = normalize(merged);
        for (int i = 1; i < paths.length; i++) {
            var normalized2 = normalize(paths[i]);
            merged = merge(normalized, normalized2);
            normalized = normalize(merged);
        }
        return merged;
    }


    /**
     * Filter dataframe based on Predicate function
     *
     * @return filtered dataframe
     * @throws IOException
     * @throws URISyntaxException
     */
    public DataFrame filter(DataFrame dataframe, String columnName, Predicate<Double> predicate) throws IOException, URISyntaxException {
        double[][] resultData = new double[dataframe.nrows()][dataframe.ncols()];
        String[] names = dataframe.names();
        int line = 0;
        for (int i = 0; i < dataframe.nrows(); i++) {
            if (!predicate.test(dataframe.getDouble(i, columnName))) {
                continue;
            }
            for (int j = 0; j < dataframe.ncols(); j++) {
                resultData[line][j] = dataframe.getDouble(i, j);
            }
            line++;
        }
        return DataFrame.of(resultData, names);
    }

    public static void main(String... args) throws IOException, URISyntaxException {
        final Formula formula = Formula.of("EXECUTE", "WAP", "Count", "Minute", "Tesla3", "Tesla6", "Tesla9", "Decision");

        var dataframe = CsvReader.read("src/main/resources/AUD_train.csv", formula);
        LoggingUtils.print("Dataframe\n" + dataframe.toString());
        LoggingUtils.print("Dataframe\n" + dataframe.summary().toString());

        var filtered = new DataFrameUtils().filter(dataframe, "Minute", val -> val == 20 || val == 40 || val == 0);
        LoggingUtils.print("Filtered\n" + filtered.toString());
        LoggingUtils.print("Filtered\n" + filtered.summary().toString());

        var formatted = formula.frame(filtered);
        LoggingUtils.print("Formatted\n" + formatted.toString());
        LoggingUtils.print("Formatted\n" + formatted.schema().toString());

        var dataframeUtils = new DataFrameUtils();

        var normalizedDataframe = dataframeUtils.normalize(dataframe);

        LoggingUtils.print("Normalized\n" + normalizedDataframe.toString());
        LoggingUtils.print("Normalized\n" + normalizedDataframe.summary().toString());
    }


}
