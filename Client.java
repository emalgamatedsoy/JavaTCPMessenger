import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

/**
 *
 * @author Patrick
 */
public class Client {
        
		public static void main(String[] args){
			try
			{
				int port;
				String ip, uname;
				Scanner scan = new Scanner(System.in);
				InetAddress ipaddr = InetAddress.getLocalHost();
				
				System.out.println("Please enter an ip address");
				ip = scan.next();

				System.out.println("Please enter a port to connect to ");
				port = scan.nextInt();

				System.out.println("Please enter a username");
				uname = scan.next();

				
				Socket server = new Socket(ip, port);
				PrintWriter printout = new PrintWriter(server.getOutputStream(), true);
				BufferedReader buffread = new BufferedReader(new InputStreamReader(server.getInputStream()));
				printout.println("Connection with " + uname + " started : " + ipaddr.getHostAddress().trim() + " " + new java.util.Date().toString());
				
				
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
			catch(IOException e)
			{}
		}
		
		
        
}
    
