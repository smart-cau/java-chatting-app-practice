package SimpleChat;

public class SimpleChatClients {
    public static void main(String[] args) {
        SimpleChatClient clientA = new SimpleChatClient("정현우");
        SimpleChatClient clientB = new SimpleChatClient("리미티드");
        clientA.go();
        clientB.go();
    }
}
