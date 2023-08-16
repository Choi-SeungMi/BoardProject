package com.seungmi.openBoard.dao;

import com.seungmi.openBoard.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Repository
public class UserDaoImpl implements UserDao {

    DataSource ds;

    UserDaoImpl(DataSource ds){this.ds = ds;}

    @Override
    //테이블에서 해당 ID의 사용자 정보를 삭제
    public int deleteUser(String id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowCnt = 0;
        String sql = "DELETE FROM user_info WHERE id= ? ";

        try {
               conn = ds.getConnection();
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, id);
               return pstmt.executeUpdate(); //  insert, delete, update
      } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }
        finally {
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}

        }
    }

    @Override
    //해당 ID의 사용자 정보를 테이블에서 가져와 user 객체에 담음
    public UserDto selectUser(String id) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDto userDto = null;
        String sql = "SELECT * FROM user_info WHERE id= ? ";

        try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);
            rs = pstmt.executeQuery(); //  select

            if (rs.next()) {
                userDto = new UserDto();
                userDto.setId(rs.getString(1));
                userDto.setPwd(rs.getString(2));
                userDto.setName(rs.getString(3));
                userDto.setEmail(rs.getString(4));
                userDto.setBirth(new Date(rs.getDate(5).getTime()));
                userDto.setReg_date(new Date(rs.getTimestamp(6).getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(rs != null) {try {rs.close();} catch (SQLException e) {}}
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }

        return userDto;
    }

    // 사용자 정보를 user_info테이블에 저장하는 메서드
    @Override
    public int insertUser(UserDto userDto) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO user_info VALUES (?,?,?,?,?, now()) ";

        try{
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userDto.getId());
            pstmt.setString(2, userDto.getPwd());
            pstmt.setString(3, userDto.getName());
            pstmt.setString(4, userDto.getEmail());
            pstmt.setDate(5, new java.sql.Date(userDto.getBirth().getTime()));

            System.out.println("sql = " + sql);

            return pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }
    }

    @Override
    //사용자 정보 수정 메서드
    public int updateUser(UserDto userDto) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rowCnt = 0;

        String sql = "UPDATE user_info " +
                "SET pwd = ?, name=?, email=?, birth =? " +
                "WHERE id = ?";

        try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userDto.getPwd());
            pstmt.setString(2, userDto.getName());
            pstmt.setString(3, userDto.getEmail());
            pstmt.setDate(4, new java.sql.Date(userDto.getBirth().getTime()));
            pstmt.setString(5, userDto.getId());

            rowCnt = pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }

        return rowCnt;
    }

    @Override
    //총 회원 수 구하는 메서드
    public int count() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT count(*) FROM user_info ";

        try{
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
            rs.next();
            int result = rs.getInt(1);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(rs != null) {try {rs.close();} catch (SQLException e) {}}
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }
    }

    @Override
    //모든 회원 삭제 메서드
    public void deleteAll() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {conn = ds.getConnection();

            String sql = "DELETE FROM user_info ";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }
    }

    //이메일로 회원정보를 찾는 메서드
    public UserDto selectUserByEmail(String email) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDto userDto = null;
        String sql = "SELECT * FROM user_info WHERE email= ?";

        try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            rs = pstmt.executeQuery(); //  select

            if (rs.next()) {
                userDto = new UserDto();
                userDto.setId(rs.getString(1));
                userDto.setPwd(rs.getString(2));
                userDto.setName(rs.getString(3));
                userDto.setEmail(rs.getString(4));
                userDto.setBirth(new Date(rs.getDate(5).getTime()));
                userDto.setReg_date(new Date(rs.getTimestamp(6).getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(rs != null) {try {rs.close();} catch (SQLException e){}}
            if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null) {try {conn.close();} catch (SQLException e){}}
        }

        return userDto;

    }

}