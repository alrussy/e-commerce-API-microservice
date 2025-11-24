package com.alrussy_dev.inventory_service.model;

import com.alrussy_dev.inventory_service.model.enums.ActionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ActionInventory extends Audition {
    @Id
    private String actionId;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private LocalDateTime actionDate;
    private Boolean isProcessed;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "action")
    @Builder.Default
    private Set<LineProduct> lineProducts = new HashSet<LineProduct>();

    private Boolean isPublished;
    private String description;
}
