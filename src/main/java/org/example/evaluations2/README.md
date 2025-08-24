# Roles and Privileges Part 1

## Requirements

You need to create 3 Entities 

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