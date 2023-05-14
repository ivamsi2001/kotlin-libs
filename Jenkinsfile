import com

pipeline{

    agent {label 'docker-gradle-slave'}

    environment {
        MAVEN_CRED = credentials('maven_credentials')
        GIT_CREDENTIAL_ID = "git_credentials_id"
    }

    stages {

        stage('kotlin-mongo-lib'){
            when {changeset "kotlin-mongo-lib/gradle.properties"}
            steps{
                sh './gradlew kotlin-mongo-lib clean build'
                sh './gradlew kotlin-mongo-lib publish --stacktrace'
            }
        }

        stage('kotlin-web-lib'){
            when {changeset "kotlin-web-lib/gradle.properties"}
            steps{
                sh './gradlew kotlin-web-lib clean build'
                sh './gradlew kotlin-web-lib publish --stacktrace'
            }
        }

        stage('Git Tag'){
            when {branch "main"}
            steps{
                def config = readProperties file: 'gradle.properties'
                echo "Git Tag Version : ${config.tagVersion}"
            }
        }
    }
}
