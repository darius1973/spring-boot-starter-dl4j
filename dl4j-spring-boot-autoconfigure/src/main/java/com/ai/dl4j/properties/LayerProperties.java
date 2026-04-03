package com.ai.dl4j.properties;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Configuration for a single neural network layer.
 *
 * <p>
 * Each layer defines its type, size, activation function,
 * and optionally a loss function (for output layers).
 */
@Data
public class LayerProperties {

    /**
     * Type of the layer.
     *
     * <p>
     * Supported values:
     * <ul>
     *     <li>DENSE - fully connected layer</li>
     *     <li>OUTPUT - output layer (must be last)</li>
     * </ul>
     */
    @NotNull
    private LayerType type;

    /**
     * Number of neurons.
     */
    @Min(1)
    private int size;

    /**
     * Activation function.
     */
    private ActivationType activation = ActivationType.RELU;

    /**
     * Loss function (only required for output layer).
     */
    private LossType loss;
}
