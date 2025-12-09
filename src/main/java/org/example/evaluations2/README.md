# Validate Jwt

## Goal

Build a secure token validation endpoint that protects your application's resources. This pattern is critical in microservices where each service validates JWTs independently without database calls


## Requirements

#### Please solve this assignment once you have solved previous assignment of `Generate Jwt`

#### AuthController
- Implement POST API at /validateToken
  - Request : JWT token in Authorization header as Bearer <token>
  - Response : ResponseEntity`<ResponseDto>`
- (Hint) You need to extract Token from Headers before calling service method.
- In case of Success 
    - ResponseDto : {"status": "SUCCESS", "message": ""}
    - Http Status :  200 OK
- In case of Failure
    - ResponseDto : {"status": "FAILURE", "message": "exception message"}
    - Http Status :  401 UNAUTHORIZED


#### AuthService -  validateToken Implementation

- Please keep this in mind that the token which we generated, we persisted that in sessionMap through SessionRepo.
- You need to add implementation in `validateToken` method, where you need to check if JWT is created by us and non-expired. If there is any violation, You need to throw JwtException.
- Please make use of JwtConstants and SecretKey bean defined in AuthConfig

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.