package com_xzyh_crm.util;

public class PublicDictUtil {

    private PublicDictUtil(){}

	//redis_ip
	public static final String REDIS_IP = "localhost";


	//阿里支付地址
	public static final String aliPay_URL = "https://openapi.alipay.com/gateway.do";

	// KEY
	public static final String KEY = "result";// 返回结果key 

	public static final String DATA_KEY = "data";// 返回结果数据key

	public static final String MSG_KEY = "msg";// 返回消息key

	// VALUE

	public static final String SUCCESS_VALUE = "100";// 返回成功码

	public static final String ERROR_VALUE = "-100";// 返回失败码

	public static final String TOKEN_INVAALID = "-101";// 返回失败码

	public static final String LOGIN_ERRO_VALUE = "-1";// 重新登录

	public static final String ERROR_DOT_VALUE = "101";
	
	public static final String IDE_REPEAT_ERROR_VALUE= "10001";//身份验证重复

	public static final String IDE_1002_VALUE= "10002";//无订单欠款详情--附表为空

	public static final String IDE_1003_VALUE= "10003";//用户没有钱包

	public static final String IDE_1004_VALUE= "10004";//用户余额不足
	public static final String DELECT_MENUROLE_ERROR_VALUE = "-201";// 返回失败码

	
	//obd 接口参数 uid
	public static final String OBD_UID = "ZHCX001";
	
	public static final String CHAR_SET="UTF-8";
	
    public static final String KEY_MD5 = "MD5";  
    
    // 盐
    public static final String SALT_MD5 = "JINRxingFu";
	
    /*************************************** ====  geet config start ====*****************************/
  
    
    public static final String WIDTH = "95";
    
    public static final String HEIGHT = "37";

    public static final Integer product_width = 166;

    public static final Integer getProduct_height = 170;
    
    public static final String industryWIDTH = "282";
    
    public static final String industryHEIGHT = "300";
    
    public static final String  ISFIRSTLOGIN = "001";  //第一次登录
    
    public static final String THUMBNAIL="th";

	public static final String INITPASSWORD = "123456";
    
    public static final String VERNAME="4.0";
    
    public static final String SDKLANG="java";

    public static final String APIURL="http://api.geetest.com";
    
    public static final String REGISTERURL="/register.php";
    
    public static final String VALIDATEURL="/validate.php";
    
    public static final String JSON_FORMAT="1";
    
    public static final String  FN_GEET_CHALLENGE = "geetest_challenge"; 
    
    public static final String FN_GEET_VALIDATE="geetest_validate";
    
    public static final String FN_GEET_SECCODE="geetest_seccode";
    
    public static final String GT_SERVER_STATUS_SESSION_KEY="gt_server_status";
    
    public static final String GT_USERID="gtuserid";
    
    public static final String USER_TOKE="token";
    
    public static final String USER = "user";

	public static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

	public static final int NO_DEL = 0; //没有删除
	
	public static final int DEL = 1; //删除
	
	public static final int CLOSE = 0; //停用
	
	public static final int OPEN = 1; //启用
	
	public static final int dealer_role = 1;//经销商
	
	public static final int crew_role = 2; //人员

    /*************************************** ====  geet config end ==== *****************************/  
    
    
    /*************  ===  polyv start ===== **************/ 
	public static final String POLYV_USER_ID = "c0b998058c"; 
   	
   	public static final String POLYV_WRITE_TOKEN = "c1a26cb8-37f2-4a83-a457-02f7bea651ac";
   	
   	public static final String POLYV_READ_TOKEN = "6b6832ab-88cd-41d7-82d5-b1f9e41e27d0";
   	
   	public static final String POLYV_SECRET_KEY  = "Y96RUqhiQX";
   	
   	public static final boolean POLYV_SGIN = false;
   	
   	public static final String POLYV_SDK_CODE = "d+2Rzlez7JmEXCyp0RXf22aZoG0RT1LybmKd1+HBMs1198MseV31MbV7NUmlYzA03cxoTrAazOK01ErEt+SS2C9qrvSmNxsAUOcpBNbW+Fx5jj0JlxGTVkYq+TkZvQzoQj9eOh7gi/I7FOKlDvE3lg==";
	
	/*************  ===  polyv end ===== **************/ 


}
