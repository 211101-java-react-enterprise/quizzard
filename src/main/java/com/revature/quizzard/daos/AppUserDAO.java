package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.datasource.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

    private final ConnectionFactory connectionFactory;

    public AppUserDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public AppUser findUserByUsername(String username) {

        Connection conn = connectionFactory.getConnection();
        AppUser retrievedUser = null;

        try {

            String sql = "select * from app_users where username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            retrievedUser = mapResultSet(rs).stream().findFirst().orElse(null);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return retrievedUser;

    }

    public AppUser findUserByEmail(String email) {

        Connection conn = connectionFactory.getConnection();
        AppUser retrievedUser = null;

        try {

            String sql = "select * from app_users where email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            retrievedUser = mapResultSet(rs).stream().findFirst().orElse(null);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return retrievedUser;

    }

    public AppUser findUserByUsernameAndPassword(String username, String password) {

        Connection conn = connectionFactory.getConnection();
        AppUser retrievedUser = null;

        try {

            String sql = "select * from app_users where username = ? and password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            retrievedUser = mapResultSet(rs).stream().findFirst().orElse(null);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return retrievedUser;

    }

    @Override
    public AppUser save(AppUser newUser) {

        Connection conn = connectionFactory.getConnection();

        try {

            newUser.setId(UUID.randomUUID().toString());

            String sql = "insert into app_users (user_id, first_name, last_name, email, username, password) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newUser.getId());
            pstmt.setString(2, newUser.getFirstName());
            pstmt.setString(3, newUser.getLastName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getUsername());
            pstmt.setString(6, newUser.getPassword());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return null;

    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser findById(String id) {
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    private List<AppUser> mapResultSet(ResultSet rs) throws SQLException {
        List<AppUser> users = new ArrayList<>();
        while (rs.next()) {
            AppUser user = new AppUser();
            user.setId(rs.getString("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            users.add(user);
        }
        return users;
    }

}
