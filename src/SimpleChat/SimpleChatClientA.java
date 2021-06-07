package SimpleChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA {
    JTextField outgoing;
    PrintWriter writer;
    Socket sock;

    public void go(){
        // GUI를 만들고 send button에 대한 리스너 등록
        JFrame frame = new JFrame("이상할 정도로 간단한 채팅 클라이언트");
        JPanel mainPanel = new JPanel();
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("보내기");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);
        // setUpNetworking() 메소드를 호출
        setUpNetworking();
    }

    public class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 텍스트 필드로부터 텍스트를 알아낸 다음
                writer.println(outgoing.getText());
                // writer(PrintWriter 객체)를 써서 서버로 보냄
                writer.flush();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    // Socket과 PrintWriter를 만드는 함수
    private void setUpNetworking(){
        try {
            // Socket을 만듦
            sock = new Socket("127.0.0.1", 5000);
            //  PrintWriter를 만듦고, 그 PrintWriter를 writer 인스턴스 변수에 대입
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("Networking ESTABLISHED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new SimpleChatClientA().go();
    }
} // 외부 클래스 끝
