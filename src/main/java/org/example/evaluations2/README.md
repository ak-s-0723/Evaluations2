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

## Extra Read 

- @Target({TYPE, ANNOTATION_TYPE})
   - This tells where this annotation can be used.
   - TYPE → can be used on a class, interface, or enum.
   - ANNOTATION_TYPE → can also be used on another annotation (meta-annotation).
- @Retention(RUNTIME)
   - This tells how long the annotation should be kept.
   - RUNTIME → The annotation will be available at runtime, so reflection-based frameworks like Spring Validator or Hibernate Validator can detect and use it.
- @Documented
  - This ensures that when Javadoc is generated, this annotation is included in the documentation.
  - Not required but helps in API documentation.
- 