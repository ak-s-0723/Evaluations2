# Persist User's Device and Login Info once Login is successful

## Requirement

##### Please solve this assignment once you have solved previous assignment `Login with Spring Security`

Once login is successful, We want to get User location and device details. That's why we are calling `verifyDevice` method of DeviceService  in `loginNotification` in UrlAuthenticationSuccessHandler.

In DeviceService, this verifyDevice calls several methods like 

- getDeviceDetails
- getIpLocation
- findExistingDevice

You need to add logic in all of these methods taking help from below resources.

Also In `verifyDevice` method, 
 - if existingDevice is non-null, then update `lastLoggedIn` 
 - if existingDevice is null, then create new instance of DeviceMetadata , populate all fields.
 - In both cases, persist using DeviceMetadataRepo

## Resources
https://www.baeldung.com/spring-security-login-new-device-location
https://www.baeldung.com/spring-redirect-after-login
https://www.baeldung.com/geolocation-by-ip-with-maxmind

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- Take help from above resources
- UserName and Password are present in application.properties which you can try to login.
- Instead of UNKNOWN, you can use ""