package com.minden.ai.scm.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Version
    @Column(name = "LOCK_FLAG")
    private Integer lockFlag;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now(ZoneId.systemDefault());
        lockFlag = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now(ZoneId.systemDefault());
    }
}
