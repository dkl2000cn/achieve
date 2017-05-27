package com.easygoal.achieve;

import java.io.IOException; 
import java.io.InputStream; 
 
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 
 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.NodeList; 
import org.xml.sax.SAXException;

import android.util.Log;
import android.widget.Toast;

/**
 * 解析Xml文件
 * ParseXmlUtils.java
 * @author Cloay
 * 2011-11-7
 */ 
public class ParseXmlUtils { 
    /**
     * 通过InputStream 解析文件
     * @param in
     * @return
     */ 
	
    public static UpdateInfo parseXml(InputStream in){ 
        UpdateInfo updateInfo = new UpdateInfo(); 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();           
        DocumentBuilder db = null;  
        String Tags="ParseXmlUtils|parseXml|";
        try { 
            db = dbf.newDocumentBuilder(); 
            Document doc = null;     
            doc = db.parse(in);  
            Element root = doc.getDocumentElement(); 
            NodeList resultNode = root.getElementsByTagName("version");
            Log.d(Tags,"nodelist"+resultNode.getLength());
            for(int i = 0; i < resultNode.getLength();i++){     
                Element res = (Element)resultNode.item(i); 
                Log.d(Tags,"node name "+res.getFirstChild().getNodeName());
                Log.d(Tags,"node type "+res.getFirstChild().getNodeType());
                Log.d(Tags,"node value"+res.getFirstChild().getNodeValue());
                Log.d(Tags,"node name "+res.getNodeName());
                Log.d(Tags,"node type "+res.getNodeType());
                Log.d(Tags,"node value"+res.getNodeValue());
                
                //Log.d("node version", ""+res.getElementsByTagName("version").item(0).getFirstChild().getNodeValue());

                updateInfo.setVersion(res.getFirstChild().getNodeValue());
                //updateInfo.setVersionName(res.getFirstChild().getNodeValue());
                // updateInfo.setUrl(res.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
                //updateInfo.setDescription(res.getElementsByTagName("description").item(0).getFirstChild().getNodeValue()); 
            }
            NodeList resultNode1 = root.getElementsByTagName("versionName");
            Log.d(Tags,"nodelist"+resultNode1.getLength());
            for(int i = 0; i < resultNode1.getLength();i++){
                Element res = (Element)resultNode1.item(i);
                Log.d(Tags,"node name "+res.getFirstChild().getNodeName());
                Log.d(Tags,"node type "+res.getFirstChild().getNodeType());
                Log.d(Tags,"node value"+res.getFirstChild().getNodeValue());
                Log.d(Tags,"node name "+res.getNodeName());
                Log.d(Tags,"node type "+res.getNodeType());
                Log.d(Tags,"node value"+res.getNodeValue());

                //Log.d("node version", ""+res.getElementsByTagName("version").item(0).getFirstChild().getNodeValue());

                updateInfo.setVersionName(res.getFirstChild().getNodeValue());
                //updateInfo.setVersionName(res.getFirstChild().getNodeValue());
                // updateInfo.setUrl(res.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
                //updateInfo.setDescription(res.getElementsByTagName("description").item(0).getFirstChild().getNodeValue());
            }
        } catch (ParserConfigurationException e) { 
            e.printStackTrace(); 
        } catch (SAXException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }       
        return updateInfo; 
    } 
} 