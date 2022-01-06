package com.icia.member;

import com.icia.member.Service.MemberService;
import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

//테스트 코드에는 @SpringBootTest를 붙인다
@SpringBootTest
public class MemberTest {
    /*
        테스트코드 주 목적
        코드가 잘 작동 되는지 먼저 확인 해보는 곳

        MemberServiceImpl.save() 메서드가 잘 동작하는지를 테스트

        회원가입 테스트
        save.html 에서 회원정보 입력 후 가입 클릭
        DB 확인

     */
    @Autowired
    private MemberService ms;

    @Test
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() {
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("1");
        memberSaveDTO.setMemberPassword("1");
        memberSaveDTO.setMemberName("1");
        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional  // 테스트 시작할 때 새로운 트랜잭션 시작
    @Rollback   // 테스트 종료 후 롤백 수행
    @DisplayName("회원조회 테스트")
    public void memberDetailTest() {
        // given : 테스트 조건 설정
        // 1. 새로운 회원을 등록하고 해당 회원의 번호(member_id)를 가져옴.
        // 1.1 테스트용 데이터 객체 생성
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("조회용회원이메일1", "조회용회원비번1", "조회용회원이름1");
        // 1.2 테스트용 데이터를 DB에 저장하고, member_id를 가져옴.
        Long memberId = ms.save(memberSaveDTO);
        // when : 테스트 수행
        // 2. 위에서 가져온 회원번호를 가지고 조회기능 수행
        MemberDetailDTO findMember = ms.findById(memberId);
        // then : 테스트 결과 검증
        // 3. 1번에서 가입한 회원의 정보와 2번에서 조회한 회원의 정보가 일치하면 테스트 통과 일치하지 않으면 테스트 실패
        // memberSaveDTO의 이메일값과 findMember의 이메일 값이 일치하는지 확인.
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
        // 좌변과 우변이 같은지 비교해주는 코드
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인테스트")
    public void loginTest() {
        /*
            1. 테스트용 회원가입 (MemberSaveDTO)를이용
            2. 로그인용 객체 생성(MemberLoginDTO)를 이용
            1.2. 수행할 때 동일한 이메일, 패스워드를 사용하도록 함.
            3. 로그인 수행
            4. 로그인 결과가 true냐 false냐 확인
         */
        //given
        final String testMemberEmail = "로그인테스트이메일";
        //파이널 선언하면 다른변수 기입 못함
        final String testMemberPassword = "로그인테스트비밀번호";
        final String testMemberName = "로그인 테스트 이름";
        final String wrongMemberEmail = "이건true면안돼";
        // 1. 회원가입
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(testMemberEmail, testMemberPassword, testMemberName);
        ms. save(memberSaveDTO);
        //when
        // 2. 로그인
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(testMemberEmail, testMemberPassword);
        boolean loginResult = ms.login(memberLoginDTO);
        //then
        assertThat(loginResult).isEqualTo(true);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 테스트")
    public void memberListTest() {
        /*
            member_table 아무 데이터가 없는 상태에서
            3명의 회원을 가입시킨 후 memberList 사이즈를 조회하여 3이면 테스트 통과
         */
        // 1. 3명의 회원을 가입
//        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원1", "리스트회원pw1", "리스트회원이름1");
//        ms.save(new MemberSaveDTO());
//        memberSaveDTO = new MemberSaveDTO("리스트회원2", "리스트회원pw2", "리스트회원이름2");
//        ms.save(new MemberSaveDTO());
//        memberSaveDTO = new MemberSaveDTO("리스트회원3", "리스트회원pw3", "리스트회원이름3");
//        ms.save(new MemberSaveDTO());
//
//        for(int i=0; i<=3; i++) {
//            MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원"+i, "리스트회원pw"+i, "리스트회원이름"+i);
//            ms.save(memberSaveDTO);
//          ms.save(new MemberSaveDTO("리스트회원"+i, "리스트회원pw"+i, "리스트회원이름"+i);)
//    }
        // IntStream 방식, -> Arrow Function(화살표함수) / rangeClosed 1부터 3까지 수행을 한다 i는 매개변수
        IntStream.rangeClosed(1,3).forEach(i -> {
            ms.save(new MemberSaveDTO("조회이메일"+i, "조회비밀번호"+i, "조회이름"+i));
        });

        List<MemberDetailDTO> list = ms.findAll();
        assertThat(list.size()).isEqualTo(3);

    }
}



