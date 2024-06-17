package com.tony.service;

import com.tony.dto.GmailRequest;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface NotifyService {

    void lineNotify(String msg, String imageUrl) throws MalformedURLException;
    void gmailNotify(GmailRequest gmailRequest) throws MessagingException, javax.mail.MessagingException, IOException;

}
