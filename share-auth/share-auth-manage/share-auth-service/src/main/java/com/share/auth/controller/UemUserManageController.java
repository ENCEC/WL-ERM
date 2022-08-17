package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.train.api.AuthInfoInterface;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.entity.UemDept;
import com.share.auth.model.entity.UemProject;
import com.share.auth.service.UemUserManageService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 用户管理接口
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-25
 */
@Api("用户管理")
@RestController
@RequestMapping("/uemUserManage")
public class UemUserManageController {

    @Autowired
    private UemUserManageService uemUserManageService;

    @Autowired
    private AuthCenterInterface authCenterInterface;

    @Autowired
    private AuthInfoInterface authInfoInterface;

    /**
     * 根据用户名、姓名或启禁用状态查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return ResultHelper<Page < UemUserDto>>
     * @date 2022-07-25
     */
    @ApiOperation("根据用户名、姓名或启禁用状态查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户名", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "name", value = "真实姓名", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "isValid", value = "启用/禁用状态", dataTypeClass = Boolean.class, paramType = "body")
    })
    @PostMapping("/queryUemUser")
    public ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.queryUemUser(uemUserDto);
    }

    /**
     * 获取用户信息
     *
     * @date 2022-07-25
     */
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/getUemUser")
    public ResultHelper<UemUserDto> getUemUser(@RequestParam Long uemUserId) {
        return uemUserManageService.getUemUser(uemUserId);
    }

    /**
     * 启用/禁用用户
     *
     * @param uemUserDto 用户表封装类
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("启用/禁用用户")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto")
    @PostMapping("/uemUserStartStop")
    public ResultHelper<?> uemUserStartStop(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.uemUserStartStop(uemUserDto);
    }

    /**
     * 修改用户信息
     *
     * @param uemUserEditDTO 用户信息新增修改接口入参
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/editUemUser")
    @ApiImplicitParam(name = "uemUserEditDTO", value = "用户信息新增修改接口入参", required = true, dataType = "UemUserEditDTO")
    public ResultHelper<?> editUemUser(@RequestBody @Valid UemUserEditDTO uemUserEditDTO, BindingResult results) {
        if (results.hasErrors()) {
            //数据校验不通过
            FieldError fieldError = results.getFieldError();
            if (Objects.nonNull(fieldError)) {
                return CommonResult.getFaildResultData(fieldError.getDefaultMessage());
            }
        }
        return uemUserManageService.editUemUser(uemUserEditDTO);
    }

    /**
     * 删除用户信息
     *
     * @date 2022-07-25
     */
    @ApiOperation("删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @PostMapping("/deleteUemUser")
    public ResultHelper<?> deleteUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.deleteUemUser(uemUserDto.getUemUserId());
    }

    /**
     * 管理员新增用户
     *
     * @param uemUserEditDTO 用户信息新增修改接口入参
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("管理员新增用户")
    @ApiImplicitParam(name = "uemUserEditDTO", value = "用户信息新增修改接口入参", required = true, dataType = "UemUserEditDTO")
    @PostMapping(value = "/saveUemUser")
    public ResultHelper<?> saveUemUser(@RequestBody @Valid UemUserEditDTO uemUserEditDTO, BindingResult results) {
        if (results.hasErrors()) {
            //数据校验不通过
            FieldError fieldError = results.getFieldError();
            if (Objects.nonNull(fieldError)) {
                return CommonResult.getFaildResultData(fieldError.getDefaultMessage());
            }
        }
        return uemUserManageService.saveUemUser(uemUserEditDTO);
    }

    /**
     * 管理员重置用户密码
     *
     * @date 2022-07-25
     */
    @ApiOperation("管理员重置用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @PostMapping(value = "/resetUemUserPassword")
    public ResultHelper<?> resetUemUserPassword(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.resetUemUserPassword(uemUserDto.getUemUserId());
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/queryRoleListByUser")
    @ApiOperation("根据用户ID获取角色列表")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    public ResultHelper<List<SysRoleDTO>> queryRoleListByUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.queryRoleListByUser(uemUserDto);
    }

    /**
     * 赋予用户角色
     *
     * @param uemUserRoleDtoList 获取uemUserId和sysRoleId
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/bindUserAndRole")
    @ApiOperation("赋予用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long", paramType = "body"),
            @ApiImplicitParam(name = "sysRoleId", value = "角色ID", required = true, dataType = "Long", paramType = "body")
    })
    public ResultHelper<?> bindUserAndRole(@RequestBody List<UemUserRoleDto> uemUserRoleDtoList) {
        return uemUserManageService.bindUserAndRole(uemUserRoleDtoList);
    }

    /**
     * 清除一个用户的所有角色
     *
     * @param uemUserDto 用户信息
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/unbindAllRoleOfUser")
    @ApiOperation("清除一个用户的所有角色")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long", paramType = "body")
    public ResultHelper<?> unbindAllRoleOfUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.unbindAllRoleOfUser(uemUserDto.getUemUserId());
    }

    /**
     * 分页带条件查询员工信息
     *
     * @param uemUserDto 员工信息
     * @author wzr
     * @date 2022-08-01
     */
    @PostMapping("/queryStaffByPage")
    @ApiOperation(value = "分页带条件查询所有员工的信息")
    public Page<UemUserDto> queryStaffByPage(@RequestBody UemUserDto uemUserDto) {
        Page<UemUserDto> uemUserDtoPage = uemUserManageService.queryStaffByPage(uemUserDto);
        return uemUserDtoPage;
    }

    /**
     * 根据id查询对应员工信息
     *
     * @author wzr
     * @date 2022-08-02
     */
    @GetMapping("/queryStaffById")
    @ApiOperation(value = "根据id查询员工信息")
    public UemUserDto queryStaffById(@RequestParam Long uemUserId) {
        return uemUserManageService.queryStaffById(uemUserId);
    }

    /**
     * 编辑员工信息
     *
     * @author wzr
     * @date 2022-08-02
     */
    @PostMapping("/updateStaff")
    @ApiOperation(value = "编辑员工信息")
    public ResultHelper<Object> updateStaff(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.updateStaff(uemUserDto);
    }

    /**
     * 删除员工信息
     *
     * @author wzr
     * @date 2022-08-02
     */
    @GetMapping("/deleteStaff")
    @ApiOperation(value = "删除员工信息")
    public ResultHelper<Object> deleteStaff(@RequestParam Long uemUserId) {
        return uemUserManageService.deleteStaff(uemUserId);
    }


    /**
     * 下载员工简历
     *
     * @author wzr
     * @date 2022-08-03
     */
    @GetMapping("/download")
    @ApiOperation(value = "下载员工简历")
    public ResultHelper<?> download(String path, HttpServletResponse response) throws Exception {
        try {
            // path是指想要下载的文件的路径
            File file = new File(path);
            System.out.println(file);
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            System.out.println("文件后缀：" + ext);
            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            return CommonResult.getFaildResultData("下载失败");
        }
        return CommonResult.getSuccessResultData("下载成功");
    }
   /* public Map<String, Object> fileupload(MultipartFile file, HttpServletRequest req) {
        //首先要给文件找一个目录
        //先写返回值
        Map<String, Object> result = new HashMap<>();
        //再用pdf格式开始书写,先找原始的名字
        String originName = file.getOriginalFilename();
        //判断文件类型是不是pdf
        if (!originName.endsWith(".pdf")) {
            //如果不是的话，就返回类型
            result.put("status", "error");
            result.put("msg", "文件类型不对");
            return result;
        }
        //如果是正确的话，就需要写保存文件的文件夹
        //.format(new Date())的意思是 也就是格式化一个新的时间
        //Date会创建一个时间，然后会按照当前的sdf格式调用format将当前时间创建出来 直接调用new Date()可能会出现这种格式
        //再是getServletContext
        String format = sdf.format(new Date());
        //这也是一个临时的路径，项目重启之后，他就会变的
        String realPath = req.getServletContext().getRealPath("/") + format;
        //再是保存文件的文件夹
        File folder = new File(realPath);
        //如果不存在，就自己创建
        if (!folder.exists()) {
            folder.mkdirs();
        }
        System.out.println(folder);
        //String newName = UUID.randomUUID().toString() + ".pdf";
        //然后就可以保存了
        try {
            file.transferTo(new File(folder, originName));
            //这个还有一个url
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + originName;
            //如果指向成功了
            result.put("status", "success");
            result.put("url", url);
        } catch (IOException e) {
            //返回异常
            result.put("status", "error");
            result.put("msg", e.getMessage());
        }
        return result;
    }*/

    /**
     * 转正，离职，辞退---查看信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @GetMapping("/queryStaffInfo")
    @ApiOperation(value = "转正，离职，辞退---查看信息")

    public ResultHelper<UemUserDto> queryStaffInfo(@RequestParam Long uemUserId) {
        return uemUserManageService.queryStaffInfo(uemUserId);
    }

    /**
     * 添加转正信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @PostMapping("/savePositiveInfo")
    @ApiOperation(value = "添加转正信息")
    public ResultHelper<Object> savePositiveInfo(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.savePositiveInfo(uemUserDto);
    }

    /**
     * 添加离职信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @PostMapping("/saveResignInfo")
    @ApiOperation(value = "添加离职信息")
    public ResultHelper<Object> saveResignInfo(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.saveResignInfo(uemUserDto);
    }

    /**
     * 添加辞退信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @PostMapping("/saveDismissInfo")
    @ApiOperation(value = "添加辞退信息")
    public ResultHelper<Object> saveDismissInfo(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.saveDismissInfo(uemUserDto);
    }

    /**
     * 查看转正评语部分信息
     *
     * @param uemUserId
     * @return
     */
    @RequestMapping("/queryOfferInfo")
    public UemUserDto queryOfferInfo(@RequestParam(value = "uemUserId") Long uemUserId) {
        return uemUserManageService.queryOfferInfo(uemUserId);
    }

    /**
     * 查看离职原因
     *
     * @param uemUserId
     * @return
     */
    @GetMapping("/queryLeaveInfo")
    public ResultHelper<UemUserDto> queryLeaveInfo(Long uemUserId) {
        return uemUserManageService.queryLeaveInfo(uemUserId);
    }

    /**
     * 查看辞退原因
     *
     * @param uemUserId
     * @return
     */
    @GetMapping("/queryDismissInfo")
    public ResultHelper<UemUserDto> queryDismissInfo(Long uemUserId) {
        return uemUserManageService.queryDismissInfo(uemUserId);
    }

    /**
     * 保存员工信息
     *
     * @param uemUserDto
     * @return
     */
    @PostMapping("/preservationUemUser")
    public ResultHelper<?> preservationUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.preservationUemUser(uemUserDto);
    }

    /**
     * 离职申请添加离职理由
     *
     * @param
     * @return
     */
    @GetMapping("/updateLeaveReason")
    public ResultHelper<?> updateLeaveReason(
            @RequestParam(value = "uemUserId") Long uemUserId, @RequestParam(value = "leaveReason") String leaveReason) {
        return uemUserManageService.updateLeaveReason(uemUserId, leaveReason);
    }

    /**
     * 上传文件
     *
     * @param systemId
     * @param fileType
     * @param fileName
     * @param uemUserId
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadExternalFile")
    public ResultHelper<?> uploadExternalFile(@RequestParam("systemId") String systemId,
                                              @RequestParam("fileType") String fileType,
                                              @RequestParam("fileName") String fileName,
                                              @RequestParam("type") String type,
                                              @RequestParam("uemUserId") Long uemUserId,
                                              @RequestPart("file") MultipartFile file) {
        return uemUserManageService.uploadExternalFile(uemUserId, systemId, fileType, fileName, type,file);
    }

    /**
     * 下拉框查询所有岗位的信息
     *
     * @return
     */
    @GetMapping("querySysPost")
    public List<SysPost> querySysPost() {
        return uemUserManageService.querySysPost();
    }

    /**
     * 下拉框查询所有职称的信息
     *
     * @return
     */
    @GetMapping("querySysTechnicalTitle")
    public List<SysTechnicalTitle> querySysTechnicalTitle() {
        return uemUserManageService.querySysTechnicalTitle();
    }

    /**
     * 下拉框查询所有项目的信息
     *
     * @return
     */
    @GetMapping("queryUemProject")
    public List<UemProject> queryUemProject() {
        return uemUserManageService.queryUemProject();
    }

    /**
     * 下拉框查询所有部门的信息
     *
     * @return
     */
    @GetMapping("queryUemDept")
    public List<UemDept> queryUemDept() {
        return uemUserManageService.queryUemDept();
    }

    /**
     * 联想控件---user表id查name
     *
     * @param uemUserId
     * @return
     */
    @RequestMapping("/queryUemUserById")
    public UemUserDto queryUemUserById(@RequestParam(value = "uemUserId") Long uemUserId) {
        return uemUserManageService.queryUemUserById(uemUserId);
    }

    /**
     * 服务调用---添加转正信息
     *
     * @param taskDetailInfoDTO
     * @return
     */
    @PostMapping("/savePositiveInfoByStaff")
    public ResultHelper<TaskDetailInfoDTO> savePositiveInfoByStaff(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        ResultHelper<Object> objectResultHelper = authInfoInterface.savePositiveInfoByStaff(taskDetailInfoDTO);
        return CommonResult.getSuccessResultData(objectResultHelper);
    }


}
