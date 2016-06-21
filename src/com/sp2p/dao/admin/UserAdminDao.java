package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.data.DataException;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_orginfo;

public class UserAdminDao {
	public static Log log =LogFactory.getLog(UserAdminDao.class);
	
	/**后台管理员更改资料
	 * @param conn
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseData(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.cellPhone.setValue(cellPhone);
		person.sex.setValue(sex);
		person.birthday.setValue(birthday);
		person.highestEdu.setValue(highestEdu);
		person.eduStartDay.setValue(eduStartDay);
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
        return person.update(conn, "userId = " + userId);
	}
	
	//认证后更新t_person表内信息
	public Long updatePerson(Connection conn, String realName, Long userId, String idNo) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.authCardName.setValue(0);
        return person.update(conn, "userId = " + userId);
	}
	//认证后更新t_person表内信息
	public Long updatePerson(Connection conn, String realName, Long userId, String idNo,String idType) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.authCardName.setValue(0);
		person.idType.setValue(idType);
        return person.update(conn, "userId = " + userId);
	}
	
	//认证后增加t_person表内信息
	public Long addPerson(Connection conn, String realName, Long userId, String idNo) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.authCardName.setValue(0);
        return person.insert(conn);
	}
	
	//更新用户认证失败次数
	public Long updatePersonCount(Connection conn,Long userId,int authCardName) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.userId.setValue(userId);
		int authCardNameCount = authCardName + 1;
		person.authCardName.setValue(authCardNameCount);
		return person.update(conn, "userId = " + userId);
	}
	
	//新增用户认证失败次数
	public Long addPersonCount(Connection conn,Long userId,int authCardName) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.userId.setValue(userId);
		int authCardNameCount = authCardName + 1;
		person.authCardName.setValue(authCardNameCount);
		return person.insert(conn);
	}
	
	/**
	 * 保存工作信息
	 * @param conn
	 * @param orgName
	 * @param occStatus
	 * @param workPro
	 * @param workCity
	 * @param companyType
	 * @param companyLine
	 * @param companyScale
	 * @param job
	 * @param monthlyIncome
	 * @param workYear
	 * @param companyTel
	 * @param workEmail
	 * @param companyAddress
	 * @param directedName
	 * @param directedRelation
	 * @param directedTel
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param moredName
	 * @param moredRelation
	 * @param moredTel
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserWorkData(Connection conn, String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws SQLException, DataException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		workauth.orgName.setValue(orgName);
		workauth.occStatus.setValue(occStatus);
		workauth.workPro.setValue(workPro);
		workauth.workCity.setValue(workCity);
		workauth.companyType.setValue(companyType);
		workauth.companyLine.setValue(companyLine);
		workauth.companyScale.setValue(companyScale);
		workauth.job.setValue(job);
		workauth.monthlyIncome.setValue(monthlyIncome);
		workauth.workYear.setValue(workYear);
		workauth.companyTel.setValue(companyTel);
		workauth.workEmail.setValue(workEmail);
		workauth.companyAddress.setValue(companyAddress);
		workauth.directedName.setValue(directedName);
		workauth.directedRelation.setValue(directedRelation);
		workauth.directedTel.setValue(directedTel);
		workauth.otherName.setValue(otherName);
		workauth.otherRelation.setValue(otherRelation);
		workauth.otherTel.setValue(otherTel);
		workauth.moredName.setValue(moredName);
		workauth.moredRelation.setValue(moredRelation);
		workauth.moredTel.setValue(moredTel);
		return workauth.update(conn, "userId = " + userId);

	}
	
	
	/**后台管理员更改资料
	 * l.x.z
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserCompanyData(Connection conn,Long Id, Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg) throws SQLException,
			DataException {
		 
			Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
			
			orginfo.organization_name.setValue(organizationName);
			orginfo.address.setValue(address);
			orginfo.industory.setValue(industory);
			orginfo.company_type.setValue(companyType);
			orginfo.company_scale.setValue(companyScale);
			orginfo.found_date.setValue(foundDate);
			orginfo.legal_person.setValue(legalPerson);
			orginfo.reg_num.setValue(regNum);
			orginfo.organization_code.setValue(organizationCode);
			orginfo.registered_capital.setValue(registeredCapital);
			orginfo.bank_name.setValue(bankName);
			orginfo.account_name.setValue(accountName);
			orginfo.public_bank_account.setValue(publicBankaccount);
			orginfo.website.setValue(website);
			orginfo.phone.setValue(phone);
			orginfo.email.setValue(email);
			orginfo.linkman_name.setValue(linkmanName);
			orginfo.linkman_mobile.setValue(linkmanMobile);
			orginfo.company_description.setValue(companyDescription);
			orginfo.head_jpg.setValue(headJpg);
			orginfo.update_time.setValue(new Date());
			
		    Long result = -1L;
		    
		    result=orginfo.update(conn, "id="+Id+" and user_id="+orgId);
		    
		    return result;
		
	}
	
	
	

}
