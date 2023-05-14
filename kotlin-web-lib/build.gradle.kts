plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    `java-library`
    `maven-publish`
}

java.sourceCompatibility = JavaVersion.VERSION_17
var artifactVersion : String? = "${version}-SNAPSHOT"
var artifactoryURL : String? = "http://localhost:8046/artifactory"

project.ext{
    val branchName: String? = System.getenv("GIT_BRANCH")
    if("main" == branchName){
        artifactVersion = "$version"
    }
    println ("Branch Name :[$branchName]")
    println ("Version  :[$artifactVersion]")
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
    repositories {
        maven {
            isAllowInsecureProtocol = true
            credentials{
                username = System.getenv("MAVEN_CRED_USR")
                password = System.getenv("MAVEN_CRED_PSW")
            }
            url = if(artifactVersion!!.endsWith ("-SNAPSHOT")){
                uri("${artifactoryURL}/libs-snapshot-local")
            }else{
                uri("${artifactoryURL}/libs-release-local")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "$group"
            artifactId = "kotlin-web-lib"
            version = "$artifactVersion"
            from(components["java"])
        }
    }
}
