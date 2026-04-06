package com.ai.dl4j.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ai.dl4j.service.Dl4jModelService;

/**
 * REST controller exposing endpoints for interacting with a DL4J (DeepLearning4J) model service.
 *
 * <p>This controller provides basic operations such as:
 * <ul>
 *   <li>Health check</li>
 *   <li>Model training</li>
 *   <li>Prediction</li>
 *   <li>Saving and loading trained models</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/dl4j")
@RequiredArgsConstructor
public class Dl4jController {

    /**
     * Service layer responsible for model training, inference, and persistence.
     */
    private final Dl4jModelService modelService;

    /**
     * Simple health check endpoint to verify that the DL4J service is running.
     *
     * @return a status message indicating the service is up
     */
    @GetMapping("/health")
    public String health() {
        return "DL4J service is up";
    }

    /**
     * Triggers model training using the underlying service.
     *
     * <p>This is a synchronous call and will block until training is completed.</p>
     *
     * @return confirmation message after training finishes
     */
    @PostMapping("/train")
    public String train() {
        modelService.train();
        return "Training completed";
    }

    /**
     * Runs inference on the trained model using the provided input vector.
     *
     * @param input input features as a double array
     * @return prediction output as a double array
     */
    @PostMapping("/predict")
    public double[] predict(@RequestBody double[] input) {
        return modelService.predict(input);
    }

    /**
     * Saves the current trained model to disk.
     *
     * @param name name of the model file (stored under "models/" directory)
     * @return confirmation message with saved model name
     * @throws Exception if saving fails due to I/O or serialization issues
     */
    @PostMapping("/save")
    public String save(@RequestParam String name) throws Exception {
        modelService.save("models/" + name);
        return "Model saved as " + name;
    }

    /**
     * Loads a previously saved model from disk.
     *
     * @param name name of the model file located in "models/" directory
     * @return confirmation message with loaded model name
     * @throws Exception if loading fails due to missing file or deserialization issues
     */
    @PostMapping("/load")
    public String load(@RequestParam String name) throws Exception {
        modelService.load("models/" + name);
        return "Model loaded from " + name;
    }
}
