package com.seungmi.openBoard.dao;

import com.seungmi.openBoard.domain.*;
import com.seungmi.openBoard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    DataSource ds;

    @Override
    //테이블에서 해당 ID의 사용자 정보를 삭제
    public int deleteUser(String id) throws Exception {
        int rowCnt = 0;
        String sql = "DELETE FROM user_info WHERE id= ? ";

        try (  // try-with-resources - since jdk7
               Connection conn = ds.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, id);
            return pstmt.executeUpdate(); //  insert, delete, update
//      } catch (Exception e) {
//          e.printStackTrace();
//          throw e;
        }
    }

    @Override
    public int deleteUserTmp(String id) throws Exception {
        int rowCnt = 0;
        String sql = "DELETE FROM tmp WHERE id= ? ";

        try (  // try-with-resources - since jdk7
               Connection conn = ds.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, id);
            return pstmt.executeUpdate(); //  insert, delete, update
//      } catch (Exception e) {
//          e.printStackTrace();
//          throw e;
        }
    }

    @Override
    //해당 ID의 사용자 정보를 테이블에서 가져와 user 객체에 담음
    public User selectUser(String id) throws Exception {
        User user = null;
        String sql = "SELECT * FROM user_info WHERE id= ? ";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

        ){
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery(); //  select

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setBirth(new Date(rs.getDate(5).getTime()));
                user.setReg_date(new Date(rs.getTimestamp(6).getTime()));
            }
        }

        return user;
    }

    // 사용자 정보를 user_info테이블에 저장하는 메서드
    @Override
    public int insertUser(User user) throws Exception {

        String sql = "INSERT INTO user_info VALUES (?,?,?,?,?, now()) ";

        try(
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ){
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));

            System.out.println("sql = " + sql);

            return pstmt.executeUpdate();
        }
    }

    @Override
    public int insertUserTmp(User user) throws Exception {

        String sql = "INSERT INTO tmp VALUES (?,?,?,?,?, now()) ";

        try(
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ){
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));

            System.out.println("sql = " + sql);

            return pstmt.executeUpdate();
        }
    }

    @Override
    //사용자 정보 수정 메서드
    public int updateUser(User user) throws Exception {
        int rowCnt = 0;

        String sql = "UPDATE user_info " +
                "SET pwd = ?, name=?, email=?, birth =? " +
                "WHERE id = ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, user.getPwd());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
            pstmt.setString(5, user.getId());

            rowCnt = pstmt.executeUpdate();
        }

        return rowCnt;
    }

    @Override
    //총 회원 수 구하는 메서드
    public int count() throws Exception {
        String sql = "SELECT count(*) FROM user_info ";

        try(
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ){
            rs.next();
            int result = rs.getInt(1);

            return result;
        }
    }

    @Override
    //모든 회원 삭제 메서드
    public void deleteAll() throws Exception {
        try (Connection conn = ds.getConnection();)
        {
            String sql = "DELETE FROM user_info ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
    }

    public User selectUserByEmail(String email) throws Exception{

        User user = null;
        String sql = "SELECT * FROM user_info WHERE email= ?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

        ){
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery(); //  select

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setBirth(new Date(rs.getDate(5).getTime()));
                user.setReg_date(new Date(rs.getTimestamp(6).getTime()));
            }
        }

        return user;

    }

}