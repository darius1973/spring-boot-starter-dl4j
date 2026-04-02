package com.ai.dl4j.config;


import com.ai.dl4j.properties.Dl4jProperties;
import com.ai.dl4j.service.Dl4jModelService;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(NeuralNetConfiguration.class)
@EnableConfigurationProperties(Dl4jProperties.class)
public class Dl4jAutoConfiguration {

    //Create the model
    @Bean
    @ConditionalOnMissingBean
    public MultiLayerNetwork multiLayerNetwork(Dl4jProperties properties) {

        // Default fallback model (simple MLP)
        var conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(properties.getInputSize())
                        .nOut(16)
                        .activation(Activation.RELU)
                        .build())

                .layer(new DenseLayer.Builder()
                        .nIn(16)
                        .nOut(8)
                        .activation(Activation.RELU)
                        .build())

                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nIn(8)
                        .nOut(properties.getOutputSize())
                        .activation(Activation.IDENTITY)
                        .build())

                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        return model;
    }

    // ⚙️ Create the service (inject model!)
    @Bean
    public Dl4jModelService dl4jModelService(
            Dl4jProperties properties,
            MultiLayerNetwork model
    ) {
        return new Dl4jModelService(properties, model);
    }
}
