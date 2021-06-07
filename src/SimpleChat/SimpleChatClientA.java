package SimpleChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;



public class SimpleChatClientA {
    JTextField outgoing;
    PrintWriter writer;
    Socket sock;

    public void go(){
        // GUI를 만들고 send button에 대한 리스너 등록
        // setUpNetworking() 메소드를 호출
    }

    private void setUpNetworking(){
        // Socket을 만들고 PrintWriter를 만듦
        // 그 PrintWriter를 writer 인스턴스 변수에 대입
    }

    public class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 텍스트 필드로부터 텍스트를 알아낸 다음
            // writer(PrintWriter 객체)를 써서 서버로 보냄
        }
    } // SentButtonListener 내부 클래스 끝
} // 외부 클래스 끝
