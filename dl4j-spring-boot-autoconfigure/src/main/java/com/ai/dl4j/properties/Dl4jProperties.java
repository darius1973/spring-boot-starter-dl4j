package com.ai.dl4j.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "dl4j")
public class Dl4jProperties {

    /**
     * Number of input features.
     */
    @Min(1)
    private int inputSize = 1;

    /**
     * Number of output features.
     */
    @Min(1)
    private int outputSize = 1;

    /**
     * Random seed for reproducibility.
     */
    private long seed = 123;

    /**
     * Number of epochs.
     */
    @Min(1)
    private int epochs;

    /**
     * Model configuration.
     */
    @Valid
    @NotNull
    private ModelProperties model = new ModelProperties();

}