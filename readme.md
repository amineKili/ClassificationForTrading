# Trading Classifier
## Multi-Model classifier

> Trading Classifier Uses Multiple Model to classify trading Signal.


![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)

Given a set of inputs

- Prices ['open','close', 'high', 'low', 'wap']
- Volume
- Count: trades count during the time period
- Minute
- Tesla magic numbers ['tesla3', 'tesla6', 'tesla9']
- Decision: flag can be NO, BUY or SELL
- Execute: flag execute the decision or ignore it, can be EXECUTE or No.

## Features
- Multi-Classification Models to pick from.
    + Random Forest
    + Grandient Boosted Trees
    + Multi Layer Perceptron
- Each Model's implementation is fully costumizable.
- Adjustable Training and test training batch sizes.
- Any of the feature can be predicted. eg the systeme can be used to predict the close price or the execute flag.
-
Data should be given in the following format:

| Currency | YYYYMMDD_HHMMSS | Open | High | Low | Close | Volume | WAP | Count | Minute | Tesla3 | Tesla6 | Tesla9 | Decision | EXECUTE |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |


## Usage

Trading Classifier.

> Any changes in DataSet input will need adjustement in DataFrameUtils and Model's implementation Formula.

### Model Evaluation
Used to analyse the performance of the model using the dataset.
1. Put data in src/main/resources.
2. For evaluation change the file paths in EntryPoint**ModelName**Evaluation.java for the model.
3. Run the EntryPoint**ModelName**.java

### Model Training And Test
Used to Train and Test the model, user either can use the test data for production or used the predict function in each model to predict the result for a user-given inputs.
1. Put data in src/main/resources.
2. For evaluation change the file paths in EntryPoint**ModelName**TrainTest.java for the model.
3. Run the EntryPoint**ModelName**TrainTest.java


## Tech

Trading Classifier uses a number of open source projects to work properly:

- [SMILE](https://haifengl.github.io/classification.html) - Statistical Machine Intelligence and Learning Engine

## Installation

Trading Classifier requires **Java 17** and **Maven** to run.

To build the project

```sh
mvn clean install
```
