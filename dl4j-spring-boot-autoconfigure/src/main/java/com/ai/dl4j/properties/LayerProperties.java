package com.ai.dl4j.properties;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LayerProperties {

    /**
     * Type of the layer (dense, output).
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
