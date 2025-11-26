package com.alrussy_dev.procurement_service.error;

public class EntityNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2614510590365378955L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
