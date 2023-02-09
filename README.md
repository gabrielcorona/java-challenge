### Basic Unit Tests
- The test were added to validate successful ideal cases and basic error handling exceptions.

### Exception Handling Advice
- The exceptions have been implemented, as well a custom controller advice has been added to catch the exceptions to provide a more standard message as a response in the endpoint.

### Adjustments in Logic
- The logic has been modified to validate employee existance, as well the employee entity has been modified to handle better the currency and the getters and setters were moved to be implemented to all the properties and null values will not be allowed.

### Adding Caching
- The respective annotations have been added to the corresponding methods to allow caching and remove the caching when an element has been affected.

### Controller Modifications
- Moved the logic found in the controller to the service implementation, changed the id existency validation exist by id and log instead of system out.
- Modified the controller to return the resulted employee in the case of POST and PUT, as well in the case of POST we now require the employee data in the request body.
- Removed unnecesary setters due to the autowiring.
----------------------------------------------------
### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years
