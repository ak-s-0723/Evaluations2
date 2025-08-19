# Validate Jwt

## Requirements

#### Please solve this assignment once you have solved previous assignment of `Generate Jwt`

#### AuthController
- In AuthController, You need to add an API with endpoint `/validateToken` which will accept Bearer Token (JWT) in Authorization Headers and return `ResponseEntity<ResponseDto>`.
- You need to extract Token from Headers before calling service method.
- In case, JWT validation is successful, ResponseDto will have SUCCESS status and Empty String Message, Return that along with 200 status code.
- In case of Exception, ResponseDto will have FAILURE status and Exception Message. Return that along with 401 status code.

#### AuthService
- Please keep this in mind that whichever token we generated, we persisted that in sessionMap through SessionRepo.
- You need to add implementation in `validateToken` method, where you need to check if JWT is created by us and non-expired. If there is any violation, You need to throw JwtException.
- Please make use of JwtConstants and SecretKey bean defined in AuthConfig

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.