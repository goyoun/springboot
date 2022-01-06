package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor // 모든필드를 매개변수로 하는 생성자
@NoArgsConstructor  // 기본생성자를 만들어주는 롬복 기능
public class MemberSaveDTO {
    @NotBlank(message = "이메일은 필수 입니다.")
    private String memberEmail;
    @NotBlank
    // @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String memberPassword;
    private String memberName;

}

