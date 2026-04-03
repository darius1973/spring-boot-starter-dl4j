package com.ai.dl4j.config;


import com.ai.dl4j.factory.Dl4jModelFactory;
import com.ai.dl4j.properties.Dl4jProperties;
import com.ai.dl4j.service.Dl4jModelService;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(NeuralNetConfiguration.class)
@AutoConfiguration
@EnableConfigurationProperties(Dl4jProperties.class)
public class Dl4jAutoConfiguration {

    /**
     * Creates the DL4J {@link MultiLayerNetwork} based on configuration properties.
     *
     * <p>
     * This is the default model used by the starter. It is built from the YAML-defined
     * layer structure (dl4j.model.layers).
     *
     * <p>
     * If a user defines their own {@link MultiLayerNetwork} bean, this one will be skipped.
     *
     * @param properties configuration bound from application.yml
     * @return initialized neural network
     */
    @Bean
    @ConditionalOnMissingBean
    public MultiLayerNetwork multiLayerNetwork(Dl4jProperties properties) {
        return Dl4jModelFactory.build(properties);
    }

    /**
     * Provides the main service for interacting with the DL4J model.
     *
     * <p>
     * This service exposes:
     * <ul>
     *     <li>Training</li>
     *     <li>Prediction</li>
     *     <li>Model persistence (save/load)</li>
     * </ul>
     *
     * @param properties configuration properties
     * @param model the neural network instance
     * @return DL4J model service
     */
    @Bean
    @ConditionalOnMissingBean
    public Dl4jModelService dl4jModelService(
            Dl4jProperties properties,
            MultiLayerNetwork model
    ) {
        return new Dl4jModelService(properties, model);
    }
}
