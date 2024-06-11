package com.kh.totalEx.DTO;

import com.kh.totalEx.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDTO {
    private String name;
    private String email;
    private String image;
    private LocalDateTime regDate;

    public static MemberResDTO of(Member member) {
        return MemberResDTO.builder()
                .name(member.getName())
                .email(member.getEmail())
                .image(member.getImage())
                .regDate(member.getRegDate())
                .build();
    }

}
