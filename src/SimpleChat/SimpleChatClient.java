package SimpleChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
    JTextArea incoming;
    JTextField outgoing;
    Socket sock;
    /* Writer & Reader -> "text" I/O에 사용. 다른 자료는 다른 input & output stream 써야 함 */
    PrintWriter writer; // byte to char
    BufferedReader reader; // client <- server(read from server)

    private String clientName;

    public SimpleChatClient(String clientName) {
        this.clientName = clientName+": ";
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

        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.setSize(650, 500);
        frame.setVisible(true);

        // 왜 아래 코드를 하면 안되는 건가...
//        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//        frame.setSize(400, 500);
//        frame.setVisible(true);
//
//        // setUpNetworking() 메소드를 호출
//        setUpNetworking();
//
//        // thread 생성 & start
//        Thread readerThread = new Thread(new IncomingReader());
//        readerThread.start();
    }

    // 네트워크 연결. reader와 writer 정의
    private void setUpNetworking(){
        try {
            // Socket을 만듦
            sock = new Socket("127.0.0.1", 5000);

            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);

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
                String sendText = clientName+ ": " +outgoing.getText();
                writer.println(sendText);
                // writer(PrintWriter 객체)를 써서 서버로 보냄
                writer.flush(); // 남아있는 text가 없게 .flush()로 모든 내용이 출력되도록 함
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
