FROM tomcat:10.0.27-jdk17-corretto
COPY authentication-api-docker.war /usr/local/tomcat/webapps/ROOT.war
