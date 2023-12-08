# API tests

Java, rest assured, AssertJ, Junit 5, Docker, Jenkins, Allure Report. add beutiful icons for used technologies 

## Description
This is my pet project to reaserch and lear how to work with ci/cd. Here are several simple tests that testing [Reqres API](https://reqres.in). 
They make basic HTTP requests : POST, GET, PUT, DELETE and check response(status code, content type, body, etc). 
These tests can be run in Jenkins pipeline. At the end of pipeline, a test report will be generated.

## Project's structure
The project has a simple structure : model classes and several basic tests

  ![Screenshot of a class structure](/assets/images/structureScreenshot.png)

The test code is written in Java using REST Assured, AssertJ, Junit 5 and Allure Report libraries

  ![Screenshot of a test code](/assets/images/codeScreenshot.png)

## Running tests 
Tests can be run both localy also in Jenkins. 

![Screenshot of a jenkinsfile](/assets/images/jenkinsPipelineScreenshot.png)

Jenkinsfile with pipeline settings is contained in repository

![Screenshot of a jenkinsfile](/assets/images/jenkinsfileScreenshot.png)

## Test report
Allure Report is used to generate reports.

![Screenshot of a Allure Report Overview ](/assets/images/allureOverviewScreenshot.png)

![Screenshot of a Allure Report Overview ](/assets/images/allureBehaviorsScreenshot.png)

![Screenshot of a Allure Report Overview ](/assets/images/allureCategoriesScreenshot.png)

