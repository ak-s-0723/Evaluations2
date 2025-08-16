# Activate a new account by Email Part-2

## Requirements

Please come to this assignment, once you have solved previous assignment.

Please go through https://www.baeldung.com/registration-verify-user-by-email before solving this assignment.

Please create an account on Gmail and generate App Password using https://myaccount.google.com/apppasswords

This will help you test functionality while running App locally.

In RegistrationController, You need to implement an API with endpoint `/registrationConfirm` which will take `token` as Request Parameter and returns a String response on screen. This endpoint is to confirm user registration once he clicks on Verification Link received on emailId used for registration.

You also need to implement `confirmRegistration` method of RegistrationService following these
- First check if token received is valid or not. If it's invalid, throw RuntimeException with message "Invalid Token passed"
- Then check if token is expired or not. If it's expired, throw RuntimeException with message "Confirmation Link expired"
- Otherwise mark user as enabled and persist through Repo and return confirmation message.


## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.
- If you will try to run testcases without providing solution, all Testcases will fail.