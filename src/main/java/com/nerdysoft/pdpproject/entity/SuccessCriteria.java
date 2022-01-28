package com.nerdysoft.pdpproject.entity;

import com.nerdysoft.pdpproject.entity.dto.SuccessCriteriaDto;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SuccessCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private CriteriaStatus criteriaStatus;

    @ManyToOne
    private Goal goal;

    public static SuccessCriteria from(SuccessCriteriaDto successCriteriaDto){
        SuccessCriteria criteria = new SuccessCriteria();

        criteria.setTitle(successCriteriaDto.getTitle());
        criteria.setDescription(successCriteriaDto.getDescription());
        criteria.setCriteriaStatus(successCriteriaDto.getCriteriaStatus());

        return criteria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SuccessCriteria criteria = (SuccessCriteria) o;
        return id != null && Objects.equals(id, criteria.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
