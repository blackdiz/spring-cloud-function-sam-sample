plugins {
    id("java")    
}
dependencies { 
    implementation(project(":common"))
    
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes(mapOf("Main-Class" to "com.blackdiz.demo.FunctionConfiguration"))
    }
}