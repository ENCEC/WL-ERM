package com.share.auth.util;

import com.share.auth.constants.CodeFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

/**
 * @author chenxf
 * @date 2020-09-22 13:42
 */
@Slf4j
public class VerifyImageUtil {
    private static final int BOLD = 5;
    private static final String IMG_FILE_TYPE = "jpg";
    private static final String TEMP_IMG_FILE_TYPE = "png";

    /**
     * 私有构造器
     *
     * @param
     * @return
     * @author huanghwh
     * @date 2021/4/30 上午11:32
     */
    private VerifyImageUtil() {
    }

    /**
     * 根据模板切图
     *
     * @param templateFile
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static Map<String, Object> pictureTemplatesCut(File templateFile, File targetFile) throws IOException {
        Map<String, Object> pictureMap = new HashMap<>(16);
        // 模板图
        BufferedImage imageTemplate = ImageIO.read(templateFile);
        int templateWidth = imageTemplate.getWidth();
        int templateHeight = imageTemplate.getHeight();

        // 原图
        BufferedImage oriImage = ImageIO.read(targetFile);
        oriImage = ShareFileUtils.resize(oriImage, CodeFinal.BIG_IMAGE_WIDTH, oriImage.getHeight());
        int oriImageWidth = oriImage.getWidth();
        int oriImageHeight = oriImage.getHeight();

        //随机生成抠图坐标X,Y
        //X轴距离右端targetWidth  Y轴距离底部targetHeight以上
        SecureRandom random = new SecureRandom();
        int widthRandom = random.nextInt(oriImageWidth - 2 * templateWidth) + templateWidth;
        int heightRandom = random.nextInt(oriImageHeight - templateHeight);
        log.info("原图大小{} x {},随机生成的坐标 X,Y 为（{}，{}）", oriImageWidth, oriImageHeight, widthRandom, heightRandom);

        // 新建一个和模板一样大小的图像，TYPE_4BYTE_ABGR表示具有8位RGBA颜色分量的图像，正常取imageTemplate.getType()
        BufferedImage newImage = new BufferedImage(templateWidth, templateHeight, imageTemplate.getType());
        //得到画笔对象
        Graphics2D graphics = newImage.createGraphics();
        //如果需要生成RGB格式，需要做如下配置,Transparency 设置透明
        newImage = graphics.getDeviceConfiguration().createCompatibleImage(templateWidth, templateHeight, Transparency.TRANSLUCENT);

        // 新建的图像根据模板颜色赋值,源图生成遮罩
        cutByTemplate(oriImage, imageTemplate, newImage, widthRandom, heightRandom);

        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(BOLD, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();
        // 新建流。
        ByteArrayOutputStream newImageOs = new ByteArrayOutputStream();
        // 利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        ImageIO.write(newImage, TEMP_IMG_FILE_TYPE, newImageOs);
        byte[] newImagebyte = newImageOs.toByteArray();
        // 新建流。
        ByteArrayOutputStream oriImagesOs = new ByteArrayOutputStream();
        // 利用ImageIO类提供的write方法，将bi以jpg图片的数据模式写入流。
        ImageIO.write(oriImage, IMG_FILE_TYPE, oriImagesOs);
        byte[] oriImageByte = oriImagesOs.toByteArray();

        pictureMap.put("smallImage", "data:image/jpg;base64," + Base64Utils.encodeToString(newImagebyte));
        pictureMap.put("bigImage", "data:image/jpg;base64," + Base64Utils.encodeToString(oriImageByte));
        log.info("widthRandom:" + widthRandom);
        pictureMap.put("xWidth", widthRandom);
        pictureMap.put("yHeight", heightRandom);
        return pictureMap;
    }

    /**
     * @param oriImage      原图
     * @param templateImage 模板图
     * @param newImage      新抠出的小图
     * @param x             随机扣取坐标X
     * @param y             随机扣取坐标y
     * @throws Exception
     */
    private static void cutByTemplate(BufferedImage oriImage, BufferedImage templateImage, BufferedImage newImage, int x, int y) {
        //临时数组遍历用于高斯模糊存周边像素值
        int[][] martrix = new int[3][3];
        int[] values = new int[9];

        int xLength = templateImage.getWidth();
        int yLength = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < xLength; i++) {
            // 模板图片高度
            for (int j = 0; j < yLength; j++) {
                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int rgb = templateImage.getRGB(i, j);
                if (rgb < 0) {
                    newImage.setRGB(i, j, oriImage.getRGB(x + i, y + j));

                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, y + j, values);
                    fillMatrix(martrix, values);
                    oriImage.setRGB(x + i, y + j, avgMatrix(martrix));
                }

                //防止数组越界判断
                if (i == (xLength - 1) || j == (yLength - 1)) {
                    continue;
                }
                int rightRgb = templateImage.getRGB(i + 1, j);
                int downRgb = templateImage.getRGB(i, j + 1);
                // 描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                boolean flag = judgeTheCriticalContour(rgb, rightRgb, downRgb);
                if (flag) {
                    newImage.setRGB(i, j, Color.white.getRGB());
                    oriImage.setRGB(x + i, y + j, Color.white.getRGB());
                }
            }
        }
    }

    /**
     * 判断是否临界轮廓点
     *
     * @param rgb
     * @param rightRgb
     * @param downRgb
     * @return
     * @author huanghwh
     * @date 2021/5/7 上午10:32
     */
    private static boolean judgeTheCriticalContour(int rgb, int rightRgb, int downRgb) {
        return (rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0);
    }

    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        int extraLength = 3;
        for (int i = xStart; i < extraLength + xStart; i++) {
            for (int j = yStart; j < extraLength + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;

                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);

            }
        }
    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();
    }

    /**
     * 获取图片，由于spring boot打包成jar之后，获取到获取不到resources里头的图片，对此进行处理
     *
     * @param path
     * @return
     * @author pangxianhe
     * @date 2020年1月2日
     */
    public static java.util.List<File> queryFileList(String path) {

        //获取容器资源解析器
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<File> filelist = new ArrayList<>();
        // 获取远程服务器IP和端口
        try {
            //获取所有匹配的文件
            Resource[] resources = resolver.getResources(path);

            for (Resource resource : resources) {
                //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
                InputStream stream = resource.getInputStream();
                String targetFilePath = resource.getFilename();
                File ttfFile = new File(targetFilePath);
                FileUtils.copyInputStreamToFile(stream, ttfFile);
                filelist.add(ttfFile);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("Facet aop failed error {}", e.getLocalizedMessage());
        }
        return filelist;
    }


    /**
     * 根据模板切图-注册
     *
     * @param templateFile
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static Map<String, Object> pictureTemplatesCutRe(File templateFile, File targetFile) throws IOException {
        Map<String, Object> pictureMap = new HashMap<>(16);
        // 模板图
        BufferedImage imageTemplate = ImageIO.read(templateFile);
        int templateWidth = imageTemplate.getWidth();
        int templateHeight = imageTemplate.getHeight();

        // 原图
        BufferedImage oriImage = ImageIO.read(targetFile);
        oriImage = ShareFileUtils.resize(oriImage, CodeFinal.BIG_IMAGE_WIDTH_RE, oriImage.getHeight());
        int oriImageWidth = oriImage.getWidth();
        int oriImageHeight = oriImage.getHeight();
        System.out.println("oriImageWidth  " + oriImageWidth);
        System.out.println("oriImageHeight  " + oriImageHeight);

        //随机生成抠图坐标X,Y
        //X轴距离右端targetWidth  Y轴距离底部targetHeight以上
        SecureRandom random = new SecureRandom();
        int widthRandom = random.nextInt(oriImageWidth - 2 * templateWidth) + templateWidth;
        int heightRandom = random.nextInt(oriImageHeight - templateHeight);
        log.info("原图大小{} x {},随机生成的坐标 X,Y 为（{}，{}）", oriImageWidth, oriImageHeight, widthRandom, heightRandom);

        // 新建一个和模板一样大小的图像，TYPE_4BYTE_ABGR表示具有8位RGBA颜色分量的图像，正常取imageTemplate.getType()
        BufferedImage newImage = new BufferedImage(templateWidth, templateHeight, imageTemplate.getType());
        //得到画笔对象
        Graphics2D graphics = newImage.createGraphics();
        //如果需要生成RGB格式，需要做如下配置,Transparency 设置透明
        newImage = graphics.getDeviceConfiguration().createCompatibleImage(templateWidth, templateHeight, Transparency.TRANSLUCENT);

        // 新建的图像根据模板颜色赋值,源图生成遮罩
        cutByTemplate(oriImage, imageTemplate, newImage, widthRandom, heightRandom);

        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(BOLD, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();
        // 新建流。
        ByteArrayOutputStream newImageOs = new ByteArrayOutputStream();
        // 利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        ImageIO.write(newImage, TEMP_IMG_FILE_TYPE, newImageOs);
        byte[] newImagebyte = newImageOs.toByteArray();
        // 新建流。
        ByteArrayOutputStream oriImagesOs = new ByteArrayOutputStream();
        // 利用ImageIO类提供的write方法，将bi以jpg图片的数据模式写入流。
        ImageIO.write(oriImage, IMG_FILE_TYPE, oriImagesOs);
        byte[] oriImageByte = oriImagesOs.toByteArray();

        pictureMap.put("smallImage", "data:image/jpg;base64," + Base64Utils.encodeToString(newImagebyte));
        pictureMap.put("bigImage", "data:image/jpg;base64," + Base64Utils.encodeToString(oriImageByte));
        log.info("widthRandom:" + widthRandom);
        pictureMap.put("xWidth", widthRandom);
        pictureMap.put("yHeight", heightRandom);
        return pictureMap;
    }
}
