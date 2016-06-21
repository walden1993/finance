package com.sp2p.service;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.sp2p.constants.IConstants;
import com.sp2p.service.admin.GetMailMsgOnUpService;


public class SendMailService {
	private static Log log = LogFactory.getLog(SendMailService.class);
	private JavaMailSenderImpl senderImpl;
	private ThreadPoolTaskExecutor taskExecutor;
	private String head;
	private String text;
	private String prompt;
	private String subject;
	private String from;
	private String message;
	public SendMailService(){
		String host = IConstants.MAIL_HOST;
		String username =IConstants.MAIL_USERNAME;
		String password = IConstants.MAIL_PASSWORD;
		this.from = IConstants.MAIL_FROM;
		
		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(host);
		senderImpl.setUsername(username);
		senderImpl.setPassword(password);
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		
	}
	/**
	 * 发送验证邮件
	 * @param VerificationUrl
	 * @param userName
	 * @param userEmail
	 */
	public void sendRegisterVerificationEmail(final String VerificationUrl, final String userName, final String userEmail) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();//-----//
					sendRegisterVerificationEmailMsg(VerificationUrl, userName, userEmail);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 验证注册邮箱密码
	 * @param VerificationUrl
	 * @param userName
	 * @param userEmail
	 */
	public void sendRegisterVerificationEmailPassWordindex(final String VerificationUrl, final String userName, final String userEmail) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();
					sendRegisterVerificationEmailPassWord(VerificationUrl, userName, userEmail);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 邮件验证
	 */
	public void sendRetakePasswordVerificationEmail(final String VerificationUrl, final String userName, final String userEmail) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();
					sendRetakePasswordVerificationEmailMsg(VerificationUrl, userName, userEmail);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void sendTrancepasswordLogin(final String VerificationUrl, final String userName, final String userEmail) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();
					sendTrancePassword(VerificationUrl, userName, userEmail);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * 发送验证信息
	 * @param VerificationUrl
	 * @param userName
	 * @param userEmail
	 * @throws MessagingException
	 */
	private void sendRegisterVerificationEmailMsg(String VerificationUrl, String userName, String userEmail) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"验证通知!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV>您的"+IConstants.PRO_GLOBLE_NAME+"账号已经成功创建，请点击此链接激活账号：</DIV>");
		sb.append("<BR/>");
		sb.append("<DIV><a href='");
		sb.append(VerificationUrl);
		sb.append("'>");
		sb.append(VerificationUrl);
		sb.append("</a></DIV>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		//记录本次发次
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送用户组邮件
	 * @param title
	 * @param content
	 * @param email
	 * @throws MessagingException 
	 */
	public void sendUserGroupEmail(String userEmail,String userName,String title,String content) throws MessagingException{
		getMailSet();
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(title);
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append(content);
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendRegisterVerificationEmailPassWord(String VerificationUrl, String userName, String userEmail) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"修改登录密码通知!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV>请点击此链接激活修改密码：</DIV>");
		sb.append("<BR/>");
		sb.append("<DIV><a href='");
		sb.append(VerificationUrl);
		sb.append("'>");
		sb.append(VerificationUrl);
		sb.append("</a></DIV>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改交易密码
	 * @param VerificationUrl
	 * @param userName
	 * @param userEmail
	 * @throws MessagingException
	 */
	private void sendTrancePassword(String VerificationUrl, String userName, String userEmail) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"修改交易密码密码通知!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV>请点击此链接激活修改交易密码：</DIV>");
		sb.append("<BR/>");
		sb.append("<DIV><a href='");
		sb.append(VerificationUrl);
		sb.append("'>");
		sb.append(VerificationUrl);
		sb.append("</a></DIV>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getMailSet(){
		this.from = IConstants.MAIL_FROM;
		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(IConstants.MAIL_HOST.trim());
		senderImpl.setUsername(IConstants.MAIL_USERNAME.trim());
		senderImpl.setPassword(IConstants.MAIL_PASSWORD.trim());
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(javaMailProperties);
	}
	
	
	
	
	
	private void sendRetakePasswordVerificationEmailMsg(String retakePassword, String userName, String userEmail) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"帐户找回密码链接!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV>请点击此链接重设密码：</DIV>");
		sb.append("<BR/>");
		sb.append("<DIV><a href='");
		sb.append(retakePassword);
		sb.append("'>");
		sb.append(retakePassword);
		sb.append("</a></DIV>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SendUserEmailSetInUser(final String VerificationUrl, final String userName, final String userEmail,final String xrandom) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();
					SendUserEmailSet(VerificationUrl, userName, userEmail, xrandom);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}

	private void SendUserEmailSet(String VerificationUrl, String userName, String userEmail,String xrandom) throws MessagingException{
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
		
		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"邮箱绑定通知!");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		//sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		//sb.append(userName);
		//sb.append("</span>:</H4>");
		//sb.append("<BR/>");
		sb.append("<DIV><H4 style='font-weight:normal;font-size:14px'>亲爱的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("</DIV>&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用三好资本，您此次的验证码为：");
		sb.append("<font style='color:#ff0000'>");
		sb.append(xrandom);
		sb.append("</font>");
		sb.append("<BR/>");
		sb.append("【三好资本】");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	private GetMailMsgOnUpService getMailMsgOnUpService;
	public GetMailMsgOnUpService getGetMailMsgOnUpService() {
		return getMailMsgOnUpService;
	}
	public void setGetMailMsgOnUpService(GetMailMsgOnUpService getMailMsgOnUpService) {
		this.getMailMsgOnUpService = getMailMsgOnUpService;
	}

/*	public void setGetAppliactionService(GetAppliactionService getAppliactionService) {
		this.getAppliactionService = getAppliactionService;
	}*/


//	public void setSenderImpl(JavaMailSender senderImpl) {
//		this.senderImpl = senderImpl;
//	}

//	public void setMsg(MimeMessage msg) {
//		this.msg = msg;
//	}
	
}

