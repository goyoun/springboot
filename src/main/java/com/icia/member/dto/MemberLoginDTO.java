package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {

    // memberEmail 필드는 th:field 와 일치 시켜야함
    @NotBlank(message = "로그인시 이메일은 필수 입니다.")
    private String memberEmail;
    @NotBlank
    // @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String memberPassword;

}
