package com.easygoal.achieve;

import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;   
    

/**  
 * 定义xml解析时的监听类  
 *   
 * 实现方式有很多，可以实现接口：ContentHandler，DTDHandler， EntityResolver 和 ErrorHandler   
 * 但我们常用的继承：DefaultHandler   
 */   
public class SaxUtil extends DefaultHandler {   
    
    private UpdateInfo updateinfo;   
    private String content;
   
    @Override   
    public void characters(char[] ch, int start, int length)   
            throws SAXException {   
        content = new String(ch, start, length);   
    }   
        
    //当解析到文本开始时触发   
    @Override   
    public void startDocument() throws SAXException {   
        super.startDocument();   
    }   
        
    //当解析到文本结束时触发   
    @Override   
    public void endDocument() throws SAXException {   
        super.endDocument();   
    }   
        
    //当解析到元素开始时触发   
    @Override   
    public void startElement(String uri, String localName, String name,   
            Attributes attributes) throws SAXException    
    {   
        if("version".equals(name))   
        {   
        	updateinfo = new UpdateInfo();   
        }   
        
    }   
        
    //当解析到元素结束时触发   
    @Override   
    public void endElement(String uri, String localName, String name)   
            throws SAXException    
    {   
        if("version".equals(name))   
        {   
        	updateinfo.setVersion(content);   
        }   
    
            
    }   
    
    public UpdateInfo getUpdateInfo(){   
        return updateinfo;   
    }   
        
}