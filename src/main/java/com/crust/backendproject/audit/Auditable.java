package com.crust.backendproject.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @JsonIgnore
    @CreatedBy
    private Long createdBy;

    @JsonProperty
    @CreatedDate
    private Instant createdAt;

    @JsonIgnore
    @LastModifiedBy
    private Long updatedBy;

    @JsonProperty
    @LastModifiedDate
    private Instant updatedAt;

}
