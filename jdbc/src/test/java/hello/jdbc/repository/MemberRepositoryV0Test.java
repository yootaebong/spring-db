package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        String memberId = "memberV102";

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