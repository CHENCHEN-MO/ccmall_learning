package com.ccmall.service.imp;

import com.ccmall.service.IFileService;
import com.ccmall.util.FTPUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by geely
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        //获取文件名
        String originFilename = file.getOriginalFilename();

        //获取扩展名
        String fileExtensionName = originFilename.substring(originFilename.lastIndexOf(".") +1);

        //A:abc.jpg
        //B:abv.jpg
        String uploadName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，文件名为{},上传路径为{},新文件名为{}",originFilename,path,uploadName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadName);

        try {
            file.transferTo(targetFile);
            //到这儿文件已经上传到我们指定的目录上，接下来要做的是将文件再传到FTP服务器上，并在上传到FTP服务器完成之后删除upload文件夹下的文件
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
        }
        return targetFile.getName();
    }
}
