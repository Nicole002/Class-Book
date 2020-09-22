/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.klassenbuch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author nicis
 */
public class LoginDAO {

    private final SAXBuilder usersBuilder;
    private final File usersXmlFile;
    private final SAXBuilder entriesBuilder;
    private final File entriesXmlFile;

    public LoginDAO() {
        usersBuilder = new SAXBuilder();
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        path = path + "WEB-INF\\users.xml";
        usersXmlFile = new File(path);

        entriesBuilder = new SAXBuilder();
        String pathe = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        pathe = pathe + "WEB-INF\\bookentries.xml";
        entriesXmlFile = new File(pathe);
    }

    public User check(String username, String password) {
        try {
            //JDOM-Dokument erzeugt
            Document doc = (Document) usersBuilder.build(usersXmlFile);
            //Zugriff auf Root
            Element roodNode = doc.getRootElement();
            //Liste der Kindelemente
            List list = roodNode.getChildren("user");
            for (int i = 0; i < list.size(); i++) {
                //Zugriff auf i-tes Element
                Element node = (Element) list.get(i);
                if (node.getChildText("username").equals(username) && node.getChildText("userPassword").equals(password)) {
                    return new User(username, Integer.parseInt(node.getChildText("userID")));
                }
            }

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List getData() {
        List<Entry> ausgabe;
        ausgabe = new ArrayList();
        try {
            Document doc = (Document) entriesBuilder.build(entriesXmlFile);
            Element roodNode = doc.getRootElement();
            List list = roodNode.getChildren("entry");
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                Entry entry = new Entry();
                entry.setIdEntry(Integer.parseInt(node.getChildText("entryID")));
                entry.setName(node.getChildText("name"));
                entry.setUsername(node.getChildText("username"));
                entry.setDate(node.getChildText("date"));
                entry.setMessage(node.getChildText("message"));

                ausgabe.add(entry);
            }

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ausgabe;
    }

    public boolean setEintrag(String name, String username, String message) {

        try {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(today);

            Document document = (Document) entriesBuilder.build(entriesXmlFile);
            Element rootNode = document.getRootElement();

            Element entry = new Element("entry");

            entry.addContent(new Element("entryID").setText(Integer.toString(this.maxIdinXML() + 1)));
            entry.addContent(new Element("name").setText(name));
            entry.addContent(new Element("username").setText(username));
            entry.addContent(new Element("date").setText(date));
            entry.addContent(new Element("message").setText(message));

            rootNode.addContent(entry);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(entriesXmlFile));

            return true;

        } catch (IOException | JDOMException e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean setUser(String firstname, String lastname, String username, String password) {
        try {
            Document doc = (Document) usersBuilder.build(usersXmlFile);
            Element rootNode = doc.getRootElement();

            Element user = new Element("user");
            user.addContent(new Element("userID").setText(Integer.toString(this.maxIdUser() + 1)));
            user.addContent(new Element("userFirstname").setText(firstname));
            user.addContent(new Element("userLastname").setText(lastname));
            user.addContent(new Element("username").setText(username));
            user.addContent(new Element("userPassword").setText(password));

            rootNode.addContent(user);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(usersXmlFile));

            return true;

        } catch (IOException | JDOMException e) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    private int maxIdinXML() {

        int tmp = Integer.MAX_VALUE;
        try {
            Document document = (Document) entriesBuilder.build(entriesXmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("entry");
            Element node = (Element) list.get(0);
            tmp = Integer.parseInt(node.getChildText("entryID"));
            for (int i = 1; i < list.size(); i++) {
                node = (Element) list.get(i);
                if (tmp < Integer.parseInt(node.getChildText("entryID"))) {
                    tmp = Integer.parseInt(node.getChildText("entryID"));
                }
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }
    
    private int maxIdUser() {

        int tmp = Integer.MAX_VALUE;
        try {
            Document document = (Document) usersBuilder.build(usersXmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("user");
            Element node = (Element) list.get(0);
            tmp = Integer.parseInt(node.getChildText("userID"));
            for (int i = 1; i < list.size(); i++) {
                node = (Element) list.get(i);
                if (tmp < Integer.parseInt(node.getChildText("userID"))) {
                    tmp = Integer.parseInt(node.getChildText("userID"));
                }
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

}
