# DL4J Spring Boot Starter

A Spring Boot starter that allows you to define and run neural networks using **YAML configuration**, powered by Deeplearning4j.

---

## ✨ Features

* YAML-driven neural network definition
* Automatic DL4J model creation
* Training and prediction API
* Model persistence (save/load)
* Optional REST API
* Spring Boot auto-configuration

## 📘 Full API Documentation: https://darius1973.github.io/spring-boot-starter-dl4j
---

## 📦 Installation

```xml
<dependency>
    <groupId>io.github.darius1973</groupId>
    <artifactId>spring-boot-starter-dl4j</artifactId>
    <version>0.1.4-BETA</version>
</dependency>
```

---

## ⚙️ Configuration

Define your neural network in `application.yml`:

```yaml
dl4j:
  input-size: 3
  seed: 123
  model:
    layers:
      - type: dense
        size: 16
        activation: relu
      - type: dense
        size: 8
        activation: relu
      - type: output
        size: 1
        activation: sigmoid
        loss: mse
```

---

## 🚀 Usage

The model is automatically configured at startup.

### Train the model

```bash
curl -X POST http://localhost:8080/api/dl4j/train
```

---

### Predict

```bash
curl -X POST http://localhost:8080/api/dl4j/predict \
     -H "Content-Type: application/json" \
     -d '[1.0, 2.0, 3.0]'
```

---

### Save model

```bash
curl -X POST "http://localhost:8080/api/dl4j/save?name=model.zip"
```

---

### Load model

```bash
curl -X POST "http://localhost:8080/api/dl4j/load?name=model.zip"
```

---

## 🧠 Supported Layers

* Dense
* Output

---

## 🔧 Supported Activations

* RELU
* SIGMOID
* TANH
* IDENTITY

---

## 📉 Supported Loss Functions

* MSE
* XENT

---

## ⚠️ Validation Rules

* Exactly one OUTPUT layer is required
* OUTPUT layer must be last
* OUTPUT layer must define a loss function

---

## 🧪 Notes

* Default training uses generated data (for demo purposes)
* Model persistence is local (stored in `models/` directory)

---

## 🔮 Roadmap

* CNN / LSTM support
* Dataset ingestion (CSV, JSON)
* Async training
* Model registry

---

## 📄 License

MIT
