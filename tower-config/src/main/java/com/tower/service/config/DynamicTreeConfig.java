package com.tower.service.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;
import com.tower.service.reflection.MetaObject;
import com.tower.service.reflection.factory.DefaultObjectFactory;

/**
 * 树形结构配置
 * <entrys> <br>
 *      <!-- getString("name") 获取到 berniew --> <br>
 *      <entry> <br>
 *          <key>name</key> <br>
 *          <value>berniew</value> <br>
 *      </entry> <br>
 *      <!-- getString("id") 获取到 123 --> <br>
 *      <entry> <br>
 *          <key>Id</key> <br>
 *          <value>123</value> <br>
 *      </entry> <br>
 *      <!-- getString("424324") 获取到 一个map结构 --> <br>
 *      <entry> <br>
 *          <key>424324</key> <br>
 *          <entrys> <br>
 *              <!-- getString("424324.Id1") 获取到 xxxx --> <br>
 *              <entry> <br>
 *                  <key>Id1</key> <br>
 *                  <value>xxxx</value> <br>
 *              </entry> <br>
 *              <!-- getString("424324.Id2") 获取到 ttttt --> <br>
 *              <entry> <br>
 *                  <key>Id2</key> <br>
 *                  <value>ttttt</value> <br>
 *              </entry> <br>
 *              <!-- getString("424324.xxxx") 获取到 一个map结构 --> <br>
 *              <entry> <br>
 *                  <key>xxxx</key> <br>
 *                  <entrys> <br>
 *                      <!-- getString("424324.xxxx.Id2") 获取到 tttttSS --> <br>
 *                      <entry> <br>
 *                          <key>Id2</key> <br>
 *                          <value>tttttSS</value> <br>
 *                      </entry> <br>
 *                  </entrys> <br>
 *              </entry> <br>
 *          </entrys> <br>
 *      </entry> <br>
 *  </entrys> <br>
 */
public class DynamicTreeConfig {
    
    private static XStream xStream = new XStream();
    static {
        xStream.autodetectAnnotations(true);
        xStream.alias("entrys", TreeMap.class);
        xStream.alias("entry", Entry.class);
        xStream.alias("key", String.class);
        xStream.alias("value", String.class);
    }
    
    private String demo =
            "<entrys><entry><key>name</key><value>berniew</value></entry><entry><key>Id</key><value>123</value></entry><entry><key>424324</key><entrys><entry><key>Ids</key><value>123</value></entry></entrys></entry></entrys>";
    private MetaObject entrys = DefaultObjectFactory.getMetaObject(xStream.fromXML(demo));

    public void onUpdate(Map<String,Object> configs) {
        entrys = DefaultObjectFactory.getMetaObject(configs);
    }

    public String getString(String key) {
        return entrys.getValue(key).toString();
    }

    public Map<String,Object> getMap(String key) {
        return (Map<String,Object>) entrys.getValue(key);
    }

    public static void main(String[] args) {

        String xml =
                "<entrys><entry><key>name</key><value>berniew</value></entry><entry><key>Id</key><value>123</value></entry><entry><key>424324</key><entrys><entry><key>Ids</key><value>123</value></entry></entrys></entry></entrys>";
        Map<String,Object> entrys = (Map<String,Object>) xStream.fromXML(xml);
        MetaObject configs_ = DefaultObjectFactory.getMetaObject(entrys);

        System.out.println(configs_.getValue("424324.Ids"));
    }
}
