package com.sist.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReserveVO {
	private int no, fno, ok ;
	private String id, reply, time, inwon, dbday, rday;
	private Date regdate;
	private FoodVO fvo = new FoodVO();
}
