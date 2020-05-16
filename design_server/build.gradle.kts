plugins {
    java
    kotlin("jvm") version "1.3.70"
}

ext["ktolinVersion"] = "1.3.70"
ext["vertxVersion"] = "4.0.0-milestone4"
ext["junitJupiterEngineVersion"] = "5.4.0"

group = "com.guojm.design_platform"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
    }
}

dependencies {
    kotlinDependencies()
    vertxDependencies()
    loggerDependencies()

    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.3")

    implementation("com.google.inject:guice:4.2.3")

    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
tasks {
    compileJava{
        options.compilerArgs.add("-parameters")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.javaParameters = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.javaParameters = true
    }
}



fun DependencyHandlerScope.kotlinDependencies(){
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
}

fun DependencyHandlerScope.vertxDependencies(){
    implementation("io.vertx:vertx-core:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-lang-kotlin:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-web:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-mysql-client:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-junit5:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-auth-oauth2:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-auth-jwt:${project.ext["vertxVersion"]}")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:${project.ext["vertxVersion"]}")

    testImplementation("io.vertx:vertx-junit5:${project.ext["vertxVersion"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${project.ext["junitJupiterEngineVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${project.ext["junitJupiterEngineVersion"]}")
}

fun DependencyHandlerScope.loggerDependencies(){
    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
    implementation("ch.qos.logback:logback-core:1.3.0-alpha5")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")
}
