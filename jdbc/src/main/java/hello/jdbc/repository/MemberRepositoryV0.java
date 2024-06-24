package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (Exception e) {
            log.error("error = {}", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId){
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Member(rs.getString("member_id"), rs.getInt("money"));
            }
            else{
                throw new NoSuchElementException("No value member_id = " + memberId);
            }
        } catch (Exception e) {
            log.error("error = {}", e);
            throw new IllegalStateException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money){
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int result = pstmt.executeUpdate();
            log.info("result = {}", result);
        } catch (Exception e) {
            log.error("error = {}", e);
            throw new IllegalStateException(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId){
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            log.error("error = {}", e);
            throw new IllegalStateException(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet re){
        if (re != null)
            try {
                re.close();
            } catch (Exception e) {
                log.error("error = {}", e);
            }

        try {
            if(stmt != null){
                stmt.close();
            }
        } catch (Exception e) {
            log.error("error = {}", e);
        }

        try {
            if(con != null){
                con.close();
            }
        } catch (Exception e) {
            log.error("error = {}", e);
        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
