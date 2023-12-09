# API tests

Java, Maven, rest assured, AssertJ, Junit 5, Docker, Jenkins, Allure Report.
![Static Badge](https://img.shields.io/badge/Java-17-red?style=plastic)
![Static Badge](https://img.shields.io/badge/Maven-3.9.1-blue?style=plastic)
![Static Badge](https://img.shields.io/badge/REST_Assured-5.3.1-green?style=plastic)
![Static Badge](https://img.shields.io/badge/Junit-5.9.2-orange?style=plastic)
![Static Badge](https://img.shields.io/badge/Lombok-1.18.28-brown?style=plastic)
![Static Badge](https://img.shields.io/badge/Jackson-2.9.5-violet?style=plastic)
![Static Badge](https://img.shields.io/badge/AssertJ-3.24.2-gold?style=plastic)
![Static Badge](https://img.shields.io/badge/Allure_Report-2.24-yellow?style=plastic)
![Static Badge](https://img.shields.io/badge/Jenkins-2.414-purple?style=plastic)


## Description

This is my pet project to reaserch and lear how to work with ci/cd. Here are several simple tests that testing [Reqres API](https://reqres.in). 
They make basic HTTP requests : POST, GET, PUT, DELETE and check response(status code, content type, body, etc). 
These tests can be run in Jenkins pipeline. At the end of pipeline, a test report will be generated.

## Project's structure

The project has a simple structure : model classes and several basic tests

  ![Screenshot of a class structure](/assets/images/structureScreenshot.png)

The test code is written in Java using REST Assured, AssertJ, Junit 5 and Allure Report libraries

Maven was used to build project

  ![Screenshot of a test code](/assets/images/codeScreenshot.png)

## Running tests

Tests can be run both localy also in Jenkins. 

![Screenshot of a jenkinsfile](/assets/images/jenkinsPipelineScreenshot.png)

For simplicity, pipeline consists of only one stage

Jenkinsfile with pipeline settings is contained in repository

![Screenshot of a jenkinsfile](/assets/images/jenkinsfileScreenshot.png)

## Test report

Allure Report is used to generate reports.

![Screenshot of a Allure Report Overview ](/assets/images/allureOverviewScreenshot.png)

Tests are divided into separate steps and annotated about owner, severity, tags, and etc. 

Also tests can be linked to bugtracker or/and task management system and include attachments with request/response

![Screenshot of a Allure Report Overview ](/assets/images/allureBehaviorsScreenshot.png)

In case of error, test is marked as failed

The step at which test failed is displayed with stacktrace

![Screenshot of a Allure Report Overview ](/assets/images/allureCategoriesScreenshot.png)

