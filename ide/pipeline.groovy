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
                powershell '.\\mvnw.cmd clean compile'
            }

            post {
              always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
            //changed {
                emailext attachLog: true, body: 'Test Body', compressLog: true,
                recipientProviders: [upstreamDevelopers(), requestor()],
                subject: 'Test Subject', to: "test@jenkins"
            //}
        }
    }
}
