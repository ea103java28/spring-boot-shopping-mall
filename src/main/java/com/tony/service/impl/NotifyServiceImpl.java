package com.tony.service.impl;

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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:env.properties")
@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${line-token}")
    String lineToken;


    @Value("${sticker-package-id:8525}")
    String stickerPackageId;

    @Value("${sticker-id:16581290}")
    String stickerId;

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

    /*
    要先寫好html，在讓cid去對應
     */
    @Override
    public void gmailNotify(List<String> to,
                            List<String> cc, String subject,
                            String content,
                            List<String> imagePaths,
                            List<String> imageUrls,
                            List<String> attachmentPaths) throws MessagingException, MalformedURLException {


        List<ClassPathResource> imageResources = null;
        List<URL> imageUrlResources = null;
        List<FileSystemResource> attachmentResources = null;

        // image local path
        if (imagePaths != null) {
            imageResources = imagePaths.stream()
                    .map(imagePath -> new ClassPathResource(imagePath))
                    .collect(Collectors.toList());
        }


        // image url path
        if (imageUrls != null) {
            imageUrlResources = imageUrls.stream()
                    .map(imageUrl -> {
                        try {
                            return new URL(imageUrl);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        }


        // 添加附件
        if (attachmentPaths != null) {
            attachmentResources = attachmentPaths.stream()
                    .map(a -> new FileSystemResource(a))
                    .collect(Collectors.toList());
        }


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


        helper.setSubject(subject);


        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("  body { background-color: pink; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h3>").append(content).append("</h3>");


        if (imageResources != null) {
            imageResources.stream()
                    .forEach(i -> htmlBody.append("<img src='cid:").append(i.getFilename()).append("' height='100px' width='auto' >").append("</img>"));
        }

        if (imageUrlResources != null) {
            imageUrlResources.stream()
                    .forEach(i -> htmlBody.append("<img src='cid:").append(i.getPath()).append("' height='100px' width='auto' >").append("</img>"));
        }


        htmlBody.append("<br><br><br>")
                .append("<h3>此為自動寄信功能，請勿回信!</h3>")
                .append("</body>")
                .append("</html>");


        helper.setText(htmlBody.toString(), true); // 使用true表示正文内容为HTML

        if (to != null) {
            helper.setTo(to.toArray(new String[0]));
        }

        if (cc != null) {
            helper.setCc(cc.toArray(new String[0]));
        }

        if (imageResources != null) {
            for (ClassPathResource i : imageResources) {
                helper.addInline(i.getFilename(), i);
            }
        }

        if (imageUrlResources != null) {
            for (URL i : imageUrlResources) {
                FileSystemResource imageResource = new FileSystemResource(i.getFile());
                helper.addInline(i.getPath(), imageResource);
            }
        }

        if (attachmentResources != null) {
            for (FileSystemResource f : attachmentResources) {
                helper.addAttachment(f.getFilename(), f);
            }
        }


        mailSender.send(message);

    }
}
