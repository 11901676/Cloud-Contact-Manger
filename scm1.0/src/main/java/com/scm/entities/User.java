package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class User implements UserDetails{

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false)
    public String userName;
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;

    @Column(length = 1000)
    private String about;


    @Column(length = 1000)
    private String profilePic;

    //information
    private boolean enabled = false;
    private boolean emailVerified;
    private boolean phoneVerified;

    //Auth Methods: SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB
    @Enumerated(value = EnumType.STRING)
    private AuthProviders authMethod = AuthProviders.SELF;
    private String authProviderId;

    //One user can have many contacts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    private String emailToken;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        //list of roles[USER, ADMIN]
        //collection of SimpleGrantedAuthority[roles{ADMIN, USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return roles;
    }

    @Override
    public String getUsername() {
        //For this project email id is our username
        return this.email;
    }

    public String getDisplayName() {
    return this.userName;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

}
