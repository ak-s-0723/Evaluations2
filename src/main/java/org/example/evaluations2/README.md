# What to do after Login is successful ?

## Requirements

#### Please solve this assignment once you have solved previous assignments

Now, we want to show Device Details which we persisted in previous assignments on Browser once login is successful, so for that we need to make GET call at endpoint `/devices/users/{userEmail}` which will eventually get details from DeviceMetadataRepo using DeviceService.

Please Make sure to add implementation in `findDevicesByUserEmail` of DeviceService.

As soon as login is successful, we need to call `sendRedirect` method of `CustomRedirectStrategy`. Please add implementation inside this method also.

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- All Testcases will fail, if you will try to run without providing solution.





