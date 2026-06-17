plugins {
    id("java")
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "2.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // Readers
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    //Trabajar con DocumentReader, PdfDocumentReader
    implementation("org.springframework.ai:spring-ai-tika-document-reader")

    //Embeddings
    implementation("org.springframework.ai:spring-ai-starter-model-transformers")

    //Vector Store
    implementation("org.springframework.ai:spring-ai-starter-vector-store-qdrant")

    //EIPs
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.20.0")
    implementation("org.apache.camel.springboot:camel-file-starter:4.20.0")

    //Docker compose
    implementation("org.springframework.boot:spring-boot-docker-compose")

    // Source: https://mvnrepository.com/artifact/com.microsoft.onnxruntime/onnxruntime_gpu
    //implementation("com.microsoft.onnxruntime:onnxruntime_gpu:1.19.+")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}