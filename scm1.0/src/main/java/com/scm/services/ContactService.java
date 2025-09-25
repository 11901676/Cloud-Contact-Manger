package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;

public interface ContactService {
    //Save contact
    Contact save(Contact contact);

    //Update contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get Contact by id
    Contact getById(String id) throws ResourceNotFoundException;

    //delete contact
    void deleteContact(String id);

    //search contact
    List<Contact> search(String name, String email, String phoneNumber);

    //search contact by user id
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortFied, String sortDirection);
    
}
