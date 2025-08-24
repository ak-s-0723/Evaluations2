package org.example.evaluations2.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRolePriviligeSchemaValidationTests {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testIfUsersTableExists() {
        assertTableExists("users_");
    }

    @Test
    void testIfRolesTableExists() {
        assertTableExists("roles_");
    }

    @Test
    void testIfPrivilegesTableExists() {
        assertTableExists("privileges_");
    }

    @Test
    void testIfUsersRolesMappingTableExists() {
        assertTableExists("users_roles_");
    }

    @Test
    void testIfRolesPrivilegesMappingTableExists() {
        assertTableExists("roles_privileges_");
    }

    @Test
    void testUsersTableColumns() throws SQLException {
        Set<String> expected = Set.of(
                "ID", "FIRST_NAME", "LAST_NAME",
                "EMAIL", "PASSWORD",
                "ENABLED", "TOKEN_EXPIRED"
        );
        assertColumnsMatch("users_", expected);
    }

    @Test
    void testRolesTableColumns() throws SQLException {
        Set<String> expected = Set.of("ID", "NAME");
        assertColumnsMatch("roles_", expected);
    }

    @Test
    void testPrivilegesTableColumns() throws SQLException {
        Set<String> expected = Set.of("ID", "NAME");
        assertColumnsMatch("privileges_", expected);
    }

    @Test
    void testUsersRolesTableColumns() throws SQLException {
        Set<String> expected = Set.of("USER_ID", "ROLE_ID");
        assertColumnsMatch("users_roles_", expected);
    }

    @Test
    void testRolesPrivilegesTableColumns() throws SQLException {
        Set<String> expected = Set.of("ROLE_ID", "PRIVILEGE_ID");
        assertColumnsMatch("roles_privileges_", expected);
    }

    @Test
    void testUsersRolesForeignKeys() throws SQLException {
        assertForeignKeyExists("users_roles_", "USER_ID", "users_");
        assertForeignKeyExists("users_roles_", "ROLE_ID", "roles_");
    }

    @Test
    void testRolesPrivilegesForeignKeys() throws SQLException {
        assertForeignKeyExists("roles_privileges_", "ROLE_ID", "roles_");
        assertForeignKeyExists("roles_privileges_", "PRIVILEGE_ID", "privileges_");
    }


    private void assertTableExists(String tableName) {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName.toUpperCase()}, Integer.class);
        assertTrue(count != null && count > 0, "Table " + tableName + " does not exist!");
    }

    private void assertColumnsMatch(String tableName, Set<String> expected) throws SQLException {
        Set<String> actual = getColumnNames(tableName.toUpperCase());
        assertTrue(actual.containsAll(expected),
                "Table " + tableName + " missing expected columns. Expected: " + expected + " but got: " + actual);
    }

    private void assertForeignKeyExists(String tableName, String fkColumn, String refTable) throws SQLException {
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        boolean found = false;
        try (ResultSet rs = metaData.getImportedKeys(null, null, tableName.toUpperCase())) {
            while (rs.next()) {
                String fkColName = rs.getString("FKCOLUMN_NAME");
                String pkTableName = rs.getString("PKTABLE_NAME");
                if (fkColName.equalsIgnoreCase(fkColumn) &&
                        pkTableName.equalsIgnoreCase(refTable)) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found, "Foreign key from " + tableName + "." + fkColumn +
                " to " + refTable + " does not exist!");
    }

    private Set<String> getColumnNames(String tableName) throws SQLException {
        Set<String> cols = new HashSet<>();
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                cols.add(rs.getString("COLUMN_NAME"));
            }
        }
        return cols;
    }
}
