package com.ai.dl4j.properties;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the neural network structure.
 *
 * <p>
 * The model is defined as a sequence of layers executed in order.
 */
@Data
public class ModelProperties {

    /**
     * List of layers composing the neural network.
     *
     * <p>
     * Must contain at least one layer and exactly one OUTPUT layer.
     */
    @Valid
    @NotEmpty
    private List<LayerProperties> layers = new ArrayList<>();
}
