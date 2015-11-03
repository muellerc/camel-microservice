# INSTALLATION

## Java
Download Java 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
Execute the installer

## Maven
'brew install maven31'

## Wildfly Application server
# donwload Wildfly
Download Wildfly from http://wildfly.org/downloads/
Untar the archive

# configure Wildfly
Goto ${WILDFLY_HOME}/standalone/configuration and open standalone.xml:
Goto server/profile/subsystem[@xmlns="urn:jboss:domain:undertow:1.2"]/server and add the following configuration entry:
<https-listener name="https" socket-binding="https" security-realm="UndertowRealm" enabled-cipher-suites="TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_256_CBC_SHA256,TLS_DH_RSA_WITH_AES_128_CBC_SHA256,TLS_DH_RSA_WITH_AES_256_CBC_SHA256,TLS_DH_DSS_WITH_AES_128_CBC_SHA256,TLS_DH_DSS_WITH_AES_256_CBC_SHA256,TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_256_CBC_SHA256,TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384,TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384" enabled-protocols="TLSv1.2"/>

# start Wildfly
Goto ${WILDFLY_HOME}/bin
Execute './standalone.sh'

## Application
Goto ${camel-microservice_HOME}
Execute 'mvn clean install -Pdeploy,it'

# CREATE A NEW COMMENT
curl -i -X POST -H "Content-Type: application/json" -d '{"text":"this is my first comment"}' http://localhost:8080/camel-microservice/v1/comments


# GET ALL COMMENTS
curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/camel-microservice/v1/comments


# UPDATE AN EXISTING COMMENT
curl -i -X PUT -H "Content-Type: application/json" -d '{"text":"this is my third comment"}' http://localhost:8080/camel-microservice/v1/comments/cbc56365-0bdb-40a9-9729-1283c89d9ac3


# GET COMMENT WITH ID {ID}
curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/camel-microservice/v1/comments/d1466ebe-9d2b-4bba-afdb-9199a436d34e


# DELETE A COMMENT BY ID {ID}
curl -i -X DELETE -H "Content-Type: application/json" http://localhost:8080/camel-microservice/v1/comments/d1466ebe-9d2b-4bba-afdb-9199a436d34e