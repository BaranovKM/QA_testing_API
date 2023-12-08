# API tests

Java, rest assured, AssertJ, Junit 5, Docker, Jenkins, Allure Report. add beutiful icons for used technologies 

## Description
This is my pet project to reaserch and lear how to work with ci/cd. Here are several simple tests that testing [Reqres API](https://reqres.in). 
They make basic HTTP requests : POST, GET, PUT, DELETE and check response(status code, content type, body, etc). 
These tests can be run in Jenkins pipeline. At the end of pipeline, a test report will be generated.

## Project's structure
The project has a simple structure : model classes and several basic tests

  insert screenshot of packages structure(make refactoring)

The test code is written in Java using REST Assured, AssertJ, Junit 5 and Allure Report libraries

  insert screenshot of code
  ![Screenshot of a test code](/assets/images/codeScreenshot.png)

## Tests runs 
Tests runs on jenkins node. Jenkinsfile with pipeline settings is contains in repository

insert screenshot of jenkinsfile

insert screenshot of pipeline

## Tests report
For report generation used Allure Report

insert screenshots of report

