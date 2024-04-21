package com.practice.mysource.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.QAbstractAuditable;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(QAbstractAuditable.class)
@JsonIgnoreProperties(value = {"createdAt","updatedAt"}, allowGetters = true)
public abstract class Auditable {

    public Auditable() {
        Random random = new Random();
        this.createdBy = random.nextLong();
        this.createdAt = now();
    }

    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;

//    @OneToMany
//    @JoinColumn(
//            name = "owner_id",
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(name="fk_user_owner", value = ConstraintMode.CONSTRAINT)
//    )
//    private List<User> owner;

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt ;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    public void beforePersist(){
//        Long userId = RequestContext.getUserId();
        Long userId = 0L;
//        if (userId == null) {
//            throw new ApiException("Cannot update entity without user ID in Request Context for this thread");
//        }
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }


    @PreUpdate
    public void beforeUpdate(){
//        Long userId = RequestContext.getUserId();
        Long userId = 0L;
//        if (userId == null) {
//            throw new ApiException("Cannot persist entity without user ID in Request Context for this thread");
//        }

        setCreatedAt(now());
        setCreatedBy(userId);
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }
}


