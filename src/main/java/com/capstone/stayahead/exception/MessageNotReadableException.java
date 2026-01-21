package com.capstone.stayahead.exception;

public class MessageNotReadableException  extends  RuntimeException{
    public MessageNotReadableException() {
        super("Invalid Data. Please check again.");
    }
}
