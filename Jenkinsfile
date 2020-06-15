node{
   stage('SCM Checkout'){
       git credentialsId: 'git-creds', url: 'https://github.com/deva9017/Service-java.git'
   }
   stage('gradle Package'){
     def gradleHome = tool name: 'gradle', type: 'gradle'
     def gradleCMD = "${gradleHome}/bin/gradle"
     sh "${gradleCMD} clean build"
   } 
   stage('ansible installing'){
   sh label: '', script: '''#!/bin/bash
   echo this script done by shiva
   echo Installing software-properties-common packages, then add the java OpenJDK PPA repository.
   sudo apt install software-properties-common apt-transport-https -y
   sudo add-apt-repository ppa:openjdk-r/ppa -y
   Now installing the Java 8 using apt command.
   sudo apt install openjdk-8-jdk -y
   echo java version installed on the system.
   java -version
   echo updating and upgrading the version
   sudo apt-get update -y
   sudo apt-get upgrade -y
   echo Ansible PPA to your server
   sudo apt-add-repository ppa:ansible/ansible \' \'
   echo update the repository and install Ansible
   sudo apt-get update -y
   sudo apt-get install ansible -y
   echo Ansible version
   sudo ansible --version'''
   }
   stage('permissions to dir'){
   sh label: '', script: '''sudo chmod 777 /usr/
   sudo chmod 777 /usr/local/
   sudo chmod 777 /usr/local/bin/'''
   }
   stage('Creating bash script to run jar'){   
     sh label: '', script: '''cd /usr/local/bin
cat >vedikaservice.sh <<'EOF'
SERVICE_NAME=vedikaservice
PATH_TO_JAR=/home/ubuntu/functionhall-service-0.0.1-SNAPSHOT.jar
PID_PATH_NAME=/tmp/vedikaservice-pid 
case $1 in 
start)
       echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then 
       nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >>/dev/null &      
                   echo $! > $PID_PATH_NAME  
       echo "$SERVICE_NAME started ..."         
  else 
       echo "$SERVICE_NAME is already running ..."
  fi
;;
stop)
  if [ -f $PID_PATH_NAME ]; then
         PID=$(cat $PID_PATH_NAME);
         echo "$SERVICE_NAME stoping ..." 
         kill $PID;         
         echo "$SERVICE_NAME stopped ..." 
         rm $PID_PATH_NAME       
  else          
         echo "$SERVICE_NAME is not running ..."   
  fi    
;;    
restart)  
  if [ -f $PID_PATH_NAME ]; then 
      PID=$(cat $PID_PATH_NAME);    
      echo "$SERVICE_NAME stopping ..."; 
      kill $PID;           
      echo "$SERVICE_NAME stopped ...";  
      rm $PID_PATH_NAME     
      echo "$SERVICE_NAME starting ..."  
      nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &            
      echo $! > $PID_PATH_NAME  
      echo "$SERVICE_NAME started ..."    
  else           
      echo "$SERVICE_NAME is not running ..."    
     fi     ;;
  esac'''   
      }
      stage('permissions to dir systemd init'){
      sh label: '', script: '''sudo chmod 777 /etc/systemd/
      sudo chmod 777 /etc/systemd/system/'''
      }
    stage('Creating jar as a service'){ 
sh label: '', script: '''cd /etc/systemd/system/
cat >vedikaservice.service <<'EOF'
[Unit]
 Description = Java Service
 After network.target = Service_Name.service
[Service]
 Type = forking
 Restart=always
 RestartSec=1
 SuccessExitStatus=143 
 ExecStart = /usr/local/bin/vedikaservice.sh start
 ExecStop = /usr/local/bin/vedikaservice.sh stop
 ExecReload = /usr/local/bin/vedikaservice.sh reload
[Install]
 WantedBy=multi-user.target'''
       }
    stage('execute permission to script'){
	sh label: '', script: 'sudo chmod +x /usr/local/bin/vedikaservice.sh'
	}
	
    stage('Service to enable and reload'){
	sh label: '', script: '''sudo systemctl daemon-reload
        sudo systemctl enable vedikaservice'''
	}
	stage('permissions opt'){
	sh label: '', script: 'sudo chmod 777 /opt'
	}
	stage('playbook creation'){
	sh label: '', script: '''cd /opt/
sudo echo "---
-
  hosts: 13.235.244.132
  tasks:
    -
      apt:
        update_cache: true
      become: true
      name: "Update APT package manager repositories cache"
    -
      apt:
        state: present
      become: true
      name: "Install OpenJDK Java"
      with_items: openjdk-8-jdk  
    -
      copy:
        src:  /var/lib/jenkins/workspace/Servicefinal/build/libs/functionhall-service-0.0.1-SNAPSHOT.jar
        dest: /home/ubuntu/
    -
      copy:
        src: /usr/local/bin/vedikaservice.sh
        dest: /usr/local/bin/
    -
      copy:
        src: /etc/systemd/system/vedikaservice.service
        dest: /etc/systemd/system/
    -
       shell: sudo chmod +x /usr/local/bin/vedikaservice.sh   
    - 
       shell: sudo apt install openjdk-11-jre-headless
    -
       shell: sudo systemctl daemon-reload  
    -
       shell: sudo systemctl enable vedikaservice 
    -
       shell: sudo systemctl start vedikaservice" > service.yaml'''
}
       sh label: '', script: '''cd /opt
       sudo ansible-playbook service.yaml'''
       
}
