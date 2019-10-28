package com.ego.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/23 16:03.
 */
public interface PicService {
    /**
     * 文件上传
     * @param file 上传的文件
     * @return  map 要求的返回格式
     */
    Map<String,Object> upload(MultipartFile file);
}
