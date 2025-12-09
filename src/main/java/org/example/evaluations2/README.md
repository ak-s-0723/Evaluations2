# Generate Jwt

## Goal

You're building a production-ready authentication service that issues JWT tokens. This implementation must handle concurrent sessions, prevent token abuse, and follow JWT security best practices commonly tested in backend interviews.​


## Requirements

#### AuthController
Implement a POST API at /authToken with

 - Input: RequestDto 
 - Output: ResponseEntity<ResponseDto>

- In case of Success
   - ResponseDto: { "status": "SUCCESS"}
   - Status Code - 201 CREATED
   - Headers: { "Authorization": "Bearer <JWT_TOKEN>" }

- In case of failure
  - ResponseDto: { "status": "FAILURE"}
  - Status Code - 500 INTERNAL SERVER ERROR


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