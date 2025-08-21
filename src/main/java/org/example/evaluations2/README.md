# Login using Spring Security

## Requirements

- You need to add implementation in bean of SecurityFilterChain in which you will configure that each request need to be authenticated and once authenticated using correct username and  password, UrlAuthenticationSuccessHandler should be called.

- As UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler, you need to add implementation in  method  `onAuthenticationSuccess` :
  - As soon as login is successful, You want to display `{username} + ", Welcome !!` on screen without any redirection.
- Also please add implementation in `clearAuthenticationAttributes` which is used to 
    - Look up the session (if any).
    - Removes the AUTHENTICATION_EXCEPTION attribute. 

## Hints
- No changes needed in pom.xml or application.properties.
- No new file need to be added.
- You can take help from this resource - https://www.baeldung.com/spring-redirect-after-login
- UserName and Password are present in application.properties which you can try to login.


