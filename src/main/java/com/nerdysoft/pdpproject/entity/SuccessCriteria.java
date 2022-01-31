package com.nerdysoft.pdpproject.entity;

import com.nerdysoft.pdpproject.entity.dto.SCDto;
import com.nerdysoft.pdpproject.entity.enums.CriteriaStatus;
import com.nerdysoft.pdpproject.validation.annotations.CreateCStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @Size(min = 3, max = 20, message = "Size of title must be between 3 and 20 chars")
    @NotEmpty(message = "Title must be filled")
    private String title;

    @Size(min = 5, max = 80, message = "Size of description must be between 5 and 80 chars")
    @NotEmpty(message = "Description must be filled")
    private String description;

    @CreateCStatus()
    @Enumerated(EnumType.STRING)
    private CriteriaStatus criteriaStatus;

    @ManyToOne
    private Goal goal;

    public static SuccessCriteria from(SCDto successCriteriaDto){
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
