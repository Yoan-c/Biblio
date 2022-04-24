package com.studi.biblio.session;

import com.studi.biblio.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class Session {

    public Boolean creatSession(User user, HttpServletRequest request) {

        if (user != null) {
            HttpSession session = request.getSession();

                String sessionUID = (String) request.getSession().getAttribute("USER_SESSION");
                if (sessionUID == null) {
                    session.setAttribute("USER_SESSION", String.valueOf(user.getId()));
                    session.setAttribute("mail", user.getEmail());
                    System.out.println("SESSION TEST " + request.getSession());
                    System.out.println("SESSION TEST 2 " + request.getSession().getAttribute("USER_SESSION"));
                    return true;
                }

        }
        return false;
    }
    public Boolean getSession(User us, HttpServletRequest request) {

        if (us != null) {
            String sessionUID = (String) request.getSession().getAttribute("USER_SESSION");
            if (sessionUID == null || !sessionUID.equals(us.getId())){
                return false;
            }
            else {
                System.out.println("SESSION USER "+ sessionUID);
                return true;
            }
        }
        return false;
    }


}
