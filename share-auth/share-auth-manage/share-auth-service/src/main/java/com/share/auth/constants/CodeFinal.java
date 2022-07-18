package com.share.auth.constants;

/**
 * @description 常量
 * @Date 20201021
 * @author xrp
 * */
public class CodeFinal {

    /**审批状态（0待审批，1审批通过，2审批失败，999-全部）*/
    public static final String AUDIT_STATUS_ZERO = "0";
    public static final String AUDIT_STATUS_ONE = "1";
    public static final String AUDIT_STATUS_TWO = "2";
    public static final String AUDIT_STATUS_ALL = "999";

    /**用户类型（0-普通用户，1-企业用户，2-企业管理员）*/
    public static final String USER_TYPE_ZERO = "0";
    public static final String USER_TYPE_ONE = "1";
    public static final String USER_TYPE_TWO = "2";

    /**否同意协议(0不同意，1同意)*/
    public static final String IS_AGREEMENT_ZERO = "0";
    public static final String IS_AGREEMENT_ONE = "1";

    /**是否禁用(0禁用,1启用)*/
    public static final String IS_VALID_ZERO = "0";
    public static final String IS_VALID_ONE = "1";

    /**是否显示（0显示，1隐藏）*/
    public static final String IS_DISPLAYED_ZERO = "0";
    public static final String IS_DISPLAYED_ONE = "1";

    /**性别（0男，1女）*/
    public static final String SEX_ZERO = "0";
    public static final String SEX_ONE = "1";

    /**操作类型（1-修改用户名，2-修改密码，3-找回密码，4-修改手机，5-绑定邮箱，6-修改邮箱,7第三方账号绑定）*/
    public static final String OPER_TYPE_ONE = "1";
    public static final String OPER_TYPE_TWO = "2";
    public static final String OPER_TYPE_THREE = "3";
    public static final String OPER_TYPE_FOUR = "4";
    public static final String OPER_TYPE_FIVE = "5";
    public static final String OPER_TYPE_SIX = "6";
    public static final String OPER_TYPE_SEVEN = "7";

    /**第三方账号类型（0微信，1qq，2国家政务平台）*/
    public static final String THIRD_ACCOUNT_TYPE_ZERO = "0";
    public static final String THIRD_ACCOUNT_TYPE_ONE = "1";
    public static final String THIRD_ACCOUNT_TYPE_TWO = "2";

    /**激活类型（0注册，1找回密码，2账号安全，3新增用户）*/
    public static final String ACTIVE_TYPE_ZERO = "0";
    public static final String ACTIVE_TYPE_ONE = "1";
    public static final String ACTIVE_TYPE_TWO = "2";

    /**接收方类型（0手机号，1邮箱号）*/
    public static final String RECEIVER_TYPE_ZERO = "0";
    public static final String RECEIVER_TYPE_ONE = "1";


    /**登录方式(0用户名，1手机，2邮箱，3微信，4qq，5国家政务平台)*/

    public static final String WAY_ZERO = "0";
    public static final String WAY_ONE = "1";
    public static final String WAY_TWO = "2";
    public static final String WAY_THREE = "3";
    public static final String WAY_FOUR = "4";
    public static final String WAY_FIVE = "5";


    /**登录设备（0手机，1电脑）*/
    public static final String EQUIPMENT_ZERO = "0";
    public static final String EQUIPMENT_ONE = "1";

    /**
     * 登录类型（0登录，1退出）
     */
    public static final String LOGIN_TYPE_ZERO = "0";
    public static final String LOGIN_TYPE_ONE = "1";

    /**
     * 登录结果（0成功，1锁定）
     */
    public static final String RESULT_ZERO = "0";
    public static final String RESULT_ONE = "1";

    /**
     * 是否可以查看下级组织数据（0否，1是）
     */
    public static final String IS_SUPERIOR_ZERO = "0";
    public static final String IS_SUPERIOR_ONE = "1";

    /**
     * 数据来源（0用户新增，2客服新增,3国家综合交通运输信息平台）
     */
    public static final String DATA_SOURCE_ZERO = "0";
    public static final String DATA_SOURCE_TWO = "2";
    public static final String DATA_SOURCE_THREE = "3";

    /**用户所在企业角色（0企业普通用户，1企业管理员）
     public static final String USER_ROLE_ZERO = "0";
     public static final String USER_ROLE_ONE = "1";*/

    /**
     * 文件类型(0身份证正面，1身份证反面，2企业证件，3管理员认证函)
     */
    public static final String FILE_TYPE_ZERO = "0";
    public static final String FILE_TYPE_ONE = "1";
    public static final String FILE_TYPE_TWO = "2";
    public static final String FILE_TYPE_THREE = "3";

    /**
     * 模板文件类型
     */
    public static final String TEMPLATE_FILE_TYPE_ONE = "1";

    /**
     * 近一个月和近三个月和最近一百条
     */
    public static final String ONE_MONTH = "1";
    public static final String THREE_MONTH = "2";
    public static final String RECENT_TIME = "0";
    /**
     * 登录日志 个人 显示分页100条
     */
    public static final Integer HUNDRED = 100;

    /**
     * 用于区别快速注册生成验证码和手机号找回密码生成验证码,1为快速注册,2为找回密码,3为修改绑定手机号
     */
    public static final String SIGN_1 = "1";
    public static final String SIGN_2 = "2";
    public static final String SIGN_3 = "3";

    /**公共服务参数定义*/
    public static final String LINKADDRESS = "linkAddress";
    public static final String CONTENT = "content";

    /**企业表国家字段默认赋值*/
    public static final String LOCCOUNTRYNAME = "中国";
    public static final String LOCCOUNTRYCODE = "CN";

    /**邮件发送key和成功值*/
    public static final String RESULTCODE = "resultCode";
    /**成功代码*/
    public static final String RESULT_SUCCESS_CODE = "0";
    public static final String SUCCESSEMAIL = "0";

    /**标识*/
    public static final Integer SIZE = 1;

    /**绑定物流交换代码*/
    public static final String USERID = "userid";
    public static final String PASS = "password";
    public static final String RESOURCE = "resource";

    /**
     * 服务资源和链接
     */
    public static final String RESOURCESERVICE = "0ACA7C6BA38E0008E053C0A87F0B0008";
    public static final String URL = "https://ssl.logink.cn/authapi/rest/auth/apply";

    // 企业用户类型
    /**
     * 需方企业
     */
    public static final String LOGIN_LOGISTICS_REQUIREMENT = "LOGIN_LOGISTICS_REQUIREMENT";
    /**
     * 承运商企业
     */
    public static final String LOGIN_LOGISTICS_SUPPLY = "LOGIN_LOGISTICS_SUPPLY";
    /**  政府机构*/
    public static final String LOGIN_MECHANISM_TYPE = "LOGIN_MECHANISM_TYPE";
    /**  其他*/
    public static final String LOGIN_OTHER = "LOGIN_OTHER";

    // 企业货物类型
    /**
     * 普通货物
     */
    public static final String WLO = "WLO";
    /**
     * 疫苗货物
     */
    public static final String WLV = "WLV";
    /**
     * 疫苗/普通需方角色
     */
    public static final String WLOV = "WLOV";
    /** 承运商*/
    public static final String CYS = "CYS";
    /** 省厅/其他政府机构*/
    public static final String ZGA = "ZGA";

    /**当前页码默认值1*/
    public static final int CURRENT_PAGE_DEFAULT = 1;
    /**每页数量默认值10*/
    public static final int PAGE_SIZE_DEFAULT = 20;
    /**新增或修改失败时，返回的记录行数*/
    public static final int SAVE_OR_UPDATE_FAIL_ROW_NUM = 0;
    /**递归开始层级*/
    public static final int RECURSION_START_LEVEL = 0;
    /**递归最大层级*/
    public static final int RECURSION_END_LEVEL = 100;
    /**平台客服默认角色*/
    public static final Long ADMIN_DEFAULT_ROLE_ID = 0L;


    /** 政府机构-部属单位*/
    public static final String LOGIN_MECHANISM_TYPE_M1 = "M1";
    /** 政府机构-省厅*/
    public static final String LOGIN_MECHANISM_TYPE_M2 = "M2";
    /** 政府机构-其他政府机构*/
    public static final String LOGIN_MECHANISM_TYPE_M3 = "M3";

    /** 承运商企业-综合物流企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S1 = "S1";
    /** 承运商企业-中欧班列企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S2 = "S2";
    /** 承运商企业-国际海运企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S3 = "S3";
    /** 承运商企业-国际航空货运企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S4 = "S4";
    /** 承运商企业-国际寄递物流企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S5 = "S5";
    /** 承运商企业-国际道路货运企业*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S6 = "S6";
    /** 承运商企业-港口*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S7 = "S7";
    /** 承运商企业-仓库*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S8 = "S8";
    /** 承运商企业-其他物流服务商*/
    public static final String LOGIN_LOGISTICS_SUPPLY_S9 = "S9";

    private CodeFinal () {}

    /** 需方企业-制造类企业*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_R1 = "R1";
    /** 需方企业-商贸类企业*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_R2 = "R2";
    /** 需方企业-药企*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_R3 = "R3";
    /** 需方企业-疾控中心*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_R4 = "R4";
    /** 需方企业-粮企*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_R5 = "R5";
    /** 需方企业-需方总部*/
    public static final String LOGIN_LOGISTICS_REQUIREMENT_S7 = "S7";

    /**
     * 企业机构类型字典
     **/
    public static final String DICT_LOGIN_ORGANIZATION_TYPE="LOGIN_ORGANIZATION_TYPE";

    /** 空判断 */
    public static final String NULL = "null";

    /** 请求token */
    public static final String ACCESS_TOKEN = "access_token";

    /** JWT失效内容*/
    public static final String DISABLED_JWT = "disabledJwt";

    /** 滑动滑块大图宽度 */
    public static final int BIG_IMAGE_WIDTH = 265;
    /** 滑动滑块大图宽度-注册 */
    public static final int BIG_IMAGE_WIDTH_RE = 370;
    /** 毫秒转秒的度量单位 */
    public static final Long MILLIS_TO_SECOND = 1000L;

    /**凭证有效期低于20分钟，刷新凭证 */
    public static final Long MILLIS_MINUTE_TEN = 1 * 60 * 1000L;
}
