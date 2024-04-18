package org.zerok.apiserver.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("{org.zerock.upload.path}")
    private String uploadPath;

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        return null;
    }
}
