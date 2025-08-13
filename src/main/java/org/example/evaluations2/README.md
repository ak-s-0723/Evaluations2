# Create ValidEmail Annotation for Signup

## Requirements

Please read  https://www.baeldung.com/registration-with-spring-mvc-and-spring-security to understand how to create validations for User Signup

You need to complete 2 tasks here
- Add logic in `validateEmail` and  `isValid` method of EmailValidator which will check if email is following proper email pattern or not.
- Add necessary annotations and logic in ValidEmail file taking help from above reference which will trigger EmailValidator `isValid` method.

Please note, Whatever logic you will add in ValidEmail file , that is getting triggered by `@Valid` in POST API in RegistrationController

You only need to make changes in ValidEmail and EmailValidator. Please don't change anything in any other file.

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.