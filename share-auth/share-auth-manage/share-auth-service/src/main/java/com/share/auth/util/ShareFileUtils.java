package com.share.auth.util;

import com.share.file.domain.FileInfoVO;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author xrp
 * @date 2020/12/4 0004
 */
@Component
public class ShareFileUtils {

    /**
     * 文件删除接口
     * @param fileUrlId 文件的地址
     * @return FileInfoVO
     * @author xrp
     * */
    public FileInfoVO deleteFile(String fileUrlId){
        //使用方系统ID
        String systemId = MessageUtil.getApplicationCode();
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setSystemId(systemId);
        fileInfoVO.setFileKey(fileUrlId);
        return fileInfoVO;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
