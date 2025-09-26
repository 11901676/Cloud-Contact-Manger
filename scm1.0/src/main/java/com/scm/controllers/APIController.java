package com.scm.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.services.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) throws ResourceNotFoundException
    {
        return contactService.getById(contactId);
    }
}
