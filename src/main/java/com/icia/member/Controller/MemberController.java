package com.icia.member.Controller;

import com.icia.member.Service.MemberService;
import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;

    // 회원가입 요청
    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("member", new MemberSaveDTO());
        System.out.println("회원가입 화면 요청");
        return "member/save";
    }

    // 회원가입 처리
    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult) {
        // @Validated 추가 @ModelAttribute 에 매개변수 ("member") BindingResult 추가
        System.out.println("회원가입처리");
        System.out.println("memberSaveDTO" + memberSaveDTO);

        if (bindingResult.hasErrors()) {
            return "member/save";
        }
        try {
            ms.save(memberSaveDTO);
        } catch (IllegalStateException e) {
            // e.getMessage()에는 서비스에서 지정한 예외메시지가 담겨있다.
            bindingResult.reject("emailCheck", e.getMessage());
            return "member/save";
        }
        return "redirect:/member/login";
    }

    //로그인 화면 요청
    @GetMapping("login")
    public String loginForm(Model model) {
        // addAttribute 와 th:object가 일치해야함
        model.addAttribute("login", new MemberLoginDTO());
        System.out.println("로그인 화면 요청");
        return "member/login";
    }

    //로그인처리
    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }

 //       boolean loginResult = ms.login(memberLoginDTO);
 //       if (loginResult) {
        if (ms.login(memberLoginDTO)) {
           session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            return "redirect:/member/findAll";
        } else {
            // 로그인 결과를 글로벌오류(Global Error) Login html에 코드작성
            // DB에 값이 있을때만 가능하다
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다");
            return "member/login";
        }
    }

    // 상세조회
    //  /member/2, /member/15 => /member/{memberId}
    // @PathVariable : 경로상에 있는 변수를 가져올 때 사용
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
        System.out.println("memberId = " + memberId);
        MemberDetailDTO member = ms.findById(memberId);
        model.addAttribute("member", member);

        return "member/detail";
    }

    //목록출력 = (/member)
    @GetMapping
    public String findAll(Model model){
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";

    }

}
