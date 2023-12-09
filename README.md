# API tests

![Static Badge](https://img.shields.io/badge/Java-17-brown?style=plastic)
![Static Badge](https://img.shields.io/badge/Lombok-1.18.28-green?style=plastic)
![Static Badge](https://img.shields.io/badge/REST_Assured-5.3.1-green?style=plastic)
![Static Badge](https://img.shields.io/badge/Jackson-2.9.5-green?style=plastic)
![Static Badge](https://img.shields.io/badge/AssertJ-3.24.2-green?style=plastic)
![Static Badge](https://img.shields.io/badge/Junit-5.9.2-gold?style=plastic)
![Static Badge](https://img.shields.io/badge/Allure_Report-2.24-gold?style=plastic)
![Static Badge](https://img.shields.io/badge/Maven-3.9.1-blue?style=plastic)
![Static Badge](https://img.shields.io/badge/Jenkins-2.414.3-blue?style=plastic)


## Description

This is my pet project to reaserch and lear how to work with ci/cd. Here are several simple tests that testing [Reqres API](https://reqres.in). 
They make basic HTTP requests : POST, GET, PUT, DELETE and check response(status code, content type, body, etc). 
These tests can be run in Jenkins pipeline. At the end of pipeline, a test report will be generated.

The project has a simple structure : model classes and several basic tests. 
The test code is written in Java using REST Assured, AssertJ, Junit 5 and Allure Report libraries.
Maven was used to build project.

```java
        Allure.label(PARENT_SUITE_LABEL_NAME, GET_SINGLE_USER);
        Allure.step("Make GET request for single user");
        ValidatableResponse response =
                given()
                    .spec(getDefaultRequestSpecification())
                .when()
                    .get(USERS_PATH_WITH_PARAMS, UsersData.DEFAULT_USER_ID)
                .then()
                    .contentType(ContentType.JSON)
                    .statusCode(HTTP_OK)
                    .time(Matchers.lessThan(5000L));

        Allure.addAttachment(
                "response",
                "application/json",
                response.extract().asPrettyString(),
                ".json");

        User user = response.extract().as(User.class);

        Allure.step("Validate response", stepContext -> {
            Allure.step("Check user's id");
            assertThat(user.data.id)
                    .isEqualTo(UsersData.DEFAULT_USER_ID);

            Allure.step("Check user's email");
            assertThat(user.data.email)
                    .isEqualTo(UsersData.DEFAULT_USER_EMAIL);

            Allure.step("Check user's avatar");
            assertThat(user.data.avatar)
                    .isEqualTo(UsersData.DEFAULT_USER_AVATAR_URL);

            Allure.step("Check user's first name");
            assertThat(user.data.getFirstName())
                    .isEqualTo(UsersData.DEFAULT_USER_FIRST_NAME);

            Allure.step("Check user's last name");
            assertThat(user.data.getLastName())
                    .isEqualTo(UsersData.DEFAULT_USER_LAST_NAME);

            Allure.step("Check support link");
            assertThat(user.support.url)
                    .isEqualTo(UsersData.DEFAULT_USER_SUPPORT_URL);

            Allure.step("Check support text");
            assertThat(user.support.text)
                    .isEqualTo(UsersData.DEFAULT_USER_TEXT);
        });
```

## Running tests

Tests can be run both localy also in Jenkins. 

![Screenshot of a jenkinsfile](/assets/images/jenkinsPipelineScreenshot.png)

For simplicity, pipeline consists of only one stage

Jenkinsfile with pipeline settings is contained in repository

```groovy
pipeline {
    agent any
    stages {
        stage('Run tests'){
            agent {
                docker {
                    image 'maven:3.9.5-eclipse-temurin-17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn verify -Dmaven.test.failure.ignore=true'//ignore tests fails so maven can make build
                //save tests results for use outside container
                stash name: 'allure-results', includes: 'allure-results/*'
            }
       }
    }
    post {
        //generate allure report
        always {
            unstash 'allure-results'
            allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            junit allowEmptyResults: true, skipMarkingBuildUnstable: true, testResults: '**/target/surefire-reports/*.xml'
        }
    }
}
```

## Test report

Allure Report is used to generate reports. Tests are divided into separate steps and annotated about owner, severity, tags, and etc. 
Also tests can be linked to bugtracker or/and task management system.

```java
    @Test
    @DisplayName("POST for single user")
    @Description("Make POST request for single user and validate json in response")
    @Severity(CRITICAL)
    @Owner(BARANOV_KM)
    @Epic(MAIN_SYSTEM_API)
    @Feature(USERS_ENDPOINT)
    @Story(CREATE_SINGLE_USER)
    @Link(TICKET_123)
    @TmsLink(TEST_123)
    @Tags({@Tag("api"), @Tag("smoke"), @Tag("regression")})
```

![Screenshot of a Allure Report Overview ](/assets/images/allureOverviewScreenshot.png)

Report allows to clearly show test behavior. 

If nessasary, request and response can be included in attachment

![Screenshot of a Allure Report Overview ](/assets/images/allureBehaviorsScreenshot.png)

In case of error, test is marked as failed

The step at which test failed is displayed with stacktrace

![Screenshot of a Allure Report Overview ](/assets/images/allureCategoriesScreenshot.png)

