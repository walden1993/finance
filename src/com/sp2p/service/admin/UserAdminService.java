package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.UserAdminDao;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;

public class UserAdminService extends BaseService{
	public static Log log =LogFactory.getLog(UserAdminService.class);
	private UserAdminDao userAdminDao;
	public void setUserAdminDao(UserAdminDao userAdminDao) {
		this.userAdminDao = userAdminDao;
	}
	
	/**
	 * 修改用户基本信息
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
	public Long updateUserBaseData(String realName, String cellPhone,
			String sex, String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.updateUserBaseData(conn, realName, cellPhone,
					sex, birthday, highestEdu, eduStartDay, school,
					maritalStatus, hasChild, hasHourse, hasHousrseLoan, hasCar,
					hasCarLoan, nativePlacePro, nativePlaceCity,
					registedPlacePro, registedPlaceCity, address, telephone,
					personalHead, userId, idNo);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}finally{
			conn.close();
		}
		return personId;

	}
	
	//认证后更新t_person表内信息
	public Long updatePerson(String realName, Long userId, String idNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.updatePerson(conn, realName, userId, idNo);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return personId;

	}
	//认证后更新t_person表内信息
		public Long updatePerson(String realName, Long userId, String idNo,String idType)
				throws Exception {
			Connection conn = MySQL.getConnection();
			long personId = -1L;
			try {
				personId = userAdminDao.updatePerson(conn, realName, userId, idNo,idType);
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
				conn.commit();
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				conn.rollback();
				throw e;
			}finally{
				conn.close();
			}
			return personId;

		}
	
	//认证后增加t_person表内信息
	public Long addPerson(String realName, Long userId, String idNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.addPerson(conn, realName, userId, idNo);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return personId;

	}
	
	//更新用户认证失败次数
	public Long updatePersonCount(Long userId,int authCardName)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.updatePersonCount(conn,userId,authCardName);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return personId;

	}
	
	//新增用户认证失败次数
	public Long addPersonCount(Long userId,int authCardName)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.addPersonCount(conn,userId,authCardName);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return personId;
	}
	
	/**
	 * 修改用户工作信息
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
	public Long updateUserWorkData(String orgName, String occStatus,
			Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long workDataId = -1L;
		try {
			workDataId = userAdminDao.updateUserWorkData(conn, orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);
			if(workDataId<=0){
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}finally{
			conn.close();
		}

		return workDataId;

	}

	/**
	 * 更新用户企业信息
	 * l.x.z
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserCompanyData(Long Id,Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userAdminDao.updateUserCompanyData(conn, Id, orgId, organizationName, 
					address, industory, companyType, companyScale, foundDate, legalPerson,
					regNum, organizationCode, registeredCapital, bankName, accountName,
					publicBankaccount, website, phone, email, linkmanName, linkmanMobile,
					companyDescription, headJpg);
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return personId;
	}
	
	

}
