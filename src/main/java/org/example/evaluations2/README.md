# Roles and Privileges Part 3

## Goal
Bridge from entities to real Spring Security config and prepare for JWT-based auth later.


### Requirements

#### Please solve this assignment once you have solved Roles and Privileges Part 1 and Part 2

You have User, Role, and Privilege entities and a /register endpoint to create users from previous assignments.

As part of this assignment, We have already added Spring Security dependency, you need to

- Add Implementation in `loadUserByUsername` method present inside StorageUserDetailsService as per below details -->
  - Check if user found in database through email, otherwise throw UsernameNotFoundException with message "user not found in db".
  - If user is found, You need to build UserDetails object using
     - Email as username.
     - Stored hashed password 
     - Enabled/disabled and account flags based on ENABLED, TOKEN_EXPIRED, etc.
 
  - From user’s roles and privileges, build `Collection<? extends GrantedAuthority> getAuthorities(
    Collection<Role> roles)`
  - In `getAuthorities` method , you need to build GrantedAuthorities using Roles and Privileges assigned to user , taking help from Reference - https://www.baeldung.com/role-and-privilege-for-spring-security-registration




<br>

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
- If you will try to run testcases without adding implementation, all Testcases will fail.