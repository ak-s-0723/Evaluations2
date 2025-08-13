# Create PasswordMatches Annotation for Signup

## Requirements

Please read  https://www.baeldung.com/registration-with-spring-mvc-and-spring-security to understand how to create validations for User Signup

You need to complete 2 tasks here
- Add logic in `isValid` method of PasswordMatchesValidator which will check if password and matchingPassword are same or not.
- Add necessary annotations and logic in PasswordMatches file taking help from above reference which will trigger PasswordMatchesValidator `isValid` method.

Please note, Whatever logic you will add in PasswordMatches , that is getting triggered by `@Valid` in POST API in RegistrationController

You only need to make changes in PasswordMatches and PasswordMatchesValidator. Please don't change anything in any other file

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.