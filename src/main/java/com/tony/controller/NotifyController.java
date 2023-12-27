package com.tony.controller;


import com.tony.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class NotifyController {


    @Autowired
    private NotifyService notifyService;


    @GetMapping("/line")
    public String lineNotify(@RequestParam(required = true) String msg,
                             @RequestParam(required = false) String imageUrl) throws MalformedURLException {
        notifyService.lineNotify(msg, imageUrl);
        return "line notify successfully";
    }


    @GetMapping("/gmail")
    public String gmailNotify(@RequestParam(required = true) String to,
                              @RequestParam(required = false) List<String> cc,
                              @RequestParam(required = true) String subject,
                              @RequestParam(required = true) String content,
                              @RequestParam(required = false) List<String> imagePaths,
                              @RequestParam(required = false) String imageUrls,
                              @RequestParam(required = false) List<String> attachmentPaths) throws MessagingException, MalformedURLException {
        List<String> toList = Arrays.asList(to.split(","));
//        List<String> imageUrlList = Arrays.asList(imageUrls.split(","));
        List<String> imageUrlList = null;

        List<String> imagePathList = Arrays.asList( new String[]{ "\\files\\cat.jpg", "\\files\\insert.png" } );

        notifyService.gmailNotify(toList, cc, subject, content, imagePathList, imageUrlList,  attachmentPaths);
        return "gmail notify successfully";
    }


}
