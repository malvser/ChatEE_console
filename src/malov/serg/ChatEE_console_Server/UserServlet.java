package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "user",urlPatterns = "/user")
public class UserServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);
        User user = User.fromJSON(bufStr);
        String param = parseParam(req.getQueryString());
        //--------------------------------------------------
        gResp(UserList.findUserSetStatus(user,param),resp.getOutputStream());

    }

    public byte[] requestBodyToArray(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[12240];
        int r;

        do {
            r = inputStream.read(buf);
            if (r > 0) byteArrayOutputStream.write(buf, 0, r);
        } while (r != -1);

        return byteArrayOutputStream.toByteArray();
    }

    private String parseParam(String param){
        String[] p = param.split("=");
        return p[1];
    }

    private void gResp(Boolean b,OutputStream os){
        try {
            try {
                os.write(toJson(b).getBytes());
            } finally {
                os.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p=req.getParameter("userList");
        if(p.equalsIgnoreCase("all")){
            OutputStream os=resp.getOutputStream();
            UserList ul=UserList.getInstance();
            try {
                os.write(ul.toJsonNoPass().getBytes());
            }finally {
                os.close();
            }
        }
    }

    private static <E>String toJson(E obj){
        Gson gson=new GsonBuilder().create();
        return gson.toJson(obj);
    }
}
