package com.tony.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GmailRequest {
    @NotEmpty
    private List<String> to;

    private List<String> cc;

    @NotNull
    private String subject;

    @NotNull
    private String content;

    private List<String> imagePaths;
    private List<String> imageUrls;
    private List<String> attachmentPaths;

}
