package com.securecode.Web;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Component
@RequestScope
public class Alert {

    public enum Type{
        Error,
        Info
    }

    private ArrayList<String> infoAlerts = new ArrayList<String>();
    private ArrayList<String> errorAlerts  = new ArrayList<String>();


    public ArrayList<String> getInfos() {
        return infoAlerts;
    }

    public ArrayList<String> getErrors() {
        return errorAlerts;
    }

    public void addAlert(Type type, String message) {
        if (type == Type.Info)
            this.infoAlerts.add(message);
        else if (type == Type.Error)
            this.errorAlerts.add(message);

    }
}