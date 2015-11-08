# INSTALLATION

## Java
- Download Java 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
- Execute the installer

## Maven
- Download Maven 3 from https://maven.apache.org/download.cgi
- Unzip/Untar the archive
- Set the environment variable M2_HOME to the directory where you have unzip/untar the archive
- Add M2_HOME/bin to your PATH variable

# EXECUTION
## Running the Arquillian integration test
- Goto ${camel-microservice_HOME}
- Execute 'mvn clean install -Pit'

## Running it in Wildfly Application server
# donwload Wildfly
- Download Wildfly from http://wildfly.org/downloads/
- Unzip/Untar the archive

# start Wildfly
- Goto ${WILDFLY_HOME}/bin
- Execute './standalone.sh'

## Application
- Goto ${camel-microservice_HOME}
- Execute 'mvn clean install -Pdeploy'

## Test the deployment
### CREATE A NEW COMMENT
curl -i -X POST -H "Content-Type: application/json" -H "Accept: application/json" -d '{"text":"this is my first comment"}' http://localhost:8080/camel-microservice/v1/comments


### GET ALL COMMENTS
curl -i -X GET -H "Accept: application/json" http://localhost:8080/camel-microservice/v1/comments


### UPDATE AN EXISTING COMMENT
curl -i -X PUT -H "Content-Type: application/json" -H "Accept: application/json" -d '{"text":"this is my third comment"}' http://localhost:8080/camel-microservice/v1/comments/cbc56365-0bdb-40a9-9729-1283c89d9ac3


### GET COMMENT WITH ID {ID}
curl -i -X GET -H "Accept: application/json" http://localhost:8080/camel-microservice/v1/comments/d1466ebe-9d2b-4bba-afdb-9199a436d34e


### DELETE A COMMENT BY ID {ID}
curl -i -X DELETE http://localhost:8080/camel-microservice/v1/comments/d1466ebe-9d2b-4bba-afdb-9199a436d34e