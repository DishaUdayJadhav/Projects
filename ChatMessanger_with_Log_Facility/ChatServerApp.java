import model.ChatServerModel;
import view.ChatServerView;
import controller.ChatServerController;

public class ChatServerApp 
{
    public static void main(String[] args) 
    {
        ChatServerModel model = new ChatServerModel();
        ChatServerView view = new ChatServerView();
        
        new ChatServerController(model, view);

        view.setVisible(true);
    }
}
