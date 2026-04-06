package com.ai.dl4j.properties;

/**
 * Supported neural network layer types used in model configuration.
 *
 * Each type represents a different kind of neural network layer that can be
 * used when dynamically building a model (e.g., DL4J or similar frameworks).
 */
public enum LayerType {

    /**
     * Fully connected (dense) layer where each neuron is connected to all outputs
     * of the previous layer.
     */
    DENSE,

    /**
     * Output layer used for producing final predictions.
     * Typically includes a loss function and activation suitable for the task.
     */
    OUTPUT,

    /**
     * Convolutional layer used primarily for image or spatial data processing.
     */
    CONVOLUTION,

    /**
     * Long Short-Term Memory (LSTM) recurrent layer for sequence modeling.
     */
    LSTM,

    /**
     * Dropout layer used for regularization by randomly disabling neurons during training.
     */
    DROPOUT,

    /**
     * Batch normalization layer used to stabilize and accelerate training by normalizing activations.
     */
    BATCH_NORM
}
