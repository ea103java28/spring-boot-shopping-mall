package com.tony.service.impl;

import com.tony.config.MailConfig;
import com.tony.dto.GmailRequest;
import com.tony.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

@PropertySource({
        "classpath:line.properties",
        "classpath:mail.properties"
})
@Service
public class NotifyServiceImpl implements NotifyService {



    @Value("${line-token}")
    String lineToken;


    @Value("${sticker-package-id:8525}")
    String stickerPackageId;

    @Value("${sticker-id:16581290}")
    String stickerId;


    @Autowired
    private MailConfig mailConfig;


    private JavaMailSenderImpl mailSender;


    @Override
    public void lineNotify(String msg, String imageUrl) throws MalformedURLException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();


        requestHeaders.set("Authorization", lineToken);
        requestHeaders.set("ContentType", "application/x-www-form-urlencoded");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();

        if (imageUrl != null) {
//            Resource resource = new ClassPathResource(imageUrl);
            Resource resource = new UrlResource(imageUrl);
            body.add("imageFile", resource);
        }


        body.add("message", msg);
        body.add("stickerPackageId", stickerPackageId);
        body.add("stickerId", stickerId);


        HttpEntity requestEntity = new HttpEntity(body, requestHeaders);

        restTemplate.exchange(
                "https://notify-api.line.me/api/notify",
                HttpMethod.POST,
                requestEntity,
                Object.class
        );


    }

    @Override
    public void gmailNotify(GmailRequest gmailRequest) throws MessagingException, IOException {


        List<FileSystemResource> attachmentResources = null;
        Function<String, String> pathImageTypeFunction =
                i -> {
                    String[] iArr = i.split("\\.");
                    return iArr[iArr.length - 1];
                };



        // 添加附件
        if (gmailRequest.getAttachmentPaths() != null) {
            attachmentResources = gmailRequest.getAttachmentPaths().stream()
                    .map(a -> new FileSystemResource(a))
                    .collect(Collectors.toList());
        }


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


        helper.setSubject(gmailRequest.getSubject());


        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("  body { background-color: pink; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h3>").append(gmailRequest.getContent()).append("</h3>");


        if (gmailRequest.getImagePaths() != null) {
            for (String i : gmailRequest.getImagePaths()
            ) {
                htmlBody.append("<img src='data:image/").append(pathImageTypeFunction.apply(i)).append(";base64,")
                        .append(encodeImageToBase64(i)).append("' height='100px' width='auto' >").append("</img>");
            }
        }




        if (gmailRequest.getImageUrls() != null) {
            gmailRequest.getImageUrls()
                    .stream()
                    .filter(i -> i.endsWith(".jpg"))
                    .forEach(url -> htmlBody.append("<img src='").append(url).append("' height='100px' width='auto' >").append("</img>"));
        }


        htmlBody.append("<br><br><br>")
                .append("<h3>此為自動寄信功能，請勿回信!</h3>")
                .append("</body>")
                .append("</html>");


        helper.setText(htmlBody.toString(), true); // 使用true表示正文内容为HTML
        System.out.println(htmlBody.toString());


        if (gmailRequest.getTo() != null) {
            helper.setTo(gmailRequest.getTo().toArray(new String[0]));
        }

        if (gmailRequest.getCc() != null) {
            helper.setCc(gmailRequest.getCc().toArray(new String[0]));
        }


        if (attachmentResources != null) {
            for (FileSystemResource f : attachmentResources) {
                helper.addAttachment(f.getFilename(), f);
            }
        }


        mailSender.send(message);

    }


    private String encodeImageToBase64(String imagePath) throws IOException {

//        String currentWorkingDirectory = System.getProperty("user.dir");
//        System.out.println(currentWorkingDirectory);
        Path path = Paths.get(imagePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + imagePath);
        }

        // read image
        byte[] imageBytes = Files.readAllBytes(path);
        // byte to base64
//        System.out.println(Base64.getEncoder().encodeToString(imageBytes));
        return Base64.getEncoder().encodeToString(imageBytes);

    }


    @PostConstruct
    private void init() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHost());
        mailSender.setPort(mailConfig.getPort());
        mailSender.setUsername(mailConfig.getUsername());
        mailSender.setPassword(mailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", mailConfig.isAuthEnabled());
        props.put("mail.smtp.starttls.enable", mailConfig.isStarttlsEnabled());
        props.put("mail.transport.protocol", mailConfig.getProtocol());
    }



}
