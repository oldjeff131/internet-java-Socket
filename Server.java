import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static int serverport = 5050;
    private static ServerSocket serverSocket;
    private static int count = 0;
    private static ArrayList<Socket> clients = new ArrayList<>();

    public static void main(String[] args, String x) {
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");
            System.out.println("Waiting for client connect");
            while (!serverSocket.isClosed()) {
                waitNewClient();
            }
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }
    }

    public static void waitNewClient() {
        try {
            Socket socket = serverSocket.accept();
            ++count;
            System.out.println("現在使用者個數:" + count);
            addNewClient(socket);
        } catch (IOException e) {
        }
    }

    public static void addNewClient(final Socket socket) throws IOException {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    clients.add(socket);
                    BufferedWriter bw;
                    bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader buf=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bw.write("hello"+"\n");
                    bw.flush();
                    String msg;
                    while ((msg-buf.readLine())!=null) {
                        System.out.println("msg: "+msg);
                        bw.write("回傳: "+msg+"\n");
                        bw.flush();
                    }
                }catch(IOException e){
                    e.getStackTrace();
                }finally{
                    //clients.remove(socket);
                    //--count;
                    //System.out.println("現在使用者個數"+count);
                }                
            }            
        });
        t.start();
    }

    public static void castMsg(String Msg) {
        Socket[] clientArrays = new Socket[clients.size()];
        clients.toArray(clientArrays);
        for (Socket socket : clientArrays) {
            try {
                BufferedWriter bw;
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(Msg + "\n");
                bw.flush();
            } catch (IOException e) {
            }

        }

    }
}