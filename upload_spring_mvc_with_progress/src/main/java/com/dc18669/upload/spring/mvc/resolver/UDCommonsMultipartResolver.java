package com.dc18669.upload.spring.mvc.resolver;

import com.dc18669.upload.spring.mvc.listener.UDProgressListener;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UDCommonsMultipartResolver extends CommonsMultipartResolver {

    @Autowired
    private UDProgressListener udProgressListener;

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        // 获取字符编码格式
        String determineEncoding = determineEncoding(request);
        // 文件预设
        FileUpload fileUpload = prepareFileUpload(determineEncoding);
        // 设置session会话对象
        udProgressListener.setSession(request.getSession());
        // 绑定进度监听
        fileUpload.setProgressListener(udProgressListener);

        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, determineEncoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }
}
