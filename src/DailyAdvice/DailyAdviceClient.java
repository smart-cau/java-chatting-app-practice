package DailyAdvice;

import java.io.*;
import java.net.*;

public class DailyAdviceClient {
    public void go(){
        try {
            Socket s = new Socket("127.0.0.1", 4242); // socket 생성

            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader); // socket으로부터의 input stream에 대한 InputStreamReader에 BufferedReader를 연쇄시킴

            String advice = reader.readLine(); // BufferedReader의 메소드를 호출할 때, 그 객체에서는 문자들이 어디에서 오는지에 대해서 전혀 신경 안씀
            System.out.println("오늘 당신은 꼭 "+ advice);

            reader.close(); // 모든 stream 닫기

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();
    }
}
