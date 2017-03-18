package malov.serg.ChatEE_console_Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "getlist", urlPatterns = "/get")
public class GetListServlet extends HttpServlet {
    MessageList msgList=MessageList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ns=req.getParameter("n");
        int n=Integer.parseInt(ns);
        String to=req.getParameter("to");
        String room=req.getParameter("room");
        String jsonMessages=msgList.toJSON(n,to,room);
        if(jsonMessages!=null){
            OutputStream os=resp.getOutputStream();
            os.write(jsonMessages.getBytes());
        }
    }
}
