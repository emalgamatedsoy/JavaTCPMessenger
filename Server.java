import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

public class Server
{
    public static void main(String args[])
    {
		try{
			
			Scanner scan = new Scanner(System.in);
			String uname;
			int portnum;
			
			System.out.println("Please indicate a port > 1024 for the server to use");
			portnum = scan.nextInt();
			System.out.println("Please indicate a username");
			uname = scan.next();
			
            if(portnum > 1024)
			{
				ServerSocket server = new ServerSocket(portnum);
				InetAddress ipaddr = InetAddress.getLocalHost();
                System.out.println("Connection started : " + ipaddr.getHostAddress().trim() + " " + new java.util.Date().toString());
				Socket client = server.accept();
                PrintWriter printout = new PrintWriter(client.getOutputStream(), true);
                BufferedReader buffread = new BufferedReader(new InputStreamReader(client.getInputStream()));

				Thread send = new Thread(new Runnable(){
					String msg;
					@Override
					public void run()
					{
						while(true)
						{
							msg = uname + ">> " + scan.nextLine();
							printout.println(msg);
							printout.flush();
						}
					}
				});
				send.start();
							    
				Thread recieve = new Thread(new Runnable(){
					String msg;
					@Override
					public void run()
					{
						while(true)
						{
							try{
								msg = buffread.readLine();
								System.out.println(msg);
							}
							catch(IOException e)
							{}
						}
					}
				});
				recieve.start();
			}
        }
		
		catch(IOException e)
		{
			e.printStackTrace();
		}		
    }

}

