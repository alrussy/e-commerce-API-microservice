package com.alrussy_dev.inventory_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LineProduct {

    @EmbeddedId
    private InventoryId id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "actionId", updatable = false, insertable = false)
    private ActionInventory action;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "skuCode", updatable = false, insertable = false)
    private Inventory inventory;
}
