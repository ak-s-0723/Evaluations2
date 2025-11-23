# Implement Signup functionality using BCrypt

## Requirements

In RegistrationController , 
 - You need to implement an API with endpoint `registration` which will take input in form of UserDto and return created User object in case signup is successful. 
 - In case of success, also return `CREATED` status along with Response Body.
 - In case of exception, return `CONFLICT` status along with null Response Body.

In UserService, add functionality in `registerNewUserAccount` method. 
 - If email already exists, You need to throw UserAlreadyExistException with message "There is already an account with email address: {emailvalue}"
 - Otherwise create a User object and save using repo layer. Id can be randomly generated and please make sure password is encoded using BCryptPasswordEncoder and roles should contain "ROLE_USER"

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.
- If you will try to run testcases without providing solution, all Testcases will fail.