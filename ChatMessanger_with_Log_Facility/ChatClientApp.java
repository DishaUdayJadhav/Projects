import controller.ChatClientController;
import model.ChatClientModel;
import view.ChatClientView;

public class ChatClientApp 
{
    public static void main(String[] args) 
    {
        ChatClientModel model = new ChatClientModel();
        ChatClientView view = new ChatClientView();
        
        ChatClientController controller = new ChatClientController(model, view);
        controller.showView();
    }
}
