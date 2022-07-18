package com.share.auth.controller;

import com.gillion.saas.redis.SassRedisInterface;
import com.share.auth.util.VerifyImageUtil;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenxf
 * @date 2021-01-13 15:41
 */
@Api("滑动解锁图片控制器")
@Controller
public class ImageController {

    @Autowired
    private SassRedisInterface sassRedisInterface;

    @Value("${imgPath}")
    private String imgPath;
    @Value("${imgPathRe}")
    private String imgPathRe;
    @Value("${tempImgPath}")
    private String tempImgPath;

    /**
     * 获取验证码
     *
     * @return
     * @throws Exception
     * @author pangxianhe
     * @date 2020年1月2日
     */
    @PostMapping(value = "/getImageVerifyCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @ResponseBody
    public ResultHelper<Map<String, Object>> getImageVerifyCode() throws IOException {

        // 读取图库目录
        List<File> imgList = VerifyImageUtil.queryFileList(imgPath);
        int randNum = new SecureRandom().nextInt(imgList.size());
        File targetFile = imgList.get(randNum);
        List<File> tempimgList = VerifyImageUtil.queryFileList(tempImgPath);
        File tempImgFile = tempimgList.get(0);
        // 根据模板裁剪图片
        Map<String, Object> resultMap = VerifyImageUtil.pictureTemplatesCut(tempImgFile, targetFile);
        int xWidth = (int) resultMap.get("xWidth");
        String checkMoveId = UUID.randomUUID().toString().replace("-", "");
        sassRedisInterface.set(checkMoveId, String.valueOf(xWidth));
        sassRedisInterface.expire(checkMoveId, 600);
        // 移除map的滑动距离，不返回给前端
        resultMap.remove("xWidth");
        resultMap.put("checkMoveId", checkMoveId);
        return CommonResult.getSuccessResultData(resultMap);
    }

    /**
     * 获取验证码-注册
     *
     * @return
     * @throws Exception
     * @author cjh
     * @date 2021年11月23日
     */
    @PostMapping(value = "/getImageVerifyCodeRe")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @ResponseBody
    public ResultHelper<Map<String, Object>> getImageVerifyCodeRe() throws IOException {

        // 读取图库目录
        List<File> imgList = VerifyImageUtil.queryFileList(imgPathRe);

        int randNum = new SecureRandom().nextInt(imgList.size());
        File targetFile = imgList.get(randNum);
        List<File> tempimgList = VerifyImageUtil.queryFileList(tempImgPath);
        File tempImgFile = tempimgList.get(0);
        // 根据模板裁剪图片
        Map<String, Object> resultMap = VerifyImageUtil.pictureTemplatesCutRe(tempImgFile, targetFile);
        int xWidth = (int) resultMap.get("xWidth");
        String checkMoveId = UUID.randomUUID().toString().replace("-", "");
        sassRedisInterface.set(checkMoveId, String.valueOf(xWidth));
        sassRedisInterface.expire(checkMoveId, 600);
        // 移除map的滑动距离，不返回给前端
        resultMap.remove("xWidth");
        resultMap.put("checkMoveId", checkMoveId);
        return CommonResult.getSuccessResultData(resultMap);
    }
}
