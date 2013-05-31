package com.tiaoin.crawl.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml 转换器
 * 
 * @author sky.yang
 * @version $Id: XStreamUtil.java, v 1.0 2013-1-21 上午11:02:56 sky.yang Exp $
 */
public class XStreamUtil {
    //private Logger logger = LogFactory.getLogger(this.getClass());

    public static void transferXml2Bean(String xmlPath, Object obj) {
        XStream xs = new XStream(new DomDriver());
        xs.autodetectAnnotations(true);
        //xs.setMode(XStream.ID_REFERENCES);
        File fis = new File(xmlPath);
        xs.fromXML(fis, obj);
    }

    public static void transferBean2Xml(String xmlPath, Object obj) {

        try {
            XStream xs = new XStream(new DomDriver());
            xs.autodetectAnnotations(true);
            xs.setMode(XStream.ID_REFERENCES);
            File fis = new File(xmlPath);
            xs.toXML(obj, new FileOutputStream(fis));
        } catch (FileNotFoundException e) {
            //logger.error("", e);
        }
    }
}
