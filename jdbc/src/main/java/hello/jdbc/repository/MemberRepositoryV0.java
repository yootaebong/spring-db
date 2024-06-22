package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

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
