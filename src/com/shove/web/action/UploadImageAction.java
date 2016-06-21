package com.shove.web.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.shove.util.SqlInfusion;
import com.shove.vo.Files;
import com.shove.web.util.ServletUtils;
import com.shove.web.util.UploadUtil;
import com.shove.web.util.VerifyTruePicture;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.HomeInfoSettingService;

public class UploadImageAction extends BasePageAction {
	private HomeInfoSettingService homeInfoSettingService;
   private File  homefile ;
   private String homefileFileName;
	private String homefileContentType;
	private Files files; // 文件参数(对象)
   
	
	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}



	public File getHomefile() {
		return homefile;
	}



	public void setHomefile(File homefile) {
		this.homefile = homefile;
	}



	public String getHomefileFileName() {
		return homefileFileName;
	}



	public void setHomefileFileName(String homefileFileName) {
		this.homefileFileName = homefileFileName;
	}



	public String getHomefileContentType() {
		return homefileContentType;
	}



	public void setHomefileContentType(String homefileContentType) {
		this.homefileContentType = homefileContentType;
	}



	/**
	 * 上传头像
	 * @return
	 * @throws Exception 
	 */
	public String homeupload() throws Exception{
		
		  String realPath = "";
		  String  fileName = "";
		 if(homefile==null){
			 getOut()
				.print(
						"<script>alert('上传路径不存在!');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>"); 
			 return null;
			 
		 }
		if (homefile.exists()) { 
			 realPath = ServletUtils.serverRootDirectory()+ "/upload/touxiang/";
			//String path=ServletActionContext.getServletContext().getRealPath("/testlw");
			File f =new File(new File(realPath),this.getHomefileFileName());
			
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			
			String getExt =  com.shove.web.util.UploadUtil.findFileNameExt(homefileFileName);
			
			if (!"GIF,JPG,JPEG,PNG,BMP".contains(getExt.toUpperCase())) {
				//return "文件类型不对!";
				getOut()
				.print(
						"<script>alert('文件类型不对!');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
				 return null;
			}
			
			String filetype2 = VerifyTruePicture.getFileByFile(homefile,"img");
			 
			if(filetype2==null)
			{
				//return "上传文件类型错误!";
				getOut()
				.print(
						"<script>alert('上传文件类型错误!');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
				 return null;
			}
			
			//限制文件大小
			long getFileSize = 1 * 700 * 1024;
			if (homefile.length() > getFileSize) {
				//return "文件超过上传700KB限制!";
				getOut()
				.print(
						"<script>alert('文件超过上传700KB限制!');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
				 return null;
			}
			  fileName = com.shove.web.util.FileUtils.getFileName() + "." + getExt;
			 
			/*try {
				FileUtils.copyFile(homefile,f);
			} catch (IOException e) {
				e.printStackTrace();
			}*/

			try {
				// 上传文件
				UploadUtil.uploadByFile(homefile, realPath, fileName);
			} catch (Exception e) {
				 
				//return "上传路径不存在!";
				getOut()
				.print(
						"<script>alert('上传路径不存在!');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
				 return null;
			}
			
		}
		else		
		{
			getOut()
			.print(
					"<script>alert('上传路径不存在!');parent.location.href='"
							+ request().getContextPath()
							+ "/home.do';</script>");
			 return null;
		}
		
		
			/**
			 * 同步上传头像到数据库中
			 */
			String realServicePath="upload/touxiang/"+fileName;
			try {
			  return	updateimag(realServicePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return SUCCESS;
	}
	
	

	public String updateimag(String realServicePath) throws Exception{
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long returnId = -1;
			returnId = homeInfoSettingService.updatePersonImg(realServicePath, user
					.getId());
			if (returnId <= 0) {
				getOut()
				.print(
						"<script>alert('上传头失败');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
				 return null;
			} else {
				
			}
			user.setPersonalHead(realServicePath);
			session().setAttribute(IConstants.SESSION_USER, user);
			 return SUCCESS;
	}
	
	
	

}
