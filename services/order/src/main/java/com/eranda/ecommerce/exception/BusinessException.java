package com.eranda.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class BusinessException extends RuntimeException {
     private final String msg;
}
