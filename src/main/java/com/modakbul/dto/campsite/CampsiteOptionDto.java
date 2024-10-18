package com.modakbul.dto.campsite;

import com.modakbul.entity.campsite.CampsiteOption; // CampsiteOption 엔티티 임포트
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CampsiteOptionDto {
    private int id;
    private String optionName;

    // CampsiteOptionDto를 CampsiteOption 엔티티로 변환하는 메서드
    public CampsiteOption toEntity() {
        return CampsiteOption.builder()
                .id(this.id)
                .optionName(this.optionName)
                .build();
    }

    // CampsiteOption 엔티티를 기반으로 DTO를 생성하는 생성자
    public CampsiteOptionDto(CampsiteOption option) {
        this.id = option.getId();
        this.optionName = option.getOptionName();
    }
}
