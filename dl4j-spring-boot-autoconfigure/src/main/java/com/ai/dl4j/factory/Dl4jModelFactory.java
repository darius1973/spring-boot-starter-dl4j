package com.ai.dl4j.factory;

import com.ai.dl4j.properties.*;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class Dl4jModelFactory {

    public static MultiLayerNetwork build(Dl4jProperties props) {

        var listBuilder = new NeuralNetConfiguration.Builder()
                .seed(props.getSeed())
                .list();

        int nIn = props.getInputSize();

        var layers = props.getModel().getLayers();

        if (layers.get(layers.size() - 1).getType() != LayerType.OUTPUT) {
            throw new IllegalStateException("Last layer must be OUTPUT");
        }

        for (LayerProperties layer : layers) {

            switch (layer.getType()) {

                case DENSE -> {
                    listBuilder.layer(new DenseLayer.Builder()
                            .nIn(nIn)
                            .nOut(layer.getSize())
                            .activation(mapActivation(layer.getActivation()))
                            .build());
                    nIn = layer.getSize();
                }

                case OUTPUT -> {
                    if (layer.getLoss() == null) {
                        throw new IllegalStateException("Output layer must define loss function");
                    }

                    listBuilder.layer(new OutputLayer.Builder(
                            mapLoss(layer.getLoss()))
                            .nIn(nIn)
                            .nOut(layer.getSize())
                            .activation(mapActivation(layer.getActivation()))
                            .build());
                    nIn = layer.getSize();
                }
            }
        }

        MultiLayerNetwork model = new MultiLayerNetwork(listBuilder.build());
        model.init();
        return model;
    }

    private static Activation mapActivation(ActivationType type) {
        return switch (type) {
            case RELU -> Activation.RELU;
            case SIGMOID -> Activation.SIGMOID;
            case TANH -> Activation.TANH;
            case IDENTITY -> Activation.IDENTITY;
        };
    }

    private static LossFunctions.LossFunction mapLoss(LossType type) {
        return switch (type) {
            case MSE -> LossFunctions.LossFunction.MSE;
            case XENT -> LossFunctions.LossFunction.XENT;
        };
    }
}

