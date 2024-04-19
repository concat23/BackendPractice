package com.practice.mysource.entity;

import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.exception.ApiException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmn_roles")
public class Role extends Auditable{
    @Column
    private String name;
    private String authorities;

}
