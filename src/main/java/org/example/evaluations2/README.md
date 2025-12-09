# Activate a new account by Email 

## Goal

Email Verification - Event Publishing

Implement asynchronous email verification using Spring's event-driven architecture. This pattern is used in production for scalability and decoupling.


## Requirements

Please go through https://www.baeldung.com/registration-verify-user-by-email before solving this assignment.

Please create an account on Gmail and generate App Password using https://myaccount.google.com/apppasswords

This will help you run Integration Test and also test functionality while running App.

You need to add functionality in the method `publishEvent` of RegistrationPublisher. Create an instance of OnRegistrationCompleteEvent and publishEvent through ApplicationEventPublisher.

Also add implementation in `confirmRegistration` method of RegistrationListener as per below details. The same steps are also present in the Baeldung reference link given above.
 - Get User from event
 - Generate a random alphanumeric token of length 30 using `RandomStringUtils`
 - create VerificationToken using RegistrationService
 - Form ConfirmationUrl which will be in format - AppUrl + "/registrationConfirm?token=" + token value
 - Create an instance of SimpleEmailMessage
 - Get recipientEmail from User and set in an instance of SimpleEmailMessage
 - Set subject as "Confirm Registration" in an instance of SimpleEmailMessage
 - Set text as "Please click on below link to confirm registration.\r\n" + ConfirmationUrl
 - Finally send this message using JavaMailSender.

## Hints
- Nothing is needed from your side in pom.xml or application.properties. Dependency is already added.
- No new file need to be created.
- If you will try to run testcases without providing solution, all Testcases will fail.
- You only need to implement above mentioned methods in RegistrationPublisher and RegistrationListener.