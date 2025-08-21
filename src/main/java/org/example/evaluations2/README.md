# Call an API after Spring Security Login is successful

## Requirements

#### Please solve this assignment once you have solved previous assignments of Spring Security for better context

Now, we want to show Device Details which we persisted in previous assignments on Browser after login is successful, so for that we need to make GET call at endpoint `/devices/users/{userEmail}` which will eventually get details from DeviceMetadataRepo using DeviceService.

Please Make sure to add implementation in `findDevicesByUserEmail` of DeviceService.

As soon as login is successful, we need to call `sendRedirect` method of `CustomRedirectStrategy`. Please add implementation inside this method also.

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- All Testcases will fail, if you will try to run without providing solution.
- UserName and Password are present in application.properties

## References
https://www.baeldung.com/spring-security-login-new-device-location
https://www.baeldung.com/spring-redirect-after-login
https://www.baeldung.com/geolocation-by-ip-with-maxmind





