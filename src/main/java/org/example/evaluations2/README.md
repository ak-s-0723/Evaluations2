# Roles and Privileges Part 2

### Requirements

#### Please make sure you solve this assignment once you have solved Roles and Privileges Part 1

In previous assignment, you have defined User, Role and Privilege Entities and relations between them.

In this assignment, 
- You need to create various Privileges like _READ_PRIVILEGE_ and _WRITE_PRIVILEGE_  and Roles like _ROLE_ADMIN_ and _ROLE_USER_ and set these up as soon as our application starts. You can take help from below reference.
- You need to add an endpoint `/register` in RegistrationController which will take input in the form of UserDto and return registered User along with 201 HTTP StatusCode.
- In RegistrationService, you also need to add implementation in `registerNewUserAccount` method, where 
  - You need to throw EmailExistsException with message "There is an account with that email address: {email}" if account already exists.
  - Otherwise create User with all fields and persist. Set appropriate default role (ROLE_USER) and default privileges via role. 
  - Set ENABLED and TOKEN_EXPIRED flags reasonably while creating user. 

### References
https://www.baeldung.com/role-and-privilege-for-spring-security-registration

### Hints
- Nothing is needed from your side in pom.xml or application.properties
- No new file need to be created.
