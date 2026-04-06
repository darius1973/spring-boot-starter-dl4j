package com.ai.dl4j.factory;

import com.ai.dl4j.properties.*;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * Factory class responsible for building a DeepLearning4J {@link MultiLayerNetwork}
 * based on a declarative configuration object ({@link Dl4jProperties}).
 *
 * <p>This class translates a custom model definition (layers, activations, loss functions)
 * into a DL4J neural network configuration and initializes the model.
 */
public class Dl4jModelFactory {

    /**
     * Builds and initializes a {@link MultiLayerNetwork} from the provided configuration.
     *
     * <p>The method:
     * <ul>
     *   <li>Reads global model properties (seed, input size)</li>
     *   <li>Iterates through layer definitions in order</li>
     *   <li>Constructs Dense and Output layers accordingly</li>
     *   <li>Validates required constraints (e.g., last layer must be OUTPUT)</li>
     *   <li>Maps custom enums to DL4J activation and loss functions</li>
     * </ul>
     * @param props model configuration containing architecture and training parameters
     * @return an initialized {@link MultiLayerNetwork} ready for training
     * @throws IllegalStateException if configuration is invalid (e.g., missing output layer or loss function)
     */
    public static MultiLayerNetwork build(Dl4jProperties props) {

        // Initialize network configuration builder with seed for reproducibility
        var listBuilder = new NeuralNetConfiguration.Builder()
                .seed(props.getSeed())
                .list();

        // Track input size for layer chaining (nIn -> nOut propagation)
        int nIn = props.getInputSize();

        // Retrieve layer definitions from configuration
        var layers = props.getModel().getLayers();

        // Ensure the last layer is an OUTPUT layer (required by this factory design)
        if (layers.get(layers.size() - 1).getType() != LayerType.OUTPUT) {
            throw new IllegalStateException("Last layer must be OUTPUT");
        }

        // Build each layer sequentially
        for (LayerProperties layer : layers) {

            switch (layer.getType()) {

                case DENSE -> {
                    // Fully connected hidden layer
                    listBuilder.layer(new DenseLayer.Builder()
                            .nIn(nIn) // input size from previous layer
                            .nOut(layer.getSize()) // number of neurons in this layer
                            .activation(mapActivation(layer.getActivation()))
                            .build());

                    // Update input size for next layer
                    nIn = layer.getSize();
                }

                case OUTPUT -> {
                    // Output layer must define a loss function for training
                    if (layer.getLoss() == null) {
                        throw new IllegalStateException("Output layer must define loss function");
                    }

                    listBuilder.layer(new OutputLayer.Builder(
                            mapLoss(layer.getLoss()))
                            .nIn(nIn)
                            .nOut(layer.getSize())
                            .activation(mapActivation(layer.getActivation()))
                            .build());

                    // Final layer output size becomes model output dimension
                    nIn = layer.getSize();
                }
            }
        }

        // Create and initialize the network
        MultiLayerNetwork model = new MultiLayerNetwork(listBuilder.build());
        model.init();

        return model;
    }

    /**
     * Maps custom activation enum to DL4J Activation functions.
     */
    private static Activation mapActivation(ActivationType type) {
        return switch (type) {
            case RELU -> Activation.RELU;
            case SIGMOID -> Activation.SIGMOID;
            case TANH -> Activation.TANH;
            case IDENTITY -> Activation.IDENTITY;
        };
    }

    /**
     * Maps custom loss enum to DL4J loss functions.
     */
    private static LossFunctions.LossFunction mapLoss(LossType type) {
        return switch (type) {
            case MSE -> LossFunctions.LossFunction.MSE;
            case XENT -> LossFunctions.LossFunction.XENT;
        };
    }
}
