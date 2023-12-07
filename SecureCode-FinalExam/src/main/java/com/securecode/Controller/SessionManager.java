package com.securecode.Controller;

import java.util.HashMap;

public class SessionManager {
    

    private static HashMap<String, Integer> sessions;

    public SessionManager() {
       sessions = new HashMap<String,Integer>();
    }


    public static void SetSession (String sessionid, Integer userid) {
        sessions.put(sessionid, userid);
    }


    public static String GenerateNewSessionKey() {
        int number = (int)(Math.random() * 10000);
        return String.valueOf(number);
    }

    private static Integer GetSession (String sessionid) {
        return sessions.get(sessionid);
    }


    public static int verifycookie(String sessionid) {
    
        Integer memberid = GetSession(sessionid);

        if (memberid == null) 
            return -1;

        return memberid.intValue();

    }


}
