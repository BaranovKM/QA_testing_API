# Main project title
## Techstack
java, rest assured, assertJ, junit5, docker, jenkins, Allure report. add beutiful icons for used technologies 

## Description
This is my pet project for reaserch and lear how to work on ci/cd. Than include several simple api test. This tests are testing api from https://reqres.in/api 
and make base http request : POST, GET, PUT, DELETE and check response : status code, content type and validate json in request body. 
They are run throw jenkins multi-branch pipeline and generate test report

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

