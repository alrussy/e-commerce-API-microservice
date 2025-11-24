package com.alrussy_dev.order_service.error;

public class CommandException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CommandException(String msg) {
        super(msg);
    }
}
