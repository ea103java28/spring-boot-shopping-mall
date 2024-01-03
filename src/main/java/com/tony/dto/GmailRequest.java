package com.tony.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class GmailRequest {
    @NotEmpty
    private List<String> to;

    private List<String> cc;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;

    private List<String> imagePaths;
    private List<String> imageUrls;
    private List<String> attachmentPaths;

}
