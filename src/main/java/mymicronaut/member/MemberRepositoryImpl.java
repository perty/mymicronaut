package mymicronaut.member;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public MemberRepositoryImpl(@CurrentSession EntityManager entityManager,
                                ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(@NotNull String id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    @Override
    @Transactional
    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(UUID.randomUUID().toString());
        }
        entityManager.persist(member);
        return member;
    }

    @Override
    @Transactional
    public List<Member> findByEmailIgnoreCase(String email) {
        Query query = entityManager.createQuery("select m from Member m where lower(m.email)=:email");
        query.setParameter("email", email.toLowerCase());
        return query.getResultList();
    }

    @Override
    public Optional<Member> findByProfileUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public List<Member> findByEmailLike(String pattern) {
        return Collections.emptyList();
    }
}
