# API tests

java, rest assured, assertJ, junit5, docker, jenkins, Allure report. add beutiful icons for used technologies 

## Description
This is my pet project to reaserch and lear how to work with ci/cd. Here are several simple tests that testing [Reqres API](https://reqres.in). 
They make basic HTTP requests : POST, GET, PUT, DELETE and check response(status code, content type, body, etc). 
These tests can be run in Jenkins pipeline. At the end of pipeline, a test report will be generated.

## Structure
Project have simple structure : model's classes and several base tests

  insert screenshot of packages structure

Code of tests  writed on Java with helps RestAssured, assertJ, Junit5 and Allure Report libries

  insert screenshot of code

## Tests runs 
Tests runs on jenkins node. Jenkinsfile with pipeline settings is contains in repository

insert screenshot of jenkinsfile

insert screenshot of pipeline

## Tests report
For report generation used Allure Report

insert screenshots of report

