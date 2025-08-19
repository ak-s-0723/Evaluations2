# Generate Jwt

## Requirements

#### AuthController
- In AuthController,You need to add an API with endpoint `/authToken` which will take input in form of `RequestDto` and return `ResponseEntity<ResponseDto>`.
- In case, token creation is successful, ResponseDto will have SUCCESS status. `Bearer ` will be added in front of generated JWT and will be sent as Authorization Header along with 201 status code.
- In case of Exception while token creation, ResponseDto will have FAILURE status and sent along with 500 status code.

#### AuthService
- You need to add implementation in `generateJwt` method, where you need to create and persist JWT.
- Please make use of all JwtConstants while setting claims/payload in JWT. SessionId will be generated and added in claims as `session_primary_<sessionId>`.
- Please create a bean of SecretKey using `secret.key` present in application.properties for signing token.
- Once JWT is generated, you need to check for all active sessions for that user using Repo and mark all of them as EXPIRED.
- Now create a new Session with ACTIVE state and persist that.
- Finally Return token

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- If you will try to run testcases without providing solution, all testcases will fail.