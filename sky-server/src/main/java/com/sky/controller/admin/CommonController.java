package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    private Result upload(MultipartFile file){


        String originalFilename = file.getOriginalFilename();

        log.info("Origin File name is {}", originalFilename);

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = null;

        try {
            String string = UUID.randomUUID().toString() + suffix ;
            url = aliOssUtil.upload(file.getBytes(), string);
        } catch (IOException e) {
            log.info("Error uplaod file {}", e.getMessage());
            return Result.error("Error uplaod file");
        }

        return Result.success(url);
    }


}
