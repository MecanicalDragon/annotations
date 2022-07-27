plugins {
    id("java")
    id("maven-publish")
}

group = "${project.property("project.group")}"
version = "${project.property("project.version")}"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    annotationProcessor("com.google.auto.service:auto-service:${project.property("google-autoservice.version")}")
    compileOnly("com.google.auto.service:auto-service:${project.property("google-autoservice.version")}")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.property("project.group")}"
            artifactId = rootProject.name
            version = "${project.property("project.version")}"

            from(components["java"])

            pom {
                name.set("annotations")
                description.set("useful annotations")
            }
        }
    }
}