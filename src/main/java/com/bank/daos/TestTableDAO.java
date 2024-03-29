package com.bank.daos;

import com.bank.models.TestTable;

import exceptions.NoSQLResultsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TestTableDAO implements Dao<TestTable>{
    //private List<TestTable> testTables;
    Connection connection;

    public TestTableDAO(Connection conn) {
        //testTables = new ArrayList<>();
        connection = conn;
    }

    @Override
    public TestTable get(int id) throws SQLException, NoSQLResultsException {
        String sql = "Select * FROM test_table WHERE string_id = ? LIMIT 1";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            TestTable row = new TestTable();
            row.setStringId(rs.getInt("string_id"));
            row.setString(rs.getString("string"));
            return row;
        } else {
            throw new NoSQLResultsException("ID: " + id + " not found.");
        }
    }

    @Override
    public List<TestTable> getAll() throws SQLException {
        String sql = "SELECT * FROM test_table";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        List<TestTable> testTableList = new ArrayList<>();

        while(rs.next()) {
            TestTable row = new TestTable();
            row.setStringId(rs.getInt("string_id"));
            row.setString(rs.getString("string"));
            testTableList.add(row);
        }
        return testTableList;
    }

    @Override
    public void save(TestTable testTable) throws SQLException {
        String sql = "INSERT INTO test_table (string) VALUES (?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, testTable.getString());

        if(pstmt.executeUpdate() > 0) {
            pstmt.getResultSet();
        }
    }

    @Override
    public void update(TestTable testTable, int id) throws SQLException, NoSQLResultsException{

    }

    @Override
    public void delete(int id) {

    }
}
