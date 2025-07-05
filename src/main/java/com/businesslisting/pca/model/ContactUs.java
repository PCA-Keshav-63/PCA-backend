package com.businesslisting.pca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_us")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String message;
}