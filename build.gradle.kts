import java.net.URI

plugins {
    id("java")
    id("signing")
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
    repositories {
        maven {
            name = "sonatype-releases"
            credentials {
                username = project.property("ghusr").toString()
                password = project.property("ghpwd").toString()
            }

            url = URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
    publications {
        create<MavenPublication>("annotations") {
            artifact("build/libs/annotations-1.0.0-javadoc.jar") {
                classifier = "javadoc"
                extension = "jar"
            }
            artifact("build/libs/annotations-1.0.0-sources.jar") {
                classifier = "sources"
                extension = "jar"
            }
            groupId = "${project.property("project.group")}"
            artifactId = rootProject.name
            version = "${project.property("project.version")}"

            from(components["java"])

            pom {
                name.set("annotations")
                packaging = "jar"
                description.set("Useful annotations.")
                url.set("https://github.com/MecanicalDragon/annotations")

                licenses {
                    license {
                        name.value("The Apache License, Version 2.0")
                        url.value("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.value("scm:git:git://github.com/MecanicalDragon/annotations.git")
                    developerConnection.value("scm:git:git://github.com/MecanicalDragon/annotations.git")
                    url.value("https://github.com/MecanicalDragon/annotations/tree/main")
                }

                developers {
                    developer {
                        id.value("madrag")
                        name.value("Stanislav Tretiakov")
                        email.value("medrag13@gmail.com")
                    }
                }
            }
        }
    }
}

tasks.create<Jar>("javadocJar"){
    archiveClassifier.value("javadoc")
    from("build/docs/javadoc")
    dependsOn("javadoc")
}

tasks.create<Jar>("sourcesJar"){
    archiveClassifier.value("sources")
    from(sourceSets.main.get().allSource)
}

tasks.create("preparePublishing"){
    group = "publishing"
    dependsOn("javadocJar", "sourcesJar", "jar")
}

artifacts {
    archives(tasks.getByName("jar"))
    archives(tasks.getByName("javadocJar"))
    archives(tasks.getByName("sourcesJar"))
}

signing {
    sign(publishing.publications)
}