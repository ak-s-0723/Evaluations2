# Roles and Privileges Part 1

## Requirements

In any secure application, controlling who can access what is critical. This is where Authentication and Authorization come into play.
To implement these correctly, we typically use a structured model involving Users, Roles, and Privileges.

1. Authentication – “Who are you?”

   - Authentication is the process of verifying the `identity` of a user.

2. Authorization – “What are you allowed to do?”
   - After authentication, the next step is authorization—determining the permissions the user has.
   - This is where Roles and Privileges become important.
   
3. Roles – Grouping of permissions
    - A role represents a collection of actions a user can perform.
    - Example: 
       - ROLE_ADMIN → can manage users, view reports, delete data
       - ROLE_USER → can view own data, update own profile
    - Roles reduce complexity by grouping permissions into meaningful categories.
4. Privileges – Fine-grained permissions
    - Privileges are the most granular access-control units.
    -  A role usually contains many privileges.
    - ADMIN can have READ_USER, WRITE_USER, DELETE_USER, READ_REPORTS
    - USER can have READ_USER


Here the task is to create 3 Entities 

`users_` with columns `ID`, `FIRST_NAME`, `LAST_NAME`, `PASSWORD`, `EMAIL`, `ENABLED` ,`TOKEN_EXPIRED`

`roles_` with columns `ID`, `NAME`

`privileges_` with columns `ID`, `NAME`


You also need to define cardinalities between these entities whatever you think are applicable.

If anywhere mapping table need to be created, it's name will be in format `table1_table2_`.

## Testing

You can also check which tables with what fields are created in H2 by running Application in IntellIJ and opening  `http://localhost:8080/h2-console` on browser and put values as below
- Saved Settings: `Generic H2(Embedded)`
- Setting Name: `Generic H2(Embedded)`
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:c4_a1`
- User Name: `sa`
- Password: `password`
- click Connect

## Hints

- Nothing is needed from your side in pom.xml or application.properties
- If you will try to run testcases without implementation, all Testcases will fail.