package com.sist.vo;

import java.util.Date;

import lombok.Data;

/*
	JNO     NOT NULL NUMBER       
	TYPE             NUMBER       
	RNO              NUMBER       
	ID               VARCHAR2(20) 
	REGDATE          DATE  
 */
@Data
public class JjimVO {

	private int jno, type, rno;
	private String id;
	private Date regdate;
	
	private FoodVO fvo = new FoodVO();
	private GoodsVO gvo = new GoodsVO();
	private RecipeVO rvo = new RecipeVO();
}
