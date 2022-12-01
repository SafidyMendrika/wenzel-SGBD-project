package socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Timer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThread extends Thread{
    long timeout;
    Socket client;
    SocketServeur classDefine;
    
    public long getTimeout() {
        return timeout;
    }

    public SocketServeur getClassDefine() {
        return classDefine;
    }

    public void setClassDefine(SocketServeur classDefine) {
        this.classDefine = classDefine;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public MultiThread(SocketServeur classDefine ,Socket client, long timeout) {
        this.setTimeout(timeout);
        this.setClient(client);
        this.setClassDefine(classDefine);
    }
    
    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            String clientRequest = classDefine.getClientRequest(ois);
            System.out.println("Client request: " + clientRequest);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            classDefine.sendResponseToClientRequest(clientRequest, oos);
            ois.close();
            oos.close();
            client.close();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        
    }
}