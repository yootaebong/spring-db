package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static hello.jdbc.connection.ConnectionConst.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        String memberId = "memberV103";

        Member member = new Member(memberId, 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(memberId);
        assertEquals(member.getMemberId(), findMember.getMemberId());

        // update money 10000 -> 20000
        repository.update(findMember.getMemberId(), 20000);
        Member updateMember = repository.findById(findMember.getMemberId());
        assertEquals(20000, updateMember.getMoney());

        // delete
        repository.delete(findMember.getMemberId());
        assertThrows(IllegalStateException.class, () -> repository.findById(findMember.getMemberId()));
    }


}