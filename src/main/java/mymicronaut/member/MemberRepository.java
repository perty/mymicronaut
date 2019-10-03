package mymicronaut.member;

import io.micronaut.spring.tx.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(@NotNull String id);

    @Transactional
    Member save(Member member);

    List<Member> findByEmailIgnoreCase(String email);

    Optional<Member> findByProfileUserName(String userName);

    List<Member> findByEmailLike(String pattern);
}
