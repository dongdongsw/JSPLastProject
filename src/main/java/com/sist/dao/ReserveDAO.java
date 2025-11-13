package com.sist.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.CreateSqlSessionFactory;
import com.sist.vo.FoodVO;
import com.sist.vo.ReserveVO;

public class ReserveDAO {
	private static SqlSessionFactory ssf;
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	/*
	 <select id="reserveFoodListData" resultType="FoodVO" parameterType="string">
		SELECT fno, name, poster, type, rownum
		FROM (
			SELECT fno, name, poster, type
			FROM menupan_food 
			ORDER BY fno
		)
		WHERE rownum&lt;=100 AND type LIKE '%'||#{type}||'%'
	</select>
	 */
	public static List<FoodVO> reserveFoodListData(String type) {
		List<FoodVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("reserveFoodListData",type);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	/*
		 <select id="reserveDateTimes" resultType="string" parameterType="int">
			SELECT times FROM reserve_date
			WHERE dno = #{dno}
		</select>
		<select id="reserveTime" resultType="string" parameterType="int">
			SELECT time FROM reserve_time
			WHERE tno = #{tno}		
		</select>
	 */
	public static String reserveDateTimes(int dno) {
		String times = "";
		try {
			SqlSession session = ssf.openSession();
			times = session.selectOne("reserveDateTimes",dno);
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return times;
	}
	
	public static String reserveTime(int tno) {
		String times = "";
		try {
			SqlSession session = ssf.openSession();
			times = session.selectOne("reserveTime",tno);
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return times;
	}
	
	/*
		 <insert id="reserveInsert" parameterType="ReserveVO">
			INSERT INTO reserve_info
			VALUES(
				ri_no_seq.nextval(), #{id}, #{fno}, #{rday}, #{time}, #{inwon}, #{ok}, SYSDATE 
			) 
		</insert>
	 */
	public static void reserveInsert(ReserveVO vo) {
		
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("reserveInsert",vo);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 *	<!-- mypage -->
		<select id="reserveMyPageListData" parameterType="string">
			SELECT no, ri.fno, id, rday, time, inwon, ok,
				name, phone, poster
			FROM reserve_info ri, menupan_food mf
			WHERE ri.fno=mf.fno
				AND id=#{id}
			ORDER BY no DESC
		
		</select>
		
		<!-- admin -->
		<select id="reserveAdminListData" parameterType="string">
			SELECT no, ri.fno, id, rday, time, inwon, ok,
				name, phone, poster
			FROM reserve_info ri, menupan_food mf
			WHERE ri.fno=mf.fno
			ORDER BY no DESC
		
		</select>
	 */
	
}
