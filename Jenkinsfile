pipeline{

    agent any

    environment {
        MAVEN_CRED = credentials('maven_credentials')
        GIT_CREDENTIAL_ID = "git_credentials_id"
    }

    stages {
        stage('kotlin-mongo-lib'){
            when {changeset "kotlin-mongo-lib/gradle.properties"}
            steps{
                bat './gradlew kotlin-mongo-lib:clean kotlin-mongo-lib:build kotlin-mongo-lib:publish'
            }
        }
        stage('kotlin-web-lib'){
            when {changeset "kotlin-web-lib/gradle.properties"}
            steps{
                bat './gradlew kotlin-web-lib:clean kotlin-web-lib:build kotlin-web-lib:publish'
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
