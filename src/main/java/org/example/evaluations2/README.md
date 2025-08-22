# Call Logout using Spring Security

## Requirements

#### Please solve this assignment once you have solved previous assignments of Spring Security for better context

You need to add an endpoint `/home` in Home Controller which will be called as soon as spring security login is successful and you will return below message from this API 
`You are logged in! If you want to Logout, Click <a href='/logout'>Here</a>`

You also need to configure calling of `/home` in case of login success.

And If user clicks on hyperlink `Here` on screen, You need to make call to spring `/logout`.

As soon `/logout` gets called, it should call `onLogoutSuccess` method of CustomLogoutSuccessHandler.
Also Invalidate Http Session and delete "JSESSIONID" cookie when `/logout` is called.

Please take help from below reference.

Please note, You don't need to add anything inside CustomLogoutSuccessHandler class. Your changes will be only in SecurityConfig and HomeController.

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- UserName and Password are present in application.properties

## References
https://www.baeldung.com/spring-security-logout