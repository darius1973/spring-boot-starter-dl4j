package com.ai.dl4j.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ai.dl4j.service.Dl4jModelService;

@RestController
@RequestMapping("/api/dl4j")
@RequiredArgsConstructor
public class Dl4jController {

    private final Dl4jModelService modelService;

    @GetMapping("/health")
    public String health() {
        return "DL4J service is up";
    }

    @PostMapping("/train")
    public String train() {
        modelService.train();
        return "Training completed";
    }

    @PostMapping("/predict")
    public double[] predict(@RequestBody double[] input) {
        return modelService.predict(input);
    }

    @PostMapping("/save")
    public String save(@RequestParam String name) throws Exception {
        modelService.save("models/" + name);
        return "Model saved as " + name;
    }

    @PostMapping("/load")
    public String load(@RequestParam String name) throws Exception {
        modelService.load("models/" + name);
        return "Model loaded from " + name;
    }
}
