# Roles and Privileges Part 3

### Requirements

#### Please solve this assignment once you have solved Roles and Privileges Part 1 and Part 2

In previous Roles and Privileges Assignments, we have added models, defined relationships and added a `/register` end point to register a new user on our platform.

As part of this assignment, We have already added Spring Security dependency, you need to

- Add Implementation in `loadUserByUsername` method present inside StorageUserDetailsService as per below details -->
  - Check if user found in database through email, otherwise throw UsernameNotFoundException with message "user not found in db".
  - If user is found, You need to build UserDetails object using attributes of user like email, password and isEnabled etc.
  - You also need to implement `getAuthorities` method which you will call while building UserDetails instance from user.
  - In `getAuthorities` method , you need to build GrantedAuthorities using Roles and Privileges assigned to user , taking help from Reference - https://www.baeldung.com/role-and-privilege-for-spring-security-registration
- You also need to define few beans like SecurityFilterChain, BCryptPasswordEncoder and RoleHierarchy in SecurityConfig.
  - In case of SecurityFilterChain, You will define that anyone should be able to call `/register` as it's used for signup.
  - In case of SecurityFilterChain, You will define that, `/roleHierarchy` should only be called if person has `STAFF` role.
  - Every other request need to be authenticated and please disable Csrf.
  - While defining SecurityFilterChain, enable Form-based authentication and HTTP Basic authentication
  - You also need to define RoleHierarchy and set Hierarchy as `ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER`
  - You can take help from above reference - https://www.baeldung.com/role-and-privilege-for-spring-security-registration

### Hints
- Nothing is needed from your side in pom.xml or application.properties
- No new file need to be created.
- If you will try to run testcases without defining relations, all Testcases will fail.