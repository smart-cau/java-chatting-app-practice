package SimpleChat;

import com.sun.codemodel.internal.JClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Iterator;

public class SimpleChatServer {
    ArrayList clientOutputStreams;

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList<JClass>();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t  = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        // 생성자. sock & reader 값 설정
        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                // sock.getInputStream() -> connection stream. data를 byte 단위로 읽음
                // InputStreamReader & BufferedReader -> chain stream
                    // InputStreamReader = byte to char
                    // BufferedReader = 문자로 읽을 때 buffer로 효율적으로 read
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
