package com.sist.dao;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;
public class ReviewDAO {
  private static SqlSessionFactory ssf;
  static 
  {
	  ssf=CreateSqlSessionFactory.getSsf();
  }
  
  /*
   *   <!-- 목록 출력 -->
		 <select id="reviewListData" resultType="com.sist.vo.ReviewVO"
		  parameterType="hashmap"
		 >
		   SELECT no,cno,id,name,msg,
		          TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') as dbday
		   FROM review 
		   WHERE cno=#{cno} AND type=#{type}
		 </select>
		 <!-- 리뷰 추가 -->
		 <insert id="reviewInsert" parameterType="com.sist.vo.ReviewVO">
		  INSERT INTO review VALUES(
		    review_no_seq.nextval,
		    #{cno},#{type},
		    #{id},#{name},
		    #{msg},SYSDATE
		  )
		 </insert>
		 <!-- 리뷰 수정 -->
		 <update id="reviewUpdate" parameterType="com.sist.vo.ReviewVO">
		  UPDATE review SET 
		  msg=#{msg}
		  WHERE no=#{no}
		 </update>
		 <!-- 리뷰 삭제 -->
		 <delete id="reviewDelete" parameterType="int">
		  DELETE FROM review
		  WHERE no=#{no}
		 </delete>
   */
  public static void reviewInsert(ReviewVO vo)
  {
	  try
	  {
		  SqlSession session=ssf.openSession(true);
		  session.insert("reviewInsert",vo);
		  session.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
  }
  public static void reviewDelete(int no)
  {
	  try
	  {
		  SqlSession session=ssf.openSession(true);
		  session.delete("reviewDelete",no);
		  session.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
  }
  public static void reviewUpdate(ReviewVO vo)
  {
	  try
	  {
		  SqlSession session=ssf.openSession(true);
		  session.update("reviewUpdate",vo);
		  session.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
  }
  /*
   *   selectList => List<~VO>
   *   selectOne => int , String , VO
   */
  public static List<ReviewVO> reviewListData(Map map)
  {
	  List<ReviewVO> list=null;
	  try
	  {
		  SqlSession session=ssf.openSession();
		  list=session.selectList("reviewListData",map);
		  session.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  return list;
  }
}