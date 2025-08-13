# Implement Login functionality using BCrypt

## Requirements

In LoginController , you need to implement an API with endpoint `login` which will take input in form of LoginRequestDto and return LoginResponseDto
 - In case of success, LoginResponseDto will contain status "SUCCESS" and token
 - In case of exception, LoginResponseDto will contain status "FAILURE" and null token

In UserService, add functionality in `login` method.
- If user is not found, You need to throw UserNotFoundException with message "There is no account with an email address: {emailvalue}"
- If user has passed wrong password in loginRequestDto, You need to throw PasswordMismatchException with message "Please type correct password, or reset it". Please check for password using BCryptPasswordEncoder as all passwords are BCrypt encoded only.
- Otherwise generate a random alphanumeric token of length 15 using `RandomStringUtils`

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.
- If you will try to run testcases without providing solution, all Testcases will fail.