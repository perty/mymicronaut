package mymicronaut;

import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;

import io.reactivex.Single;
import mymicronaut.member.Member;
import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HelloClientSpec  {

    private static final String SOME_EMAIL = "some.email" + Date.from(Instant.now()).getTime() + "@example.com";
    private static final String SOME_PASSWORD = "some password";
    private static final String SOME_NOT_EXISTING_EMAIL = "some-not-existing-email";

    @Inject
    HelloClient client; 

    @Test
    void testHelloWorldResponse(){
        assertEquals("Hello World", client.hello().blockingGet());
    }

    @Test
    @Order(1)
    void testCreatingAMember() {
        Single<Member> single = client.createMember(SOME_EMAIL, SOME_PASSWORD);

        assertNotNull(single);
        Member member = single.blockingGet();
        assertEquals(SOME_EMAIL, member.getEmail());

        single = client.findById(member.getId());
        assertNotNull(single);
        Member member2 = single.blockingGet();
        assertEquals(SOME_EMAIL, member2.getEmail());
        assertEquals(member.getId(), member2.getId());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @Order(2)
    void findByEmailThrowsNotFound() {
        Single<Member> byEmail = client.findByEmail(SOME_NOT_EXISTING_EMAIL);

        Assertions.assertThrows(
                HttpClientException.class,
                byEmail::blockingGet);
    }

    @Test
    @Order(3)
    void findMemberByEmail() {
        Single<Member> byEmail = client.findByEmail(SOME_EMAIL.toUpperCase());

        Member member = byEmail.blockingGet();
        assertEquals(SOME_EMAIL, member.getEmail());
    }
}