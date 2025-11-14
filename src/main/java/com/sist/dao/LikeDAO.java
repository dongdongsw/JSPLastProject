package com.sist.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.CreateSqlSessionFactory;

public class LikeDAO {

	private static SqlSessionFactory ssf;
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	 <insert id="likeOn" parameterType="hashmap">
		INSERT INTO all_like
		VALUES(
			al_lno_seq.nextval,
			#{type},
			#{rno},
			#{id}
		)
	</insert>
	<delete id="likeOff" parameterType="hashmap">
		DELECT FROM all_like
		WHERE rno = #{rno} AND id=#{id} AND type = #{type}
	</delete>
	<update id="likeCountIncrement" parameterType="hashmap">
		UPDATE ${table} SET
		likecount = likecount + 1
		WHERE ${checks} = #{rno}
	</update>
	*/
	public static int likeOn(Map map) {
		int count = 0;
		SqlSession session = null;
		try {
			session  = ssf.openSession();
			session.insert("likeOn",map);
			session.update("likeCountIncrement",map);
			session.commit();
			count = session.selectOne("likeCount",map);
			session.close();
		} catch (Exception ex) {
			session.rollback();
			ex.printStackTrace();
		}
		return count;
	}
	
	/*
	<update id="likeCountDecrement" parameterType="hashmap">
		UPDATE ${table} SET
		likecount = likecount - 1
		WHERE ${checks} = #{rno}
	</update>
	<select id="likeCount" parameterType="hashmap" resultType="int">
		SELECT NVL(likecount,0) as likecount
		FROM ${table}
		WHERE ${checks} = #{rno}
	</select>
	<select id="likeCheck" resultType="int" parameterType="hashmap">
		SELECT COUNT(*) FROM all_like
		WHERE rno = #{rno} AND type=#{type} AND id= #{id}
	</select>
	 */
	public static int likeOff(Map map) {
		int count = 0;
		SqlSession session = null;
		try {
			session  = ssf.openSession();
			session.delete("likeOff",map);
			session.update("likeCountDecrement",map);
			session.commit();
			count = session.selectOne("likecount",map);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	public static int likeCheck(Map map) {
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("likeCount",map);
			session.close();
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		return count;
	}
}
