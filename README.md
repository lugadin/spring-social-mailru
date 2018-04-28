# spring-social-mailru

Spring social plugin for Mail.Ru

## Introduction

### How to create new version of plugin

Maven repository is located in folder repository. To produce new version, change version in pom.xml, then run command:

    mvn clean deploy
    
You might need to change maven settings: `.m2/settings.xml`:

    <?xml version="1.0" encoding="UTF-8"?>
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
        <servers>
            <server>
                <id>github</id>
                <username>username</username>
                <password>password</password>
            </server>
        </servers>
    </settings>

To create local installation run command:

    mvn clean compile package install:install-file
    -DgroupId=org.springframework.social
    -DartifactId=spring-social-mailru
    -Dversion=1.1.1
    -Dfile=target/spring-social-mailru-1.1.1.jar
    -Dpackaging=jar
    -DgeneratePom=true
    -DlocalRepositoryPath=mvn-repo/spring-social-mailru
    -DcreateChecksum=true

### Maven: how to build and install locally, without uploading to remote repository

    mvn clean install
