package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String address;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile contactImage;
 
    public boolean isFavourite()
    {
        return this.favourite;
    }
}
