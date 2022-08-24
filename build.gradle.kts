import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.*

plugins {
    id("java-library")
    id("idea")
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.springframework.boot.experimental.thin-launcher") version "1.0.28.RELEASE"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.github.johnrengelman.shadow")

    java.sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
    java.targetCompatibility = org.gradle.api.JavaVersion.VERSION_11

    group = "com.blackdiz"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    val springCloudVersion = "2021.0.3"
    val awsLambdaEventsVersion = "3.11.0"
    val awsLambdaCoreVersion = "1.2.1"

    dependencies {
        implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
        implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
        implementation("org.springframework.boot:spring-boot-starter-json")
        implementation("com.amazonaws:aws-lambda-java-events:$awsLambdaEventsVersion")
        implementation("com.amazonaws:aws-lambda-java-core:$awsLambdaCoreVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<ShadowJar> {
        archiveClassifier.set("aws")
        dependencies {
            exclude("org.springframework.cloud:spring-cloud-function-web:3.2.4")
        }
        mergeServiceFiles()
        append("META-INF/spring.handlers")
        append("META-INF/spring.schemas")
        append("META-INF/spring.tooling")
        val pft = PropertiesFileTransformer()
        pft.paths = listOf("META-INF/spring.factories")
        pft.mergeStrategy = "append"
        transform(pft)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    } 
}