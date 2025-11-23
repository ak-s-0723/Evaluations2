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

## Extra Read

- @Target({TYPE, FIELD, ANNOTATION_TYPE})
    - This tells where this annotation can be applied.
    - TYPE → can be used on a class, interface, or enum.
    - FIELD → can be applied on fields
    - ANNOTATION_TYPE → can also be used on another annotation (meta-annotation).
- @Retention(RUNTIME)
    - Specifies how long the annotation information should be retained.
    - RUNTIME → The annotation will be available at runtime, so reflection-based frameworks like Spring Validator or Hibernate Validator can detect and use it.
- @Constraint(validatedBy = EmailValidator.class)
    - validatedBy = EmailValidator.class tells Jakarta Bean Validation to use your custom validator (EmailValidator) whenever it encounters @ValidEmail.
    - The validator class must implement: ConstraintValidator<ValidEmail, String>
    - This is the heart of the custom validation logic.
- @Documented
    - This ensures that when Javadoc is generated, this annotation is included in the documentation.
    - Not required but helps in API documentation.