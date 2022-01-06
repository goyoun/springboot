package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity member) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(member.getId());
        memberDetailDTO.setMemberEmail(member.getMemberEmail());
        memberDetailDTO.setMemberPassword(member.getMemberPassword());
        memberDetailDTO.setMemberName(member.getMemberName());
        return memberDetailDTO;

    }
}
