package SimpleChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA {
    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public static void main(String[] args) {
        new SimpleChatClientA().go();
    }

    public void go(){
        // GUI를 만들고 send button에 대한 리스너 등록
        JFrame frame = new JFrame("이상할 정도로 간단한 채팅 클라이언트");
        JPanel mainPanel = new JPanel();

        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);

        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        outgoing = new JTextField(20);
        JButton sendButton = new JButton("보내기");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);

        // setUpNetworking() 메소드를 호출
        setUpNetworking();

        // thread 생성 & start
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    // Socket과 PrintWriter를 만드는 함수
    private void setUpNetworking(){
        try {
            // Socket을 만듦
            sock = new Socket("127.0.0.1", 5000);

            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            //  PrintWriter를 만듦고, 그 PrintWriter를 writer 인스턴스 변수에 대입
            writer = new PrintWriter(sock.getOutputStream());

            System.out.println("Networking ESTABLISHED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) { // server에서 null이 아닌 것을 받을 때까지 계속 읽어온 값을 출력
                    System.out.println("read "+ message);
                    incoming.append(message+"\n"); // 스크롤 텍스트 영역에 새 메시지 추가
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
} // 외부 클래스 끝
