package com.easygoal.achieve;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：achieve
 * 类描述：
 * 创建人：Acer
 * 创建时间：2017/5/27 23:51
 * 修改人：Acer
 * 修改时间：2017/5/27 23:51
 * 修改备注：
 */
public class XmlUtils {

    public static List<UpdateInfo> Pull2Xml(InputStream is) {
        List<UpdateInfo> list=new ArrayList<UpdateInfo>();
        UpdateInfo updateInfo=new UpdateInfo();
        XmlPullParser parser= Xml.newPullParser();
        LogUtils.d("start list");
        try {
            parser.setInput(is,"utf_8");
            int type = parser.getEventType();
            LogUtils.d("event type"+type);
            while (type!= XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        LogUtils.d("start tag");
                        if ("update".equals(parser.getName())) {
                            LogUtils.d("start tag update");
                            // updateInfo.setVersion(parser.getAttributeName(0).getAttributeValue(0));
                            //LogUtils.d(parser.getName()+parser.nextText());
                        }
                        if ("version".equals(parser.getName())) {
                            LogUtils.d("start tag version");
                            //LogUtils.d(parser.getName()+parser.nextText());
                            String v=parser.nextText();

                            updateInfo.setVersion(v);
                            LogUtils.d(v);
                        }
                        if ("versionName".equals(parser.getName())) {
                            LogUtils.d("start tag versionname");
                            //LogUtils.d(parser.getName()+parser.nextText());
                            String vn=parser.nextText();
                            updateInfo.setVersionName(vn);
                            LogUtils.d(vn);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        list.add(updateInfo);
                        LogUtils.d("end");
                        break;
                    default:break;

                }
                type=parser.next();
            }

        } catch (XmlPullParserException e) {
            LogUtils.d("start tag versionname" );
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    };

}