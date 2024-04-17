package com.practice.mysource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
