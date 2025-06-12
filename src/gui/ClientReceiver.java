package gui;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.Socket;

public class ClientReceiver
{

    private final TextArea chatArea;
    private final ListView<String> userlist;
    private final Socket socket;

    public ClientReceiver(TextArea chatArea,ListView<String> userlist,Socket socket)
    {
        this.chatArea=chatArea;
        this.userlist=userlist;
        this.socket=socket;
    }


}
