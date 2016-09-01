package com.sf.sfpp.common.dto;

/**
 * 对应Exceptionmessage资源文件的key
 * <p>
 * <pre>
 * 跟客户端请求有关的错误,错误代码以R开头;
 * 跟系统有关的错误,错误代码以E开头;
 * 另外，对于跟业务及逻辑有关的L开头;
 * </pre>
 *
 * @author <a href="mailto:tangjimo@sf-express.com">709166</a>
 */
public interface SystemErrorCode {

    //////================跟客户端请求有关的错误===============//////////
    /**
     * 请求参数错误
     */
    String PARAMETER_HAS_ERROR = "00";
    /**
     * 参数{0}值不能为空
     */
    String PARAMETER_HAS_EMPTY = "01";

    /**
     * 请求参数不适合,如格式错误
     */
    String PARAMETER_HAS_ILLEGALSTATE = "02";
    /**
     * 请求参数数组及集合类越界
     */
    String PARAMETER_HAS_INDEXOUTOFBOUNDS = "03";
    /**
     * 请求参数存在空指针或空值
     */
    String PARAMETER_HAS_NULLPOINTER = "04";
    /**
     * 参数值不在范围内
     */
    String PARAMETER_HAC_UNLIMITED_ERROR = "05";
    /**
     * 请求参数过长
     */
    String PARAMETER_TOO_LONG = "06";



    //////================跟系统有关的错误===============//////////
    /**
     * 系统错误
     */
    String SYSTEM_HAS_ERROR = "D0";
    /**
     * 系统异常，参数为空
     */
    String SYSTEM_HAS_EMPTY = "D1";
    /**
     * 网络异常
     */
    String SYSTEM_HAS_NETWORK_ERROR = "D2";
    /**
     * 后台接口异常：
     **/
    String RPC_HAS_ERROR = "D3";

    /**
     * 数据转化出错
     */
    String SYSTEM_CAST_ERROR = "D5";
    //////================跟业务有关的错误===============//////////
    /**
     * 未知错误
     */
    String BIZ_HAS_UNKNOW_ERROR = "60";
    /**
     * 操作失败
     */
    String BIZ_OPERATE_FAILURE = "61";
    /**
     * 空指针异常
     */
    String BIZ_NULLPOINTER_ERROR = "62";
    /**
     * 系统不支持的业务
     */
    String BIZ_NOT_SUPPORT = "63";
    /**
     * 登陆相关的错误
     */
    String BIZ_LOGIN_ERROR = "64";
    /**
     * 操作相关的错误
     */
    String BIZ_OPERATE_ERROR = "65";
    /**
     * 操作超时
     */
    String BIZ_OPERATE_OUTTIME = "66";
    //=======用户业务相关的错误=======//
    /**
     * 用户名已存在
     */
    String BIZ_USER_EXISTED = "U0";
    /**
     * 用户名非工号
     */
    String BIZ_USER_NOT_WORKCODE = "U1";
    //=======角色业务相关的错误=======//
    /**
     * 角色{0}已经绑定(分配)给该用户{1}
     **/
    String BIZ_ROLE_ROLEEXISTED = "R0";
    /**
     * 用户{0}已经绑定(分配)给该角色{1}
     **/
    String BIZ_ROLE_USEREXISTED = "R1";
    /**
     * 角色不存在
     **/
    String BIZ_ROLE_NOEXIST = "R2";
    /**
     * 角色已存在
     **/
    String BIZ_ROLE_EXISTED = "R3";


    //////================其他错误===============//////////
    /**
     * 其他异常
     */
    String OTHER_ERROR = "F0";


}
