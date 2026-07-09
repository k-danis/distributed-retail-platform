package com.retail.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) // TODO
@Data // TODO
public class CustomerNotFoundException extends RuntimeException {
    private final String msg;
}
