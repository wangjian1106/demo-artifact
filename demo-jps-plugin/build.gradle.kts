fun parentProp(key: String) = project.parent?.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.16.1"
}

group = parentProp("pluginGroup")
version = parentProp("pluginVersion")

repositories {
    maven("https://www.jetbrains.com/intellij-repository/releases")
    maven("https://www.jetbrains.com/intellij-repository/snapshots")
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

intellij {
    //pluginName.set(prop("pluginName"))
    version.set(parentProp("platformVersion"))
    type.set(parentProp("platformType"))
    plugins.set(parentProp("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(parentProp("jdkVersion")))
    }
}

tasks {
    // Set the JVM compatibility versions
    compileJava {
        options.release.set(parentProp("compatibleJdkVersion").toInt())
    }

    buildSearchableOptions {
        enabled = false
    }

    verifyPlugin {
        enabled = false
    }
}

tasks.test {
    useJUnitPlatform()
}