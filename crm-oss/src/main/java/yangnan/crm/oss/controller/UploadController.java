package yangnan.crm.oss.controller;

import com.yangnan.crm.common.util.JSONResult;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yangnan.crm.oss.util.QiniuUtils;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/oss/upload")
public class UploadController {

    // SpringMVC把前台上传的信息封装到MultipartFile
    @RequestMapping("/uploadImage")
    public JSONResult uploadImage(MultipartFile file) {
        System.out.println("UploadController.uploadImage");
        // dc70f19aed4d4619812d0e751b7d8e09
        String name = UUID.randomUUID().toString().replace("-", "");
        // 返回前台上传的图片的名字   aaa.png
        String fileName = file.getOriginalFilename();
        // png
        String extension = FilenameUtils.getExtension(fileName);
        // dc70f19aed4d4619812d0e751b7d8e09.png
        String newFileName = name + "." + extension;

        try {
            QiniuUtils.upload2Qiniu(file.getBytes(), newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("上传图片：" + newFileName);

        return JSONResult.ok("上传成功", QiniuUtils.IMG_SERVER_QINIU + newFileName);
    }
}