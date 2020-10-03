import java.net.*;
import java.io.*;
class Server{



    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server()
    {
        try {
            server= new ServerSocket(7777); 
            System.out.println("serever is ready for cliet connection...");
            System.out.println("waiting....");
           socket= server.accept();

           br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

           out=new PrintWriter(socket.getOutputStream());


           startReading();
           startWriting();
        
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public void startReading()
    {
            Runnable r1=()->{
                    System.out.println("reader starts here...");
                    try{
                    
                    while(!socket.isClosed())
                    {
                        
                        String msg=br.readLine();
                        if(msg.equals("exit"))
                        {
                            System.out.println("client terminated the chat..");
                            break;
                        }
                        System.out.println("Client : "+msg);
                    }  
                }
                catch(Exception e)
                {
                   // e.printStackTrace();
                   System.out.println("connection is closed");
                }   

            };new Thread(r1).start();
    }

    public void startWriting()
    {
        Runnable r2=()->{
            System.out.println("writer starts here...");
            try {
            while(!socket.isClosed())
           {
               
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));   
                String content=br1.readLine();
                out.println(content);
                out.flush();
                if(content.equals("exit"))
                {
                    socket.close();
                    break;
                }

           }
           
        } catch (Exception e) {
           // e.printStackTrace();
           System.out.println("connection is closed ");
         }
    
        };new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("server is going to start here....");
        new Server();
    }
}