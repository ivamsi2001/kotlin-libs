plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    `java-library`
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_17
var artifactVersion : String? = "${version}-SNAPSHOT"
var artifactoryURL : String? = ""

project.ext{
    val branchName: String? = System.getenv("GIT_BRANCH")
    if("main".equals(branchName)){
        artifactVersion = "${version}"
    }
    println ("Branch Name :["+branchName+"]")
    println ("Version  :["+artifactVersion+"]")
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:31.1-jre")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

java{
    withSourcesJar()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${group}"
            artifactId = "kotlin-web-lib"
            version = "${artifactVersion}"
            from(components["java"])
        }
    }
    repositories {
        maven {
            if(artifactVersion!!.endsWith ("-SNAPSHOT")){
                url = uri("${artifactoryURL}/libs-snapshot")
            }else{
                url = uri("${artifactoryURL}/releases")
            }
            credentials{
                username = System.getenv("MAVEN_CRED_USR")
                password = System.getenv("MAVEN_CRED_PSW")
            }
        }
    }
}