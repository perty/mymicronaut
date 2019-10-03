package mymicronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import mymicronaut.member.Member;
import mymicronaut.member.MemberRepository;

import java.util.List;

@Controller("/hello") 
public class HelloController {

    private MemberRepository memberRepository;

    public HelloController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World"; 
    }

    @Get("/{id}")
    public Member show(String id) {
        return memberRepository
                .findById(id)
                .orElse(null);
    }

    @Get("/byemail/{email}")
    public HttpResponse<Member> findByEmail(String email) {
        List<Member> byEmailIgnoreCase = memberRepository.findByEmailIgnoreCase(email);
        if (byEmailIgnoreCase.isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(byEmailIgnoreCase.get(0));
    }

    @Post(consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<Member> createMember(String email, String password ) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        Member saved = memberRepository.save(member);
        return HttpResponse.created(saved);
    }
}
