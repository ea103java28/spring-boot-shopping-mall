package com.tony.service;

import org.springframework.messaging.MessagingException;

import java.net.MalformedURLException;
import java.util.List;

public interface NotifyService {

    void lineNotify(String msg, String imageUrl) throws MalformedURLException;
    void gmailNotify(List<String> to,
                    List<String> cc,
                    String subject,
                    String content,
                    List<String> imagePaths,
                     List<String> imageUrls,
                    List<String> attachmentPaths
                    ) throws MessagingException, javax.mail.MessagingException, MalformedURLException;

}
