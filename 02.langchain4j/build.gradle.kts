plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

val langchain4jVersion = "1.14.0"

dependencies {
    implementation("dev.langchain4j:langchain4j:${langchain4jVersion}")
    implementation("dev.langchain4j:langchain4j-open-ai:${langchain4jVersion}")
}

tasks.test {
    useJUnitPlatform()
}