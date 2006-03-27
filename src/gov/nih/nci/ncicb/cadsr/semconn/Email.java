package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.common.util.ListProxy;

import java.util.*;
import java.lang.Integer;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.apache.log4j.*;

/**
  * <!-- LICENSE_TEXT_START -->
* Copyright 2001-2004 SAIC. Copyright 2001-2003 SAIC. This software was developed in conjunction with the National Cancer Institute, 
* and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105. 
* Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
* 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions 
* in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other 
* materials provided with the distribution. 
* 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
* "This product includes software developed by the SAIC and the National Cancer Institute."
* If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, 
* wherever such third-party acknowledgments normally appear. 
* 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
* 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize 
* the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
* 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, 
* SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
  * <!-- LICENSE_TEXT_END -->
  */
/**
  * The Email class can be used to sends emails
  * @author caBIO Team 
  * @version 1.0
 */

public class Email {

  private InternetAddress   m_From= null;

  private InternetAddress[] m_To  = null,
                            m_CC  = null,
                            m_BCC = null;


  private String  m_Subject = null;
  private String  m_Body = null;
  private String  m_MimeType= null;
  private String  m_Filename = null;

  public  String PROP_SMTPHOST   = "mail.smtp.host",
                 PROP_SMTPPORT   = "mail.smtp.port",
                 PROP_NoSubject  = "(No Subject)";
  private Properties props;
  private static Logger log= Logger.getLogger(Email.class.getName());
/**
   Default constructor
 */
  public Email() {
    initialize();

    }

/**
    constructor method
   @param From      The email address of the sender
   @param To        The email address(es) of the recipient(s)
   @param CC        The email address(es) of the Carbon Copy recipient(s)
   @param BCC       The email address(es) of the Blind Carbon Copy recipient(s)
   @param Subject   The text to be shown as the "Subject" line of the email
   @param Message   The text to be used as the body of the email
   @throws AddressException
 */
  public Email( String From, String To, String CC, String BCC,
                String Subject, String Message, String Attachment) throws AddressException {
    initialize();

    setFrom(From);
    setTo(To);
    setCC(CC);
    setBCC(BCC);
    setSubject(Subject);
    setMessage(Message);
    setAttach(Attachment);
    }

/**
   This method initialises the various private data elements,
   ready for setting later.
 */
  private void initialize() {
  	props = System.getProperties();
    m_From= null;
    m_To  = null;
    m_CC  = null;
    m_BCC = null;
    m_Subject = null;
    m_Body    = null;
    m_Filename = null;


    }

/**
    This method sets the "From" address for the email from the supplied String.

   @param From      The email address of the sender of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setFrom(String From) throws AddressException {
    InternetAddress[] ia = null;

    if (From == null) {
      throw new AddressException();
      }
    else {
      ia = InternetAddress.parse(From);

      if (ia.length > 0)
        m_From = ia[0];
      else
        throw new AddressException();
      }
    }

/**
    This method sets the "From" address for the email from the supplied String.

   @param From      The email address of the sender of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setFrom(InternetAddress From) throws AddressException {
    if (From == null) {
      throw new AddressException();
      }
    else
      m_From = From;
    }

/**
    This method sets the "To" field of the email.

   @param To    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setTo(String To) throws AddressException {
    if (To == null) {
      m_To = null;
      }
    else {
      m_To = InternetAddress.parse(To);
      }
    }

/**
    This method sets the "To" field of the email.

   @param To    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */

  public void setTo(InternetAddress[] To) throws AddressException {
    m_To = To;
    }

/**
    This method sets the "CC" field of the email.

   @param CC    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setCC(String CC) throws AddressException {
    if (CC == null) {
      m_CC = null;
      }
    else
      m_CC = InternetAddress.parse(CC);
    }

/**
    This method sets the "CC" field of the email.

   @param CC    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setCC(InternetAddress[] CC) throws AddressException {
    m_CC = CC;
    }

/**
    This method sets the "BCC" field of the email.

   @param BCC    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setBCC(String BCC) throws AddressException {
    if (BCC == null) {
      m_BCC = null;
      }
    else
      m_BCC = InternetAddress.parse(BCC);
    }

/**
    This method sets the "CC" field of the email.

   @param CC    The email address of the recipient of the email.(Should be in sender@company.com format)
   @throws AddressException
 */
  public void setBCC(InternetAddress[] BCC) throws AddressException {
    m_BCC = BCC;
    }

/**
    This method sets the "Subject"  for the email

   @param Subject   Subject line of email
 */
  public void setSubject(String Subject) {
    m_Subject = (Subject == null) ? PROP_NoSubject : Subject;
    }

/**
    This message sets the content of the message part of the email.

   @param Message   The text appears in the body of the email message.
 */
  public void setMessage(String Message) {
    m_Body = Message;
    }


  public void setAttach(String Attachment) {
  	m_Filename = Attachment;
  }
/**
    This method does the actual sending of the email. It allows
    the email to be modified after it's creation and then sent later.

   @return boolean  Indicates success or failure of sending the email to the
                    specified SMTP host
 */
  public boolean send() throws Exception{
    boolean  mailSent  = false;

    Session      sess  = Session.getDefaultInstance(props, null);
    MimeMessage  msg   = null;
    Multipart    mp    = null;

    try
    {

      if (props.containsKey(PROP_SMTPHOST) &&
          !props.getProperty(PROP_SMTPHOST).equals(""))
      {


          msg = new MimeMessage(sess);
          msg.setFrom(m_From);


          msg.setSentDate(new Date());


          if ((m_To == null) &&
              (m_CC == null) &&
              (m_BCC== null))
            throw new MessagingException("Trying to send an email with no recipients defined");


          if (m_To != null)
            msg.addRecipients(Message.RecipientType.TO, m_To);


          if (m_CC != null)
            msg.addRecipients(Message.RecipientType.CC, m_CC);


          if (m_BCC != null)
            msg.addRecipients(Message.RecipientType.BCC, m_BCC);


          if (m_Subject == null)
            msg.setSubject(PROP_NoSubject);
          else
            msg.setSubject(m_Subject);

          MimeMultipart mp1 = new MimeMultipart();

          if(m_Body != null)
          {
	         BodyPart bpMsg = new MimeBodyPart();
	         bpMsg.setText(m_Body);
	     	 mp1.addBodyPart(bpMsg);

          }


          if(m_Filename != null)
          {
	         BodyPart bpAtt = new MimeBodyPart();
	     	 FileDataSource fds = new FileDataSource(m_Filename);
		     bpAtt.setDataHandler(new DataHandler(fds));
	   	     bpAtt.setFileName(fds.getName());
	     	 mp1.addBodyPart(bpAtt);
          }
          msg.setContent(mp1);

         
          Transport.send(msg);

           mailSent = true;
          }
        }
        catch (MessagingException mex)
        {
          mailSent = false;
          log.error("MessagingException: " + mex.getMessage());
          throw new Exception(mex.getMessage());
        }

          return mailSent;

    }

/**
    This method sets the value of the SMTP mail hosts address.

   @param sIP       The internet address of the SMTP mail host to use as the
                    Transport for the email.
 */
  public void setMailHost(String sIP) {
   // Properties props = System.getProperties();

      props.put(PROP_SMTPHOST, sIP);
    }

/**
    This method allows the value of the SMTP Mail Host's port to be changed.

   @param port      The port number for the SMTP host.
 */
  public void setMailHostPort(String port) {
 
    Integer newPort = new Integer(Integer.parseInt(port));

    props.put(PROP_SMTPPORT, newPort.toString());
    }

  }
