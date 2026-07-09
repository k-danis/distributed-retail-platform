package com.retail.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Customer {

    @Id
    private String id; // TODO
    private String firstname; // TODO
    private String lastname; // TODO
    private String email;
    private Address address;
}
