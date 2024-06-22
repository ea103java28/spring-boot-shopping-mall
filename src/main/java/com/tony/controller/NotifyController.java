//package com.tony.controller;
//
//
//import com.tony.dto.GmailRequest;
//import com.tony.service.NotifyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.mail.MessagingException;
//import javax.validation.Valid;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Valid
//@RestController
//public class NotifyController {
//
//
//    @Autowired
//    private NotifyService notifyService;
//
//
//    @GetMapping("/line")
//    public String lineNotify(@RequestParam(required = true) String msg,
//                             @RequestParam(required = false) String imageUrl) throws MalformedURLException {
//        notifyService.lineNotify(msg, imageUrl);
//        return "line notify successfully";
//    }
//
//
//    @PostMapping("/gmail")
//    public String gmailNotify(@RequestBody @Valid GmailRequest gmailRequest) throws MessagingException, IOException {
//
//        notifyService.gmailNotify(gmailRequest);
//        return "gmail notify successfully";
//    }
//
//
//}
