package com.icia.member.entity;

import com.icia.member.dto.MemberSaveDTO;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name= "member_table")
public class MemberEntity {
    @Id // Pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name= "member_id") // 별도 컬럼이름 지정할때
    private Long id;

    // memberEmail : 크기 50, unique
    @Column(length = 50, unique = true)
    private String memberEmail;

    // memberPassword : 크기 20
    @Column(length = 20)
    private String memberPassword;

    // Column 생략하면 default 크기 255로 지정됨
    private String memberName;

    /*
        DTO클래스 객체를 전달받아 Entity 클래스 필드값으로 세팅하고
        Entity 객체를 리턴하는 메서드 선언

        static 메서드(정적메서드)
     */
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;
    }
}
