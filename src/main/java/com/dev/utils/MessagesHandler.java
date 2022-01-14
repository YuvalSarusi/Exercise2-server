package com.dev.utils;

import com.dev.Persist;
import com.dev.WebSocketConfig;
import com.dev.objects.Sale;
import com.dev.objects.UserObject;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

@Component
public class MessagesHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();
    private static Map<String, WebSocketSession> sessionMap = new HashMap<>();
    private static List<Sale> allSales = new ArrayList<Sale>();
    private static Date date = new Date();
    private static int totalSessions;

    @Autowired
    private Persist persist ;

    @PostConstruct
    public void init () {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    allSales = persist.getAllSales();
                    checkSalesDate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Map<String, String> map = Utils.splitQuery(session.getUri().getQuery());
        sessionMap.put(map.get("token"),session);
        totalSessions = sessionList.size();
        System.out.println("afterConnectionEstablished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("handleTextMessage");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
        System.out.println("afterConnectionClosed");
    }


    public void checkSalesDate(){
        for (Sale sale: allSales){
            checkSalesStarted(sale);
            checkSalesEnded(sale);
        }
    }

    public void checkSalesStarted(Sale sale){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String saleDate = dateFormat.format(sale.getStartTime());
        String currentDate = dateFormat.format(date.getTime());
        if (saleDate == currentDate){
            List<UserObject> userObjects = persist.getSaleUsers(sale.getId());
            for (UserObject userObject : userObjects){
                this.sendMessageSaleStarted(userObject.getToken(),sale);
                System.out.println("the sale " + sale.getDescription() + "started!!!");
            }
        }
    }

    public void checkSalesEnded(Sale sale){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String saleDate = dateFormat.format(sale.getEndTime());
        String currentDate = dateFormat.format(date.getTime());
        if (saleDate == currentDate){
            List<UserObject> userObjects = persist.getSaleUsers(sale.getId());
            for (UserObject userObject : userObjects){
                this.sendMessageSaleEnded(userObject.getToken(),sale);
            }
        }
    }

    public void sendMessageSaleStarted(String userToken, Sale sale){
        for(Map.Entry<String, WebSocketSession> session : sessionMap.entrySet()){
            if(userToken == session.getKey()){
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("saleStarted", sale.getShop().getName()+", "+sale.getDescription()+" just started!");
                    session.getValue().sendMessage(new TextMessage(jsonObject.toString()));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessageSaleEnded(String userToken, Sale sale){
        for(Map.Entry<String, WebSocketSession> session : sessionMap.entrySet()){
            if(userToken == session.getKey()){
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("saleEnded", sale.getShop().getName()+", "+sale.getDescription()+" just ended!");
                    session.getValue().sendMessage(new TextMessage(jsonObject.toString()));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}
