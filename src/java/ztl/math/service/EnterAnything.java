/*
 * This Application and all Its resources are the property of
 * John Mtonga(Application Developer)
 */
package ztl.math.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ztl.math.obj.Menu;
import ztl.math.obj.RequestType;
import static ztl.math.obj.RequestType.CONTINUE;
import static ztl.math.obj.RequestType.TERMINATE;
import ztl.math.obj.Session;
import ztl.math.obj.USSD;

/**
 *
 * @author user
 */
public class EnterAnything extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     *
     * http://localhost:8080/ZamtelUSSDVasServicesTest/DataBundleUSSDService?&TransId=88237813&RequestType=2&MSISDN=260950003972&SHORTCODE=355&AppId=302&USSDString=*355%23
     *
     * RULES:
     *
     * to implement single socket per session login will be done only once...
     * unsubscribe will not logout and will have to have service.logout
     * immediately after if need. subscribe will logout at the end .. login will
     * be called with list phs, or OfferDAO.getCurrentOffer hence the login will
     * have to be done by you after this.. a logout shall be called if an
     * exception at the very end.... Good luck..
     *
     * * # encoded is %23
     * @throws IOException if an I/O error occurs
     */
    static Logger log = Logger.getLogger("USSDBUNDLES");
    static Logger successLog = Logger.getLogger("SUCCESSLOGS");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String transactionID = request.getParameter("TransId");
        int requestType = Integer.parseInt(request.getParameter("RequestType"));
        String MSISDN = request.getParameter("MSISDN");
        String shortCode = URLDecoder.decode(request.getParameter("SHORTCODE"));
        int applicationID = Integer.parseInt(request.getParameter("AppId"));
        String ussdString = URLDecoder.decode(request.getParameter("USSDString"));

        USSD ussd = new USSD();
        ussd.setTransactionID(transactionID);
        ussd.setMSISDN(MSISDN);
        ussd.setRequestType(RequestType.getRequestType(requestType));
        ussd.setShortCode(shortCode);
        ussd.setApplicationID(applicationID);
        ussd.setUSSDString(ussdString);
        
        log.info(String.format("REQUEST: {%s}", ussd.toString()));
        successLog.info(String.format("REQUEST: {%s}", ussd.toString()));

 
        
        try {

 
            switch (ussd.getRequestType()) {
                case INITIATE:

                    String str = ussd.getUSSDString().replace(String.format("*%s*", ussd.getShortCode()), "");
                    
                    //System.out.println("string is" +str);

                    //out.println("connected to " +alreadySubscribedTo +"at price plan "+ selectedCheck.getPricePlan());
                    switch (str) {
 
                        default:
                            Session session = new Session();
                            session.setId(ussd.getTransactionID());
                            session.setMSISDN(ussd.getMSISDN());
                            session.setMenu(null);
                            SessionDAO.createSession(session);
                            session.setCurrentPage(0);
                            ussd.setRequestType(RequestType.CONTINUE);
                            
                            ussd.setUSSDString(getMenu());
                            break;
                    }

                    break;

                case CONTINUE:
                    
                    
                    Session s = SessionDAO.getSession(ussd.getMSISDN());
                    if (!s.getId().equals(ussd.getTransactionID())) {
                        log.info(String.format("TRANSACTION ID ERROR: Inconsistent TransactionID {session: %s, url: %s, %s}", ussd.getTransactionID(), s.getId(), ussd.getMSISDN()));
                        throw new Exception("Temporarily Failed to access One Coin Menu , \nPlease try again.");
                    }

                    String option = ussd.getUSSDString().replace(String.format("*%s*", ussd.getShortCode()), "");
                    
                    String text = option;
                    
                    //text = URLEncoder.encode(text,"UTF-8");
                    
                    if(true)
                    {
                        throw new Exception("You entered " +text);
                    }
                    
                    
                    //out.println(option);
                    
                  

                   
                    Menu m = null;

                    if (s.getMenu() == null) {
                        m = Menu.getMenu(Integer.parseInt(option));
                        s.setMenu(m);
                        s.setState(1);

                    } else {
                        m = s.getMenu();
                    }

                    if (m == null) {
                        throw new Exception("Invalid Menu Selection");
                    }

                    switch (m) {

                        case Addition:
                            switch (s.getState()) {

                                case 1:


                                    StringBuilder sb = new StringBuilder("Please enter your first Number\n");
                                    sb.append(" # Back");
                                    
                                    ussd.setUSSDString(sb.toString());

                                    s.setState(2);
                                    SessionDAO.createSession(s);

                                    break;

                                case 2:
                            

                                    
                                    ussd.setUSSDString("you have entered first number as : " +option+", Please enter your second number");
                                    s.setFirstNumber(Integer.parseInt(option));
                                    s.setState(3);
                                    ussd.setRequestType(CONTINUE);
                                    SessionDAO.createSession(s);

                                    break;
                                    
                               case 3:
                                    
                                    s.setSecondNumber(Integer.parseInt(option));
                                    
                                    ussd.setUSSDString("you have entered second number as " +option+"");
                                    
                                    int sum = s.getFirstNumber() + s.getSecondNumber();
                                            
                                    ussd.setUSSDString("The addition of the 2 numbers is : " +sum +" please enter 0 to exit,or # to restart");
                                    
                                    
                                                                      
                                    ussd.setRequestType(CONTINUE);
                                    
                                    SessionDAO.createSession(s);



                                    break;

                            }
                            break;

                        default:
                            ussd.setRequestType(TERMINATE);
                            ussd.setUSSDString("Invalid menu selection, Please try again");
                            SessionDAO.deleteSession(MSISDN);
                            break;
                    }
            }
        } catch (Exception ex) {

            //use request type 1 if exception was thrown from initiate
            if (ussd.getRequestType() == RequestType.INITIATE) {

                ussd.setRequestType(RequestType.INITIATE); //leave it as initiate..
            } else {
                ussd.setRequestType(RequestType.TERMINATE);
            }

            ussd.setUSSDString(ex.getMessage());
            Session s = new Session();
            s.setMSISDN(MSISDN);
            SessionDAO.createSession(s);
            log.fatal(ex);
        }
        out.print(ussd.toString());
        log.info(String.format("RESPONSE: {%s}", ussd.toString()));
    }

    public String getMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to test\n");
        sb.append("enter anything :");

        
        
        return sb.toString();
    }



    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Log4JInitServlet is initializing log4j");
        //String log4jLocation = config.getInitParameter("log4j-init-file");

        
        ServletContext sc = config.getServletContext();
         
        String log4jLocation = sc.getInitParameter("log4j-init-file");
        
        //System.out.println("log4jLocation at : " + log4jLocation.toString());

        if (log4jLocation == null) {
            System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String webAppPath = sc.getRealPath("/");
            String log4jProp = webAppPath + log4jLocation;

            File configFile = new File(log4jProp);
            if (configFile.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                PropertyConfigurator.configure(log4jProp);
            } else {
                System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
        super.init(config);
    }

//    public void init() {
//        String prefix = getServletContext().getRealPath("/");
//        String file = getInitParameter("log4j-init-file");
//        // if the log4j-init-file is not set, then no point in trying
//        if (file != null) {
//            PropertyConfigurator.configure(prefix + file);
//        }
//    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
