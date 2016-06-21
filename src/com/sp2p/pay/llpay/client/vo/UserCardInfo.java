package com.sp2p.pay.llpay.client.vo;

import java.io.Serializable;

public class UserCardInfo implements Serializable {

	private static final long serialVersionUID = -1834512287323784819L;

	private String user_id;// '商户用户唯一编',
	private String no_agree;// '签约协议号',
	private String card_no;// '银行卡号后 4 位',
	private String bank_code;// '所属银行编号',
	private String bank_name;// '所属银行名称 ',
	private String card_type;// '银行卡类型',
	private String bind_mobile;// '绑定手机号码'
	
	private String ret_code;

    private String ret_msg;

    public String getRet_code()
    {
        return ret_code;
    }

    public void setRet_code(String ret_code)
    {
        this.ret_code = ret_code;
    }

    public String getRet_msg()
    {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg)
    {
        this.ret_msg = ret_msg;
    }
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getBind_mobile() {
		return bind_mobile;
	}

	public void setBind_mobile(String bind_mobile) {
		this.bind_mobile = bind_mobile;
	}

}
