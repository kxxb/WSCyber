/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cyber.ws;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.lang.System.out;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;
import java.util.Scanner;
import java.util.logging.FileHandler;
import javax.sql.DataSource;





//import org.Cyber.*;
import org.CyberPlat.*;
import static java.lang.System.*;
/**
 *
 * @author Peter.s
 */

public class CyberWS {
    private DataSource ds;
    private OracleDataSource orclds;
    File filelog=new File("filelog.log");
    java.util.logging.FileHandler Fh;
    private static Logger  logger= Logger.getLogger(CyberWS.class.getName()); 
    private Connection connection;
    ResControl   res=new ResControl();
       private static final String PASS="1111111111";
    /** РЎРµСЂРёР№РЅС‹Р№ РЅРѕРјРµСЂ Р±Р°РЅРєРѕРІСЃРєРѕРіРѕ РєР»СЋС‡Р° */
    private static int BANK_KEY_SERIAL=64182;
     private IPrivKey sec=null;
     private IPrivKey pub=null;
    String intofile="",updateFails="";
    
    
    
    
    
    
    public void cyberStart(java.lang.String go)  {
try{
     sec=IPriv.openSecretKey("secret.key",PASS);
      pub=IPriv.openPublicKey("pubkeys.key",BANK_KEY_SERIAL);   
}catch(Exception e){}
      res.start();
        
    }

    public int cyberStop(java.lang.String stopin) {
        //TODO implement this method
       int result=1;
       boolean s = res.stop();
       done(); 
        if(s) result=0;
            
        out.println("Stop : "+result);
        return result;
    }
    
      boolean ins(String s) {// вставляем доп строку данных в уже существующие данные файла 

boolean succes=false;
//char c;
File file = new File("Input.txt");
//File file = new File("/home/petr/Input.txt");
ByteArrayOutputStream bo;
FileOutputStream fo;
      
            
           // ss+="\n"+s;  
try {      
                bo= new ByteArrayOutputStream();
                  fo = new FileOutputStream(file);
                 // b=strall.getBytes();      
                  
                  bo.write(s.getBytes());// из вх пар-ра
                  bo.writeTo(fo);
                  fo.close();
                 bo.close();
                  succes=true;
                  
                 
            
      } catch (FileNotFoundException f) {
                   succes=false; 
           out.println(f.toString());
              } catch (IOException f) {
                  out.println(f.toString());
                  // TODO 
                  succes=false; 
              }
finally{
    out.print(succes);
return succes;

}
    }
    
     public void poolInit(){
            String stcon = null;
 
     try {

orclds = new OracleDataSource();
ds = (DataSource)orclds;        

        
          } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex);
         out.println("Connection failed");
          stcon ="Connection failed";
            out.println(ex.toString());
            stcon+="\n"+ex.toString();
        
              
        } 
finally{
    //jTextArea1.setText(stcon);
    logger.info(stcon);


   } 
 }
    
    private static  void close(Connection con){
    if(con==null){
   return ;
    }
    try{
      if(!con.isClosed()){
        String strc =con.toString(); 
      out.println(strc);
      logger.info("con "+strc);
      con.close();
      logger.info("Closed con");
          }
       }catch(Exception e ){
logger.info(e.toString());
  } 
}
     void done()
    {
        
            sec.closeKey();
            pub.closeKey();
        out.println("pub closed");
        logger.info("pub closed" );
    }
    class ResType{
   private String opID,Pay_Id,Sd,Ap,Op,s_id_out,Ctn,Amount,url,Url_Type;

        public String getOpID() {
            return opID;
        }

        public void setOpID(String opID) {
            this.opID = opID;
        }

        public String getPay_Id() {
            return Pay_Id;
        }

        public void setPay_Id(String Pay_Id) {
            this.Pay_Id = Pay_Id;
        }

        public String getSd() {
            return Sd;
        }

        public void setSd(String Sd) {
            this.Sd = Sd;
        }

        public String getAp() {
            return Ap;
        }

        public void setAp(String Ap) {
            this.Ap = Ap;
        }

        public String getOp() {
            return Op;
        }

        public void setOp(String Op) {
            this.Op = Op;
        }

        public String getS_id_out() {
            return s_id_out;
        }

        public void setS_id_out(String s_id_out) {
            this.s_id_out = s_id_out;
        }

        public String getCtn() {
            return Ctn;
        }

        public void setCtn(String Ctn) {
            this.Ctn = Ctn;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl_Type() {
            return Url_Type;
        }

        public void setUrl_Type(String Url_Type) {
            this.Url_Type = Url_Type;
        }
    
    
    }
    
    public   class ResControl   {
        String req=null ;
        boolean proc = false;
        PreparedStatement prst=null,prst1=null; PreparedStatement  prst2=null, prst3=null;
        LinkedList<ResType> li ;
        ListIterator<ResType> lit;
        ResType restype;
       String genses="";
        private static final String ENC="windows-1251";
        private  long l;
        Thread t;
        int errorNum,operNum; 
        volatile boolean go;
        String [] rsArStr,storeArr;
        String sql = "Select" +
              " ope.Id As Oper_Id,"+
             "p.Id As Pay_Id, d.Sd,    d.Ap, d.Op, ope.session_id_out, p.Ctn, p.Amount,u.url,u.Id_Cp_s_Url_Type" +
             " From Cp_Dealer d, Cp_Opers ope, Cp_Url u, Cp_Pay p Where p.Id = ope.Id_Cp_Pay" +
             " And p.Id_Cp_Dealer = d.Id  And ope.Id_Cp_Url = u.Id " +
             "  And ope.Rec_State = 1  And ope.start_time <= Sysdate   And p.pay_status = 1";
        
        String update2 = "Update cp_opers t Set  t.rec_state = 2 Where t.Id = ?";
        
        
        
        String upd3  = " Update cp_opers oe  Set oe.Oper_Date = ?, oe.Code_Error = ?," +
                " oe.session_id_in = ?, oe.Oper_Result = ?, oe.Opname = ?,  oe.Account_cyb = ?, " +
                " oe.Errmsg =  ? , oe.rec_state = ?, " +
                " oe.Confirm_sign   = ?, oe.transid = ? ,oe.request_str = ?," +
                "  oe.responce_str = ?,oe.Date_Rec  = Sysdate ,oe.Start_Time  = Sysdate   Where oe.Id = ? ";
////        
////            Update Cp_Opers oe
////       Set 
////           oe.Oper_Date   = to_char(Sysdate,'dd:MM:YYYY')||'- T -'||to_char(Sysdate,'HH24:MI:SS'),
////           oe.Code_Error  = v_Code_Error_chn,
////           oe.session_id_in = rec0.session_id_out,
////           oe.Oper_Result = v_Oper_Result_chn,
////           oe.Opname      = 'MTS mobile tele systems ',
////           oe.Account_cyb     = '36998',
////           oe.Errmsg      = 'All right',
////           oe.Rec_State   = 0,
////           oe.Date_Rec    = Sysdate,
////           oe.Start_Time  = sysdate,
////           oe.Confirm_sign   = 0,
////           oe.transid = 'oi12sdu546',           
////           oe.request_str = '0000035401SM000000970000009700000121
////                             api99               00000990',
////           oe.responce_str = '0000035401SM000000970000009700000121
////                             api99               00000991'
////           Where Id = rec0.oper_id;
        
 
 
 
        
        ResControl(){
      
            IPriv.setCodePage(ENC);
            rsArStr=new String [9];
      
            li= new LinkedList<CyberWS.ResType>();
          
        
              try {
                    Fh = new FileHandler("CyberWSlog.xml");
                    logger.addHandler(Fh);
                } catch (IOException ex) {
                    Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                }
         
      
        }
        
        
        
        
      String[] sendRequest(String url,String request) 
    {  
        String resp=null,reQ="";
         String[] rClr_Drt = new String[7];
            /* Р§С‚РµРЅРёРµ РѕС‚РІРµС‚Р° */
            BufferedReader in = null;
            out.println(" send to  URL  : "+url);
            logger.info(" send to  URL  : "+url);
            try {
               try{
                rClr_Drt[3]=request;//  not подписанный запрос 
                rClr_Drt[2]="1";// подпись not ок 
                rClr_Drt[5]=" ";
                rClr_Drt[4]=" ";
                rClr_Drt[6]=" ";
               if(sec!=null)  {
                out.println( "sign method ");
                reQ=sec.signText(request);
                out.println( "\nSigning req : " + reQ);
                rClr_Drt[3]=reQ;// подписанный запрос 
                rClr_Drt[2]="0";// подпись ок 
                //out.println("");
                 }
               } 
              catch(IPrivException ex){
               rClr_Drt[2]="1"; // failed sign 
               rClr_Drt[3]=request;// неподписанный запрос 
               rClr_Drt[6]="1";
               out.println("Sig failed "+ex.toString());
              }
               catch (Exception ex) {
                    Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
               out.println("sign catch Exception "+ex.toString()); 
               rClr_Drt[2]="1"; // failed sign 
               rClr_Drt[3]=request;// неподписанный запрос 
               rClr_Drt[6]="1";
               }
               if(rClr_Drt[2].equals("0"))
               {
                reQ = "inputmessage=" + URLEncoder.encode(reQ, ENC);// урл кодирование + подписывается ЭЦП 
                
                URL u = new URL(url);
                URLConnection con = u.openConnection();
                con.setConnectTimeout(10000);
                rClr_Drt[6]="1";     
                con.setRequestProperty("input", reQ);
                rClr_Drt[6]="0";
                in = new BufferedReader(new InputStreamReader(con.getInputStream(), ENC));
                rClr_Drt[4]="0";
                 if(rClr_Drt[4].equals("0")) { 
                 char[] raw_resp = new char[1024];
                   int raw_resp_len = in.read(raw_resp);                
                     out.println("response readed");
                      StringBuffer s = new StringBuffer(); 
                       s.append(raw_resp, 0, raw_resp_len);
                        resp = s.toString();
                         getRespParams(resp, rsArStr);
                          out.println("Not verifyed response :  " + resp);
                        }
                rClr_Drt[0]=resp;// необработанный ответ 
              //  rClr_Drt[2]="1";// для верификации ответа 
                //pub.decryptText(resp) ;
               // out.println("Response decrypted");
                if(resp!=null){
                    try{
                        rClr_Drt[5]="1";// // дешифр ЭЦП -not ок 
                        rClr_Drt[1]=resp;// not обработанный ответ 
                        rClr_Drt[4]="0";// response https - ok 
                        if(pub!=null){
                        resp = pub.verifyText(resp);
                        rClr_Drt[5]="0";// // дешифр ЭЦП - ок 
                        rClr_Drt[1]=resp;// обработанный ответ 
                        out.println("Response verifyed ");
                        } // end of if 
                    }// проверка ответа  }
                    catch(IPrivException ex){
                        rClr_Drt[5]="1";// подпись не валидировалась 
                        out.println("verify failed "+ex.toString());
                     }
                    catch (Exception ex) {
                        Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                        out.println("verify catched Exception "+ex.toString());
                        rClr_Drt[5]="1";// подпись не валидировалась 
                     }  
                    }
            }// end of if sign ok 
                //if(resp!=null){rClr_Drt[2]="0";}
                
                   
            } catch (IOException ex) {
                Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                logger.info(ex.toString()+" CAtched ");
              rClr_Drt[4]="1"; //при обрыве соединения флаг массива (4) становится =1 
              rClr_Drt[0]=resp;
              rClr_Drt[1]=resp;
              rClr_Drt[5]=" ";
            }  try {
                   if(in!=null){ 
                       out.println("closing in ");
                       in.close();}
                 }
                
            finally {    
           updateResp(prst3, update2, reQ, resp, resp, update2, reQ, reQ, operNum, operNum, resp, req, resp, upd3, rsArStr);   //
            logger.info("exiting from sendrequest");    
            out.println("exiting from send request ");
            return rClr_Drt;
            }
      }
 
      
  

 public void start(){
  
  if(t==null){  
      
      t = new Thread(new Runnable(){      
         public void run(){
             logger.info("Thread "+ t.getName() +" started");
            
            while(go){
                        try {
                            goDB();
                            Thread.sleep(30000);
                        } catch (InterruptedException ex) {
                            logger.log(Level.SEVERE, ex.toString(), ex);
                            out.println(ex.toString());
                        }
                        catch(Exception ex){
                        out.println(ex.toString()+"Run catch exception ");
                        }     
            }
         }
      });
      out.println(t.isAlive());
      out.println(t.isDaemon());      
      t.setDaemon(false);
      out.println(t.isDaemon());
      go=true;
      t.start();
   }
  
   }  
  public boolean stop(){
      
      boolean stopped =false;
     
                try {
                    String thName = t.getName();
                    out.println(thName);
                    Thread.sleep(1000);
                   
                    go=false;
                    logger.info("thread " + thName + " stopped");
                } catch (InterruptedException ex) {
                    Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                }
  
      if(t==null){stopped = true;}
      logger.info("exiting from Stop method ");
      return stopped;
      
   }

  void updateResp(PreparedStatement ps ,String sysdate, String codeErr,
          String  session,String resUlt,
          String opName,String acnt,String errMes,
                int recSt,int sign,String transid,
                String reqStr,String respStr,String opId, String[] status){
      
      out.println("inside Update Params");
            try {
             ps.setString(1,null);
              ps.setString(2, null);
               ps.setString(3, null);
                ps.setString(4, null);
                 ps.setString(5, null);
                  ps.setString(6, null);
                   ps.setString(7, null);
                   ps.setInt(9, Integer.parseInt(status[2]));// рез-т выполнения подписания запроса                   
                    ps.setString(10, null); 
                    ps.setString(12,  status[0]);
                     ps.setString(13,  opId);        
                        out.println("13 "+opId);
                     //out.println("12 " +status[0]);
                 if(status[2].equals("1")){  
                     out.println("ststus [2]=1?  "+status[2] );
                     //В случае если ты не смог отправить в КиберПлат
                     //свой запрос, то есть не подписал Библиотекой 
                     // или проблемы со связью (в другом случае )
                      ps.setInt(8, 3);   
                     ps.setString(11,  status[3]);
                      //out.println("11 " +status[3]);                                                      
                     
                    }
                 
                if(status[5].equals("1")){
                    out.println("ststus [5]=1?  "+status[5] );
                // В случае от КиберПлата пришел ответ но подпись не валидировалась
              
                    ps.setInt(8, 0);// or 3 
                    ps.setInt(9, Integer.parseInt(status[2]));// рез-т выполнения подписания запроса       
                     ps.setString(11,  status[3]);
                        out.println("11 " +status[3]);                        
                                            
                       
                     
                }
                 if(status[4].equals("1")&status[6].equals("1")){
                    out.println("ststus [4]=1?  "+status[4]+"[6] " + status[6] );
                  //В случае если от Киберплат вообще ничего не пришло, 
                    //то есть был обрыв связи в момент ответа, 
                    //эти поля имеют следующие значения
                    ps.setInt(8, 3);
                     ps.setInt(9, Integer.parseInt(status[2]));// рез-т выполнения подписания запроса 
                      ps.setString(11,  status[3]);
                      //out.println("11 " +status[3]);                                               
                      }
                if(status[4].equals("0")&status[5].equals("0")&status[2].equals("0")&status[6].equals("0")){
                   out.println("ststus [4] "+status[4]+"status[5] "+status[5] + " [6] "+ status[6]+"[2]"+status[2]);
                 ps.setString(1, sysdate);
                out.println("1 "+sysdate);
                ps.setString(2, codeErr);              
                ps.setString(3, session.trim());
                out.println("3 " +session);                        
                ps.setString(4, resUlt);
                out.println("4 " +resUlt);                        
                ps.setString(5, opName);
                out.println("5 " +opName);                        
                ps.setString(6,  acnt);
                out.println("6 " +acnt);                        
                ps.setString(7,  errMes);
                out.println("7 " +errMes);                        
                ps.setInt(8, recSt);
                out.println("8 "+recSt);
                ps.setInt(9, Integer.parseInt(status[2]));
                out.println("9 " +status[2]);   //                      
                ps.setString(10,  transid);
                out.println("10 "+transid);
                ps.setString(11,  status[3]);
                out.println("11 " +status[3]);                        
                ps.setString(12,  status[1]);
                out.println("12 " +status[1]);                        
                }
                 ps.addBatch();
            } catch (SQLException ex) {
                Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex.toString());
                
            }
     
        
        }
     
   public  String[]  getRespParams(String str,String [] trgt ){
       Scanner scan=null;
       logger.info("GET RESPONSE PARAMETERS : ");
       try{  
           scan = new Scanner(str);
           while(scan.hasNext()){
           scan.findInLine("DATE=");
                  trgt[0] = scan.next();
                    out.println(trgt[0]); 
               scan.findInLine("SESSION");
                  trgt[1] = scan.next();
               out.println(trgt[1]);                
               scan.findInLine("ERROR=");
               trgt[2] = scan.next().trim();
               out.println(trgt[2]); 
               scan.findInLine("RESULT=");
                trgt[3] = scan.next();
               out.println(trgt[3]); 
              // trgt[4] ="";
               scan.findInLine("OPNAME=");
                   out.println(scan.findInLine("OPNAME="));trgt[4] = scan.next();out.println(trgt[4]); 
               
              // trgt[5] ="";
               scan.findInLine("ACCOUNT=");
                   out.println(scan.findInLine("ACCOUNT="));
               trgt[5] = scan.next();
               out.println(trgt[5]); 
               //trgt[6] ="";
               scan.findInLine("ERRMSG=");
                    
               out.println(scan.findInLine("ERRMSG="));
               trgt[6] = scan.next();
               out.println(trgt[6]); 
               
               //trgt[7] = "";
              // trgt[8] = "";
               scan.findInLine("TRANSID=");
               trgt[7] = scan.next();
               scan.findInLine("AUTHCODE=");
               trgt[8] = scan.next();
           }         
           }     
      
       catch(java.lang.NullPointerException nux){
       
       out.println("getResp catched NUX "+nux.toString());
           for(int i =0;i<trgt.length;i++){trgt[i]="";}
           out.println("trg[] is empty");
           logger.info("trg[] is empty");
       }
        finally{
          if(scan!=null) scan.close();
           out.println("scan closed");
           logger.info("exit from getRespParams");
           return trgt;
           }
           
   }
  
private void goDB(){
 String url="",rspnse="";
 ResultSet rset=null;
 
 int ret = 0;
 
       try {         
      Thread.sleep(10000); 
             
 if(ds==null){
 poolInit();
 }
   connection = ds.getConnection("cp","DfMAP001");
               if(connection!=null){
                   
           
                prst =  connection.prepareStatement(sql);
                prst1 = connection.prepareStatement(update2);
               prst2 = connection.prepareStatement("Update cp_opers t Set t.rec_state =1");
               prst3 = connection.prepareStatement(upd3);
                rset=prst.executeQuery();    
                
                
               while(rset.next()){
   
                   
           restype = new ResType();
           restype.setOpID(rset.getString(1));
           restype.setPay_Id(rset.getString(2));   
           restype.setSd(rset.getString(3));   
           restype.setAp(rset.getString(4));
           restype.setOp(rset.getString(5));
           restype.setS_id_out(rset.getString(6).trim());
           restype.setCtn(rset.getString(7));
           restype.setAmount(rset.getString(8));
           restype.setUrl(rset.getString(9));
           restype.setUrl_Type(rset.getString(10));
           li.add(restype);
           
           prst1.setString(1,restype.getOpID());
     
           prst1.addBatch();
            //
           
           restype=null;
               }                         // в процессе 
               int [] i = prst1.executeBatch(); // закидываем апдейт со знач 2 для резултсета, который закроется 
               out.println("Batch size : "+i.length);
               logger.info("Proccessing Statement 2 Batch size : "+i.length);
               // connection.commit();
                 if(rset!=null){
                    try{
                        rset.close();
                         }
                    catch(SQLException e3){
                    out.println(e3.toString());}                    
                    }
             
                out.println("Collection size "+li.size());
                logger.info("Collection size "+li.size());
             lit = li.listIterator();
               
             while(lit.hasNext()){
                 restype=lit.next();           
   if(restype.getUrl_Type().equals("1")) {
             //1  формирование ЭЦП + сохранение сообщения (может возникнуть исключение  )
       req= "SD="+restype.getSd()+"\r\n"+
            "AP="+restype.getAp()+"\r\n"+
            "OP="+ restype.getOp()+"\r\n"+
            //"SESSION="+restype.getS_id_out().trim()+"\r\n"+ // из базы 
            "SESSION="+genses+"\r\n"+
            "NUMBER="+restype.getCtn()+"\r\n"+     
            "AMOUNT="+restype.getAmount()+"\r\n";     
      
       url=restype.getUrl();
      
       out.println("\nRequest 1 : ");
       logger.info("\nRequest 1 : ");
       //rspnse=sendRequest("https://payment.cyberplat.ru/cgi-bin/es/es_pay_check.cgi", req);
       storeArr=sendRequest("https://payment.cyberRplat.ru/cgi-bin/es/es_pay_check.cgi", req);// bad link
        // out.println("\nResponse dirty  : "+storeArr[0]+" Response clear :  "+storeArr[1] );      // 3 Получение ответа от сервера Киберплат +  сохранение сообщения 
              // 4 Дешифрация ЭЦП 
       rsArStr=getRespParams(storeArr[1], rsArStr);
       updateResp(prst3,rsArStr[0], rsArStr[2], rsArStr[1], 
               rsArStr[3], rsArStr[4], rsArStr[5],
               rsArStr[6], 0, Integer.parseInt(storeArr[2]),
               rsArStr[7], storeArr[3], storeArr[0], restype.getOpID(),storeArr);
       // 5 Обновление базы 
                }
   if(restype.getUrl_Type().equals("2")) {
   
      req= "SD="+restype.getSd()+"\r\n"+
           "AP="+restype.getAp()+"\r\n"+
           "OP="+restype.getOp()+"\r\n"+
           //"SESSION="+restype.getS_id_out().trim()+"\r\n"+ // из базы 
           "SESSION="+genses+"\r\n"+
           "NUMBER="+restype.getCtn()+"\r\n" +           
           "AMOUNT="+restype.getAmount()+"\r\n";      
      //"ACCOUNT= \r\n""ACCOUNT= \r\n"+
       // 2  Отправка сообщения по НТТПС  (может возникнуть исключение  )
       url=restype.getUrl();
       out.println("Request 2 : ");
       logger.info("\nRequest 2 : ");
      //rspnse= sendRequest("https://payment.cyberplat.ru/cgi-bin/es/es_pay.cgi", req);
       storeArr=sendRequest("https://payment.cyberplat.ru/cgi-bin/es/es_pay.cgi", req);
     out.println("Response dirty  : "+storeArr[0]+" Response clear :  "+storeArr[1] ); 
     rsArStr=getRespParams(storeArr[1], rsArStr);
     updateResp(prst3,rsArStr[0], rsArStr[2], rsArStr[1], 
               rsArStr[3], rsArStr[4], rsArStr[5],
               rsArStr[6], 0, Integer.parseInt(storeArr[2]),
               rsArStr[7], storeArr[3], storeArr[0], restype.getOpID(),storeArr);
   }
   if(restype.getUrl_Type().equals("3")) {
        
    req="SESSION=" + restype.getS_id_out() + "\r\n";
       // 2  Отправка сообщения по НТТПС  (может возникнуть исключение  )
       url=restype.getUrl();
       out.println("Request 3 : "+req);
       logger.info("\nRequest 3 : ");
      //rspnse= sendRequest("https://payment.cyberplat.ru/cgi-bin/es/es_pay_status.cgi", req);
       storeArr=sendRequest("https://payment.cyberplat.ru/cgi-bin/es/es_pay_status.cgi", req);
       rsArStr=getRespParams(storeArr[1], rsArStr);
      updateResp(prst3,rsArStr[0], rsArStr[2], rsArStr[1], 
               rsArStr[3], rsArStr[4], rsArStr[5],
               rsArStr[6], 0, Integer.parseInt(storeArr[2]),
               rsArStr[7], storeArr[3], storeArr[0], restype.getOpID(),storeArr);
               }
             
             }  // end of while list 
             
            i = prst3.executeBatch();// обновление бд 
             out.println("Batch size of prst3 : "+i.length );
             logger.info("Prst3 Udpate  completed "+i.length);
           
           
           /*  ret =  prst2.executeUpdate();// второй апдейт для возврата состояний в исходное значение
           out.println("Update to resume state 1  : "+ret);
           logger.info("Update again to resume state 1  : "+ret);
           */
             connection.commit();
             
             
                   
                }// if connection !=null
                
    
                
               else {
               out.println("connection refused ");
              connection = ds.getConnection();
               
               }  
      
       
      
            } catch (InterruptedException ex) {
                Logger.getLogger(CyberWS.class.getName()).log(Level.SEVERE, null, ex);
                out.println("goDb catch Interrupted"+ ex.toString());
            } 
             catch (SQLException ex) {
               logger.log(Level.ALL, ex.toString(), ex);
               out.println(ex.toString());
               ins("SQLEX "+intofile);              
                logger.info(ex.toString());
                 }
                catch(NullPointerException ex ){
                out.println("goDb catch NullException "+ ex.toString()+" ");
                ex.printStackTrace();
                }
               
 finally {
                    if(rset!=null){
                    try{
                        rset.close();
                         }
                    catch(Exception e3){
                    out.println(e3.toString());}
                    }
                    if (prst != null) {
                        try {
                            prst.close();
                        } catch (Exception e1) {
                            out.println(e1.toString());
                        }
                      
                   
                  }
                      if (prst1 != null) {
                        try {
                            prst1.close();
                        } catch (Exception e1) {
                            out.println(e1.toString());
                        }
                    }
                    if (prst3 != null) {
                        try {
                            prst3.close();
                        } catch (Exception e1) {
                            out.println(e1.toString());
                        }
                        
                    }
                      if (prst2 != null) {
                        try {
                            prst2.close();
                        } catch (Exception e1) {
                            out.println(e1.toString());
                        }       
                    }
                     
                  close(connection);
                
            }
 
     }
    
    }

}