package org.example.noteappserver.global.exception;

import org.springframework.security.core.AuthenticationException;

public class Exception401 extends AuthenticationException {
    public Exception401(String message) {
        super(message);
    }
}
