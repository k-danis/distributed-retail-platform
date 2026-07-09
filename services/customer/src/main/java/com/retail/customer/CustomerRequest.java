package com.retail.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer first name is required")
        String firstname, // TODO
        @NotNull(message = "Customer last name is required")
        String lastname, // TODO
        @Email(message = "Customer email is invalid") @NotNull(message = "Customer email is required")
        String email,
        Address address
) {
}
