package com.securecode.Web;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.securecode.Controller.LoginController;
import com.securecode.Controller.SessionManager;
import com.securecode.DomainObject.UserDomainObject;
import com.securecode.DomainObject.ItemDomainObject;
import com.securecode.DomainPrimitive.SecureString;
import com.securecode.Model.ItemModel;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WebController {

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String login() {

        return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Login</h2>" +
			"  <form action=\"/login\" method=\"post\">" +
			"      <input name=\"username\" type=\"text\" placeholder=\"login\" value=\"\" />" +
			"      <input name=\"password\" type=\"password\" placeholder=\"password\" value=\"\" />" +
			"      <input type=\"submit\" value=\"login\" />" +
			"  </form>" +
			"</body>" +
			"</html>";
    }

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String login(HttpServletResponse response, String username, char[] password) {

		Alert alert = new Alert();

        SecureString securePassword = new SecureString(password);
    
		UserDomainObject item = LoginController.Login(alert, username, securePassword);



		if (item == null) {

			return 
				"<html>" +
				"<title></title>" +
				"<body>" +
				"  <h2>Login</h2>" +
				"  <div>Invalid Credentials</div>" +
				"  <form action=\"/login\" method=\"post\">" +
				"      <input name=\"username\" type=\"text\" placeholder=\"login\" value=\"\" />" +
				"      <input name=\"password\" type=\"password\" placeholder=\"password\" value=\"\" />" +
				"      <input type=\"submit\" value=\"login\" />" +
				"  </form>" +
				"</body>" +
				"</html>";

		}

		String sessionkey = SessionManager.GenerateNewSessionKey();
		SessionManager.SetSession(sessionkey, item.getUserId());


		Cookie cookie = new Cookie("Session", sessionkey);
		response.addCookie(cookie);


		String content = String.format("Welcome to the site %s", item.getUsername());

        return 
			"<html>" +
			"<title></title>" +
			"<body>" +
			"  <h2>Login</h2>" +
			"  <div>" + content + "</div>" +
			"  <div><a href=\"/item\">View My Items</a></div>" +
			"  <div><a href=\"/item/new\">Add Item</a></div>" +
            "</body>" +
			"</html>";
    }




    @CrossOrigin(origins = "*")
	@GetMapping(value = "/item", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String AllMessages(HttpServletResponse response, @CookieValue(value="Session") String sessionid) {

	  try{

		if (sessionid == null) {
			response.setStatus(401);
			return "Unauthorized";
		}

  		int userid = SessionManager.verifycookie(sessionid);


        Alert alert = new Alert();

        ArrayList<ItemDomainObject> items = ItemModel.GetAllItemsByUser(alert, userid);

        String itemsHTML = "";
        String itemTemplate = 
                "<div style='border: 1px solid black; margin: 1em; width=25%%;'>"
            +   "<div>Item Name:   %s</div>"
            +   "<div>Description: %s</div>"
            +   "<div><a href='/item/%s'>Details</a></div>"
            +   "</div>";
        
        for (ItemDomainObject item : items) {
            itemsHTML += String.format(itemTemplate, item.name, item.description, item.itemId);
        }

        return 
			"<html>" +
			"<title>My Items</title>" +
			"<body>" +
            "<h1>My Items</h1>" +
			"<div>" + itemsHTML + "</div>" +
    		"<div><a href='/item/new'>Add Item</a></div>" +
            "</body>" +
			"</html>";
    
	  }
	  catch (Exception ex) {
		return "error";
	  }
	}




	@CrossOrigin(origins = "*")
	@GetMapping(value = "/item/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String AllMessages(HttpServletResponse response, @CookieValue(value="Session") String sessionid, @PathVariable int id) {

	  try{

		if (sessionid == null) {
			response.setStatus(401);
			return "Unauthorized";
		}

  		int userid = SessionManager.verifycookie(sessionid);


        Alert alert = new Alert();

        ItemDomainObject message = ItemModel.GetItemById(alert, id);

        if (userid != message.userId) {
			response.setStatus(401);
			return "Unauthorized";            
        }

        String messageTemplate = 
                "<div style='border: 1px solid black; margin: 1em; width=25%%;'>"
            +   "<div>Item Id:     %s</div>"
            +   "<div>User Id:     %s</div>"
            +   "<div>User Name:   %s</div>"
            +   "<div>Description: %s</div>"
            +   "</div>";
        
       
        messageTemplate = String.format(messageTemplate, message.itemId, message.userId, message.name, message.description);

        return 
			"<html>" +
			"<title>Item Details</title>" +
			"<body>" +
            "  <h1>Item Details for Item Id: " + message.itemId + "</h1>" +
			"  <div>" + messageTemplate + "</div>" +
            "<div><a href='/item'>Back to My Items</a></div>" +
    		"<div><a href='/item/new'>Add Item</a></div>" +
            "</body>" +
			"</html>";
    
	  }
	  catch (Exception ex) {
		return "error";
	  }
	}



	@CrossOrigin(origins = "*")
	@GetMapping(value = "/item/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String AddItem(HttpServletResponse response, @CookieValue(value="Session") String sessionid) {

	  try{

            if (sessionid == null) {
                response.setStatus(401);
                return "Unauthorized";
            }

            int userid = SessionManager.verifycookie(sessionid);

            Alert alert = new Alert();
            String messageTemplate = 
                    "<form action='/item/new' method='POST'>"
                +    "    <label for='name'>Name</label><br>"
                +    "    <input id='name' name='name'><br><br>"
                +    "    <label for='desc'>Description</label><br>"
                +    "    <input id='desc' name='desc'><br><br>"
                +    "    <button type='submit'>Submit</button>"
                +    "</form>";

            return 
                "<html>" +
                "<title>Item Details</title>" +
                "<body>" +
                "  <div>" + messageTemplate + "</div>" +
                "<div><a href='/item'>Back to My Items</a></div>" +
                "<div><a href='/item/new'>Add Item</a></div>" +
                "</body>" +
                "</html>";
	    }
	    catch (Exception ex) {
	    	return "error";
        }
	}



	@CrossOrigin(origins = "*")
	@PostMapping(value = "/item/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
	public String AddItem(HttpServletResponse response, @CookieValue(value="Session") String sessionid, String name, String desc) {

	  try{

            if (sessionid == null) {
                response.setStatus(401);
                return "Unauthorized";
            }

            int userid = SessionManager.verifycookie(sessionid);

            if (userid < 0) {
                response.setStatus(401);
                return "Unauthorized";
            }

            Alert alert = new Alert();

            ItemDomainObject newItem = new ItemDomainObject(userid, name, desc); 

            ItemDomainObject createdItem = ItemModel.CreateItem(alert, newItem);

        
            String messageTemplate = 
                    "<div style='border: 1px solid black; margin: 1em; width=25%%;'>"
                +   "<div>Item Name:   %s</div>"
                +   "<div>Description: %s</div>"
                +   "<div><a href='/item/%d'>Details</a></div>"
                +   "</div>";

            messageTemplate = String.format(messageTemplate, createdItem.name, createdItem.description, createdItem.itemId);


                return 
                "<html>" +
                "<title>Created Item</title>" +
                "<body>" +
                "  <div>" + messageTemplate + "</div>" +
                "<div><a href='/item'>View My Items</a></div>" +
                "<div><a href='/item/new'>Add Item</a></div>" +
                "</body>" +
                "</html>";
        }
        catch (Exception ex) {
            return "error: " + ex.getMessage();
        }
	}
}
