package com.sist.manager;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.*;
import java.io.*;
import java.util.*;
import com.sist.vo.*;
@ServerEndpoint(value="/chat",configurator = WebSocketSessionConfigurator.class)
public class ChatManager {
	// 접속자 저장
	private static Map<Session, ChatVO> users = 
			Collections.synchronizedMap(new HashMap<Session, ChatVO>());
	// 접속시 처리
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception{
		ChatVO vo = new ChatVO();
		HttpSession hs = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		vo.setId((String)hs.getAttribute("id"));
		vo.setName((String)hs.getAttribute("name"));
		vo.setSession(session);
		
		users.put(session,vo);
		
		// 입장 메시지
		Iterator<Session> it = users.keySet().iterator();
		while(it.hasNext()) {
			Session ss = it.next();
			if(ss.getId()!=session.getId()) { // 본인이 아닌 경우
				ss.getBasicRemote().sendText("msg:[알림]" + vo.getName()+ "님이 입장하셨습니다");
				
			}
			
		}
		System.out.println("클라이언트 접속" + vo.getId() +","+ vo.getName() +"," + vo.getSession());
		
	}
	// 접속 해제 처리 =>
	@OnClose
	public void onClose(Session session) throws Exception{
		Iterator<Session> it = users.keySet().iterator();
		while(it.hasNext()) {
			Session ss = it.next();
			ChatVO vo = users.get(session);
			if(ss.getId()!=session.getId()) { // 본인이 아닌 경우
				ss.getBasicRemote().sendText("msg:[알림]" + vo.getName()+ "님이 퇴장하셨습니다");
				
			}
		}
		System.out.println("클라이언트 접속 종료"  + users.get(session).getName());
		users.remove(session);

	}
	@OnError
	public void onError(Session session, Throwable ex) {
		ex.printStackTrace();
	}

	// 메세지 처리
	@OnMessage
	public void onMessage(String message, Session session) throws Exception{
		System.out.println("수신된 메시지: " + message + "," + users.get(session).getName());
		Iterator<Session> it = users.keySet().iterator();
		while(it.hasNext()) {
			Session ss = it.next(); // 접속자 전체
			ChatVO vo = users.get(session); // 보낸사람
			if(session.getId()==ss.getId()) { // 본인
				ss.getBasicRemote().sendText("my:[" + vo.getName() + "]" + message);
			}else { // 상대방
				ss.getBasicRemote().sendText("you:[" + vo.getName() + "]" + message);
			}
		}
	}
}
