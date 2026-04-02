package com.ai.dl4j.service;

import com.ai.dl4j.properties.Dl4jProperties;
import lombok.RequiredArgsConstructor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class Dl4jModelService {

    private final Dl4jProperties properties;
    private final MultiLayerNetwork model;

    public void train() {
        var input = Nd4j.rand(10, properties.getInputSize());
        var labels = Nd4j.rand(10, properties.getOutputSize());

        for (int i = 0; i < properties.getEpochs(); i++) {
            model.fit(input, labels);
        }
    }

    public double[] predict(double[] inputData) {
        var input = Nd4j.create(inputData);
        var output = model.output(input);
        return output.toDoubleVector();
    }

    // Save model
    public void save(String path) throws IOException {
        ModelSerializer.writeModel(model, new File(path), true);
    }

    // Load model
    public MultiLayerNetwork load(String path) throws IOException {
        return ModelSerializer.restoreMultiLayerNetwork(new File(path));
    }
}