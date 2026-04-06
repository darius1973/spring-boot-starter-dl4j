package com.dl4j.demo;

import com.ai.dl4j.service.Dl4jModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/dl4j")
@RequiredArgsConstructor
public class DemoController {

    private final Dl4jModelService modelService;

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public String health() {
        return "DL4J Demo is running";
    }

    /**
     * Triggers model training.
     */
    @PostMapping("/train")
    public String train() {
        modelService.train();
        return "Training completed";
    }

    /**
     * Performs prediction.
     */
    @PostMapping("/predict")
    public double[] predict(@RequestBody double[] input) {
        return modelService.predict(input);
    }

    /**
     * Saves model to disk.
     */
    @PostMapping("/save")
    public String save(@RequestParam String name) throws Exception {
        modelService.save("models/" + name);
        return "Model saved as " + name;
    }

    /**
     * Loads model from disk.
     */
    @PostMapping("/load")
    public String load(@RequestParam String name) throws Exception {
        modelService.load("models/" + name);
        return "Model loaded from " + name;
    }
}
