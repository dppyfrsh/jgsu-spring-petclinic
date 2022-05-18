pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/dppyfrsh/jgsu-spring-petclinic.git'
              }
            }
        stage('Build') {
            steps {
                powershell '.\\mvnw.cmd clean package'
            }

            post {
              always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
