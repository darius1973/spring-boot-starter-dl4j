package com.ai.dl4j.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ai.dl4j.service.Dl4jModelService;

@RestController
@RequestMapping("/api/dl4j")
@RequiredArgsConstructor
public class Dl4jController {

    private final Dl4jModelService modelService;

    @PostMapping("/train")
    public String train() {
        modelService.train();
        return "Model training started";
    }

    @PostMapping("/predict")
    public double[] predict(@RequestBody double[] input) {
        return modelService.predict(input);
    }

    @GetMapping("/health")
    public String health() {
        return "DL4J service is up";
    }
}
