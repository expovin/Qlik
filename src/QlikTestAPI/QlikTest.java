package QlikTestAPI;

import java.io.IOException;
 
 
public class QlikTest {


    public static void main(String args[]) {
    	Base b = new Base();
    	
    	try {
			b.TicketRequest("POST", "WIN2012_SENSE01", "QSManager", "WIN2012_SENSE01");
	//		b.TicketRequest("POST", "2008R2-0", "QlikService", "2008R2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }       
 
}

