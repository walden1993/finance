package com.sp2p.entity;

import java.util.Date;

//用户实体类--llz
public class Users {

	private Integer id;  //用户ID
	private String email;  //用户email
	private String userName;  //用户名
	private Integer vipStatus;  //VIP会员状态(1 默认 非VIP 2 VIP 3 待续费VIP)
	private Integer authStep;  //认证步骤(默认是1  个人详细信息 2  工作认证 3上传 资料)
	private Integer enable;  //是否禁用 1、启用 2、禁用 3.黑名单 默认1
	private String password;  //用户密码
	private String dealpwd;  //交易密码(初始密码为用户密码)
	private String mobilePhone;  //用户移动电话
	private String refferee;  //推荐人
	private Integer rating;  //网站积分
	private Integer creditrating;  //信用积分
	private String lastIP;  //最后登录IP
	private Date lastDate;  //最后登录时间
	private Date vipCreateTime;  //VIP创建时间
	private Integer creaditLimit;  //信用额度
	private String headImg;  //头像
	private Date createTime;  //帐号创建时间
	private String content;  //用户vip申请会员时候填写的备注
	private Integer usableSum;  //可用金额
	private Integer freezeSum;  //冻结金额
	private Integer dueinSum;  //待收金额
	private Integer dueoutSum;  //待还金额
	private Integer kefuId;  //客服Id
	private Integer adminId;  //后台审核员id
	private Integer groupId;  //组ID
	private Integer usableCreateitLimit;  //可用信用额度
	private Integer creditlimtor;  //额度审核审核员id
	private Integer vipFee;  //vip会费
	private Integer feeStatus;  //费用扣除状态( 1 待扣 2 已扣)
	private Integer loginCount;  //登录次数
	private Date lockTime;  //锁定时间
	private Integer cashStatus;  //提现状态(默认1 启动 2 禁止)
	private Integer xMax;  //最大待收本金
	private Integer x;  //系数
	private Integer xMin;  //最小值
	private Integer isFirstVip;  //是否首次成为VIP(默认 1 是 2 否)
	private String sign;  //
	private String sign2;  //
	private Integer loginErrorCount;  //错误登录次数，默认0
	private Integer isLoginLimit;  //1.不限制登录 2.限制登录
	private Integer isApplypro;  //
	private String orgName;  //企业全称
	private Integer userType;  //1-个人用户，2-企业用户
	private String source; //客户推广,渠道码
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getVipStatus() {
		return vipStatus;
	}
	public void setVipStatus(Integer vipStatus) {
		this.vipStatus = vipStatus;
	}
	public Integer getAuthStep() {
		return authStep;
	}
	public void setAuthStep(Integer authStep) {
		this.authStep = authStep;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDealpwd() {
		return dealpwd;
	}
	public void setDealpwd(String dealpwd) {
		this.dealpwd = dealpwd;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRefferee() {
		return refferee;
	}
	public void setRefferee(String refferee) {
		this.refferee = refferee;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public Integer getCreditrating() {
		return creditrating;
	}
	public void setCreditrating(Integer creditrating) {
		this.creditrating = creditrating;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public Date getVipCreateTime() {
		return vipCreateTime;
	}
	public void setVipCreateTime(Date vipCreateTime) {
		this.vipCreateTime = vipCreateTime;
	}
	public Integer getCreaditLimit() {
		return creaditLimit;
	}
	public void setCreaditLimit(Integer creaditLimit) {
		this.creaditLimit = creaditLimit;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUsableSum() {
		return usableSum;
	}
	public void setUsableSum(Integer usableSum) {
		this.usableSum = usableSum;
	}
	public Integer getFreezeSum() {
		return freezeSum;
	}
	public void setFreezeSum(Integer freezeSum) {
		this.freezeSum = freezeSum;
	}
	public Integer getDueinSum() {
		return dueinSum;
	}
	public void setDueinSum(Integer dueinSum) {
		this.dueinSum = dueinSum;
	}
	public Integer getDueoutSum() {
		return dueoutSum;
	}
	public void setDueoutSum(Integer dueoutSum) {
		this.dueoutSum = dueoutSum;
	}
	public Integer getKefuId() {
		return kefuId;
	}
	public void setKefuId(Integer kefuId) {
		this.kefuId = kefuId;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getUsableCreateitLimit() {
		return usableCreateitLimit;
	}
	public void setUsableCreateitLimit(Integer usableCreateitLimit) {
		this.usableCreateitLimit = usableCreateitLimit;
	}
	public Integer getCreditlimtor() {
		return creditlimtor;
	}
	public void setCreditlimtor(Integer creditlimtor) {
		this.creditlimtor = creditlimtor;
	}
	public Integer getVipFee() {
		return vipFee;
	}
	public void setVipFee(Integer vipFee) {
		this.vipFee = vipFee;
	}
	public Integer getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(Integer feeStatus) {
		this.feeStatus = feeStatus;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	public Integer getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}
	public Integer getxMax() {
		return xMax;
	}
	public void setxMax(Integer xMax) {
		this.xMax = xMax;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getxMin() {
		return xMin;
	}
	public void setxMin(Integer xMin) {
		this.xMin = xMin;
	}
	public Integer getIsFirstVip() {
		return isFirstVip;
	}
	public void setIsFirstVip(Integer isFirstVip) {
		this.isFirstVip = isFirstVip;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign2() {
		return sign2;
	}
	public void setSign2(String sign2) {
		this.sign2 = sign2;
	}
	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}
	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}
	public Integer getIsLoginLimit() {
		return isLoginLimit;
	}
	public void setIsLoginLimit(Integer isLoginLimit) {
		this.isLoginLimit = isLoginLimit;
	}
	public Integer getIsApplypro() {
		return isApplypro;
	}
	public void setIsApplypro(Integer isApplypro) {
		this.isApplypro = isApplypro;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	

	
	
	
	
	
	
}
