package com.scm.controllers;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.*;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.services.ContactService;
import com.scm.services.UserService;
import com.scm.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private imageService imageService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession httpSession) {

        if(result.hasErrors())
        {
            httpSession.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        String username = LoginHelper.getLoggedInUserEmail(authentication);

        User user = userService.getUserByEmail(username);
        
        logger.info("file Information: {}", contactForm.getContactImage().getOriginalFilename());


        Contact contact = new Contact();
        contact.setUser(user);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        if(contactForm.getContactImage() != null && contactForm.getContactImage().isEmpty() == false)
        {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            System.out.println("FileURL is: " + fileURL);

            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }
        
        contactService.save(contact);
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Showing data fetched from contact form");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println(contactForm.toString());
        
        httpSession.setAttribute("message", Message.builder()
            .content("Your contact has been added successfully")
            .type(MessageType.green)
            .build());

        return "redirect:/user/contacts/add";
    }
    
    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue =  "0") int page,
    @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction,
     Model model, Authentication authentication)
    {
        String username = LoginHelper.getLoggedInUserEmail(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
        @RequestParam(value="page", defaultValue = "0") int page,
        @RequestParam(value="sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value="direction", defaultValue = "asc") String direction,
        Model model,
        Authentication authentication)
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        logger.info("Field {}, Keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        Page<Contact> pageContact = null;

        var user =  userService.getUserByEmail(com.scm.helpers.LoginHelper.getLoggedInUserEmail(authentication));

        if(contactSearchForm.getField().equalsIgnoreCase("name"))
        {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email"))
        {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
        {
            pageContact = contactService.searchByPhone(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteConact(@PathVariable("contactId") String contactId,
    HttpSession httpSession)
    {
        contactService.deleteContact(contactId);
        httpSession.setAttribute("message",
        Message.builder()
        .content("Contact Deleted Successfully.")
        .type(MessageType.green)
        .build());
        return "redirect:/user/contacts";
    }

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId,
    Model model) throws ResourceNotFoundException
    {
        var contact = contactService.getById(contactId);
        System.out.println("Picture URL is: " + contact.getPicture());

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
    @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model) throws ResourceNotFoundException
    {
        if(bindingResult.hasErrors())
        {
            return "user/update_contact_view";
        }

        var contact = contactService.getById(contactId);

        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavourite(contactForm.isFavourite());


        //Processing image update
        if(contactForm.getContactImage() != null && contactForm.getContactImage().isEmpty() == false)
        {
            String fileName = UUID.randomUUID().toString();
            String imageURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
            contact.setCloudinaryImagePublicId(imageURL);
            contact.setPicture(imageURL);
            contactForm.setPicture(imageURL);
        }

        var updatedContact = contactService.update(contact);

        logger.info("Contact Updated : {}", updatedContact);

        model.addAttribute("message", Message.builder().content("Contact Updated successfully").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" +  contactId;
    }
}