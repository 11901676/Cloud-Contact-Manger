package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService 
{
    String uploadImage(MultipartFile contactImage);

    String getURLFromPublicId(String publicId);
}
