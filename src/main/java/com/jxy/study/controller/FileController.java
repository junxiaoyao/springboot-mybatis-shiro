package com.jxy.study.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.jxy.study.dao.FileDao;
import com.jxy.study.entity.FileUpload;
import com.jxy.study.util.ThreadLocalUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description
 * @author: jxy
 * @create: 2019-09-30 15:18
 */
@Controller
@RequestMapping("file")
public class FileController {

    @RequestMapping(method = RequestMethod.GET)
    public String upload() {
        System.out.println("controller:"+ ThreadLocalUtil.getValue());
        return "file/fileUpload";
    }

    @Autowired
    private FileDao fileDao;

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        FileUpload fileUpload = new FileUpload();
        fileUpload.setData(bytes);
        fileUpload.setFileName(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));
        fileUpload.setFileType(file.getOriginalFilename());
        fileDao.uploadFile(fileUpload);
        System.out.println();
    }

    @RequestMapping(value = "download2", method = RequestMethod.GET)
    @ResponseBody
    public Object download2(String fileName, HttpServletResponse response) {

        FileUpload fileUpload = fileDao.getByName(fileName);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", fileUpload.getData());
        return JSONUtils.toJSONString(map);
    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public void download(String fileName, HttpServletResponse response) {
        OutputStream out = null;
        try {
            FileUpload fileUpload = fileDao.getByName(fileName);
            if (null != fileUpload && fileUpload.getData().length > 0) {
                response.reset();
                out = response.getOutputStream();
                response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileUpload.getFileType()).getBytes("gb2312"), "ISO8859_1"));
                response.addHeader("Content-Length", "" + fileUpload.getData().length);
                out.write(fileUpload.getData());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
