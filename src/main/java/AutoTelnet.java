
import java.io.InputStream;  
import java.io.PrintStream;  
 
import java.io.File;  
import java.io.FileOutputStream;   
import java.io.RandomAccessFile; 
import java.io.BufferedWriter; 
import java.io.FileWriter;

import org.apache.commons.net.telnet.TelnetClient; 
  
public class AutoTelnet {
    private TelnetClient telnet = new TelnetClient("VT220");  
    
    private InputStream in;  
    private PrintStream out;  
  
    // ��ͨ�û�����  
    public AutoTelnet(String ip, int port, String user, String password) {  
        try {  
            telnet.connect(ip, port);  
            in = telnet.getInputStream();  
            out = new PrintStream(telnet.getOutputStream());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** * ��ȡ������� * * @param pattern * @return */  
    public String readUntil() {  
        try {  
			byte [] tt=new byte[in.available()];
			int b;
			while((b=in.read(tt))!=-1){
				System.out.println(b);
				return new String(tt, "GBK"); 
			  }			
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** * д���� * * @param value */  
    public void write(String value) {  
        try {
						
            out.println(value);  
            out.flush();		
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** * ��Ŀ�귢�������ַ��� * * @param command * @return */  
    public String sendCommand(String command,int time) {  
        try {  
            write(command); 
			Thread.sleep(time);	
			return readUntil(); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** * �ر����� */  
    public void disconnect() {  
        try {  
            telnet.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    public static boolean writeTxtFile(String content,File  fileName)throws Exception{  
	RandomAccessFile mm=null;  
	boolean flag=false;  
	FileOutputStream o=null;  
	try {
    String iso = new String(content.getBytes("UTF-8"),"ISO-8859-1"); 
	o = new FileOutputStream(fileName);  
	  o.write(iso.getBytes("ISO-8859-1"));  
	  o.close();  
	//   mm=new RandomAccessFile(fileName,"rw");  
	//   mm.writeBytes(content);  
	flag=true;  
	} catch (Exception e) {  
	// TODO: handle exception  
	e.printStackTrace();  
	}finally{  
	if(mm!=null){  
	mm.close();  
	}  
	}  
	return flag;  
	}  

	 public static boolean createFile(File fileName)throws Exception{  
	 try{  
	  if(!fileName.exists()){  
	   fileName.createNewFile();   
	  }  
	 }catch(Exception e){  
	  e.printStackTrace();  
	 }  
	 return true;  
	} 
  public static void contentToTxt(String filePath, String content) {  
       try {  
           File f = new File(filePath);  
           if (f.exists()) {  
               //System.out.print("�ļ�����");  
           } else {  
               System.out.print("�ļ�������");  
               //f.createNewFile();// �������򴴽�  
           }  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(content);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
  
 
    public static void main(String[] args) {  
        try {  
            System.out.println("����Telnet...");  
            String ip = args[0];  
            int port = Integer.parseInt(args[1]); 
			System.out.println(port);
            String user = "jwj";  
            String password = "BUXIANGSHEMIMA@1";  
            AutoTelnet telnet = new AutoTelnet(ip, port, user, password);	
			//telnet.sendCommand(user,500);
			//telnet.sendCommand(password,1000);			
            String r1 = telnet.sendCommand(args[2],Integer.parseInt(args[4]));  
            //String r2 = telnet.sendCommand("pwd");  
            //String r3 = telnet.sendCommand("sh a.sh");  
            System.out.println("��ʾ���");  
            System.out.println(r1);  
			contentToTxt(args[3],r1);
            //System.out.println(r2);  
            //System.out.println(r3);  
            telnet.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  