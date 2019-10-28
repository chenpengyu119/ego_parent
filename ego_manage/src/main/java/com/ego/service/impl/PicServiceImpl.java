package com.ego.service.impl;

import com.ego.commons.utils.FastDFSClient;
import com.ego.commons.utils.IDUtils;
import com.ego.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/23 16:04.
 */
@Service
public class PicServiceImpl implements PicService {

    @Value("${custom.fastdfs.nginx}")
    private String url;

    @Override
    public Map<String,Object> upload(MultipartFile file) {

        Map<String,Object> map = new HashMap<>();

        String oldName = file.getOriginalFilename();
        // 文件名
        String fileName = IDUtils.genImageName()+oldName.substring(oldName.lastIndexOf("."));
        try {
            String[] result = FastDFSClient.uploadFile(file.getInputStream(), fileName);
            map.put("error", 0);
            // result[0] 组名  result[1]文件路径文件名
            map.put("url", url+result[0]+"/"+result[1]);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", e.getMessage());
            return map;
        }
    }
}
