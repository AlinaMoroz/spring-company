package org.example.database.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T extends Serializable> implements BaseEntity<T> {
    //Тут мы нашему случателю событий указываем какое поля за должно прослуш и для чего
    // при создании
    @CreatedDate
    private Instant createdAt;
    // При исправлении
    @LastModifiedDate
    private Instant modifiedAt;

    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
}
