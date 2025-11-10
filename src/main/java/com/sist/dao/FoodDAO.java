package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class FoodDAO {
	private static SqlSessionFactory ssf;
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
		 <select id="foodListData" resultType="FoodVO" parameterType="hashmap">
			SELECT fno, name, type, address, poster, likecount, replycount, num
			FROM(SELECT fno, name, type, address, poster, likecount, replycount, rownum as num
			FROM(SELECT fno, name, type, address, poster, likecount, replycount
			FROM menupan_food ORDER BY fno ASC))
			WHERE num BETWEEN #{start} AND #{end}
		</select>
	 */
	// 목록 읽기
	public static List<FoodVO> foodListData(Map map){

		List<FoodVO> list = null;
		SqlSession session = null;
		try {
			session = ssf.openSession();
			list = session.selectList("foodListData",map);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return list;
	}
	
	/*
	 	<select id="foodTotalPage" resultType="int">
			SELECT CEIL(COUNT(*)/20.0) FROM menupan_food
		</select> 
	 */
	// 총 페이지
	public static int foodTotalPage() {
		
		int total = 0;
		SqlSession session = null;
		try {
			session = ssf.openSession();
			total = session.selectOne("foodTotalPage");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return total;
	}
	
	/*
		 <!-- 상세보기 -->
		<sql id="where-fno">
			WHERE fno=#{fno}
		</sql>
		<!-- SQL : 반복된느 SQL 문장을 저장후에 재사용 -->
		<update id="foodHitIncrement" parameterType="int">
			UPDATE menupan_food SET
			hit=hit+1
			WHERE fno=#{fno}
			<include refid="where-fno"/>
		</update>
		<select id="foodDetailData" resultType="FoodVO" parameterType="int">
			SELECT * FROM menufan_food
			<include refid="where-fno"/>
		</select>
	 */
	// 상세보기
	public static FoodVO foodDetailData(int fno) {
		SqlSession session = ssf.openSession();
		session.update("foodHitIncrement",fno);
		FoodVO vo = session.selectOne("foodDetailData",fno);
		session.close();
		return vo;
	}
	
	// mapper.xml => 실행(x) => sql 문장 저장
	public static FoodVO foodCookieData(int fno) {
		
		SqlSession session = ssf.openSession();
		FoodVO vo = session.selectOne("foodDetailData",fno);
		session.close();
		return vo;
	}
	
	public static List<FoodVO> foodFindData(Map map){
		
		List<FoodVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("foodFindData",map);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	public static int foodFindCount(Map map){
		
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("foodFindCount",map);
			
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return count;
	}
}
