package mymicronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;
import mymicronaut.member.Member;

@Client("/hello") 
public interface HelloClient {

    @Get 
    Single<String> hello();

    @Post(processes = MediaType.APPLICATION_FORM_URLENCODED)
    Single<Member> createMember(String email, String password);

    @Get("/{id}")
    Single<Member> findById(String id);

    @Get("/byemail/{email}")
    Single<Member> findByEmail(String email);
}