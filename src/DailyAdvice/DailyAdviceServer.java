package DailyAdvice;

import java.io.*;
import java.net.*;

public class DailyAdviceServer {

    String[] adviceList = {"평소 안먹던 메뉴를 먹어보세요", "시간이 남는다면, 인라인 연습을 해보세요", "자전거 타고 퇴근하세요"};

    private String getAdvice() {
        int random = (int)(Math.random() * adviceList.length);
        return adviceList[random];
    }

    public void go() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242); // server socket으로 4242번 포트로 들어오는 클라이언트 요청을 listening

            while(true) {
                Socket sock = serverSocket.accept(); // .accept() 메소드는 요청이 들어 올 때까지 그냥 기다림. client 요청이 들어오면 client와의 통신을 위해 (현재 안쓰는 포트에 대한) Socket을 return함

                PrintWriter writer = new PrintWriter(sock.getOutputStream()); // client에 대한 socket 연결을 써서, PrintWriter를 만듦
                String advice = getAdvice();
                writer.println(advice); // 클라이언트에 String 조언메세지(advice)를 보냄
                writer.close(); // Socket 닫기
                System.out.println(advice);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
