import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{
    
    private static Socket clienSocket;
    private static BufferedWriter bw;
    private static BufferedReader br;
    public static String tmp=null;
    public static Thread thread;

    public static void main(String[] args)throws UnknownHostException,IOException {
        clienSocket=new Socket("127.0.0.1",5050);
        bw=new BufferedWriter(new OutputStreamWriter(clienSocket.getOutputStream()));
        br=new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
        thread=new Thread(Connection);
        thread.start();
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine();
        bw.write(input+"\n");
        bw.flush();
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        clienSocket.close();
    }
    private static Runnable Connection=new Runnable() {
        public void run(){
            try{
                while(clienSocket.isConnected()){
                    tmp=br.readLine();
                    System.out.println(tmp);
                }
            }catch(Exception exception){

            }
        }
    };
}