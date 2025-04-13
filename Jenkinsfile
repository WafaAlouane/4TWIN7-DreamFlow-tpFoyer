pipeline {
    agent any  
    tools {
        maven 'M2_HOME'  
        jdk 'JDK21'  
    }

    environment {

        SONARQUBE = 'SonarQube'

NEXUS_REPO = 'http://192.168.150.245:8081/repository/maven-releases/'
    
    }

    stages {
        stage('GIT') {
            steps {
echo "Getting Project from Git"
                git url: 'https://github.com/Warda127/devops.git', branch: 'feature/warda'

                            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean' 
            }
        }

        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile' 
            }
        }

        stage('JUnit') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
                steps {
                    echo "Starting SonarQube analysis..."
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH_TOKEN')]) {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn sonar:sonar -Dsonar.projectKey=devops -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${SONAR_AUTH_TOKEN}'
                        }
                    }
                }
            }

stage('Integration Tests') {
                    steps {
                        sh 'mvn install -DskipTests'
                    }
                }
 stage(' Docker Image') {
    steps {
        script {
            echo " Building Docker image..."
            dockerImage = docker.build("espritt/khaddem:1.0.0")
        }
    }
}



        stage('Push Docker Image') {
    steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                // Utilisation sécurisée du mot de passe avec echo dans un fichier temporaire
                sh """
                    echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                """
                sh "docker push espritt/khaddem:1.0.0"
            }
        }
    }
}

stage('List Files') {
    steps {
        script {
            // Liste les fichiers dans le répertoire du projet
            sh 'ls -la'
        }
    }
}

stage('Check Docker') {
    steps {
        sh 'docker --version'
        sh 'docker-compose --version'
    }
}

    stage('Deploy with Docker Compose') {
    steps {
        script {
            echo "Deploying with Docker Compose..."
            sh 'docker compose -f /mnt/c/Users/LENOVO/Desktop/kaddem/docker-compose.yml up -d --build'
        }
    }
}
 stage('Prometheus and Grafana Check') {
            steps {
                script {
                    // Vérifier si Prometheus est en fonctionnement
                    echo "Checking Prometheus status..."
                    sh 'curl -s -o /dev/null -w "%{http_code}" http://localhost:9090/metrics'

                    // Vérifier si Grafana est en fonctionnement
                    echo "Checking Grafana status..."
                    sh 'curl -s -o /dev/null -w "%{http_code}" http://localhost:3000'
                }
            }
        }

    }

    post {
        success {
            echo 'Build et analyse SonarQube réussis !'  // Message si le pipeline réussit
        }
        failure {
            echo 'Le build ou l\'analyse SonarQube a échoué.'  // Message si le pipeline échoue
        }
    }
}
