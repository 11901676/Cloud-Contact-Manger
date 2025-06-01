package com.scm.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ContactForm {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private boolean isFavouriteContact;
    private String websiteLink;
    private String linkedInLink;

    public boolean getIsFavouriteContact()
    {
        return isFavouriteContact;
    }
}
