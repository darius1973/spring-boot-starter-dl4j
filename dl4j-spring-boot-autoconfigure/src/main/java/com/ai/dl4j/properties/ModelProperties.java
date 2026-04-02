package com.ai.dl4j.properties;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ModelProperties {

    /**
     * List of layers defining the neural network.
     */
    @Valid
    @NotEmpty
    private List<LayerProperties> layers = new ArrayList<>();
}
