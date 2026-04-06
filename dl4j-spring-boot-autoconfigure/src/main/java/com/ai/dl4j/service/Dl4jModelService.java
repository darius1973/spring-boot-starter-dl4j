package com.ai.dl4j.service;

import com.ai.dl4j.properties.Dl4jProperties;
import lombok.RequiredArgsConstructor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;
import java.io.File;
import java.io.IOException;

/**
 * Core service responsible for managing the DL4J model lifecycle.
 *
 * Provides a high-level abstraction over a {@link MultiLayerNetwork},
 * handling training, inference, and model persistence operations.
 *
 * Responsibilities:
 * <ul>
 *   <li>Model initialization and usage</li>
 *   <li>Training execution</li>
 *   <li>Prediction/inference</li>
 *   <li>Saving and loading model state</li>
 * </ul>
 */
@RequiredArgsConstructor
public class Dl4jModelService {

    /**
     * Configuration properties defining model architecture and training parameters.
     */
    private final Dl4jProperties properties;

    /**
     * The underlying DL4J neural network used for training and inference.
     */
    private final MultiLayerNetwork model;

    /**
     * Trains the model using randomly generated synthetic data.
     *
     * This is a placeholder training implementation that:
     * <ul>
     *   <li>Generates random input data based on configured input size</li>
     *   <li>Generates random labels based on configured output size</li>
     *   <li>Runs training for the configured number of epochs</li>
     * </ul>
     *
     * Each epoch performs a full pass of {@code model.fit()}.
     */
    public void train() {
        var input = Nd4j.rand(10, properties.getInputSize());
        var labels = Nd4j.rand(10, properties.getOutputSize());

        for (int i = 0; i < properties.getEpochs(); i++) {
            model.fit(input, labels);
        }
    }

    /**
     * Performs inference on a single input sample.
     *
     * @param inputData raw feature vector
     * @return predicted output vector from the neural network
     */
    public double[] predict(double[] inputData) {
        var input = Nd4j.create(inputData);
        var output = model.output(input);
        return output.toDoubleVector();
    }

    /**
     * Saves the current model state to disk.
     *
     * The model is serialized using DL4J's {@link ModelSerializer}.
     *
     * @param path file location where the model should be stored
     * @throws IOException if writing to disk fails
     */
    public void save(String path) throws IOException {
        ModelSerializer.writeModel(model, new File(path), true);
    }

    /**
     * Loads a previously saved DL4J model from disk.
     *
     * @param path file location of the saved model
     * @return deserialized {@link MultiLayerNetwork} instance
     * @throws IOException if the file cannot be read or is invalid
     */
    public MultiLayerNetwork load(String path) throws IOException {
        return ModelSerializer.restoreMultiLayerNetwork(new File(path));
    }
}