package com.alrussy_dev.inventory_service.model;

import com.alrussy_dev.inventory_service.model.enums.ActionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActionInventory {
    @Id
    private String actionId;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private LocalDateTime actionDate;
    private Boolean isProcessed;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "action")
    @Builder.Default
    private List<LineProduct> lineProducts = new ArrayList<>();

    private Boolean isPublished;
    private String description;
}
