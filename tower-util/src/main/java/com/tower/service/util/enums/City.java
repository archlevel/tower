package com.tower.service.util.enums;

import java.util.LinkedList;
import java.util.List;

/**
 * 城市.
 * <p>
 * <ul>
 * <li>11. 上海</li>
 * <li>12. 广州</li>
 * <li>13. 深圳</li>
 * <li>14. 北京</li>
 * <li>15. 成都</li>
 * <li>16. 南京</li>
 * <li>17. 天津</li>
 * <li>18. 杭州</li>
 * <li>19. 苏州</li>
 * <li>20. 重庆</li>
 * <li>21. 大连</li>
 * <li>22. 武汉</li>
 * <li>23. 济南</li>
 * <li>24. 佛山</li>
 * <li>25. 无锡</li>
 * <li>26. 郑州</li>
 * <li>27. 长沙</li>
 * <li>28. 石家庄</li>
 * <li>29. 香港</li>
 * <li>30. 青岛</li>
 * <li>31. 西安</li>
 * <li>32. 宁波</li>
 * <li>33. 合肥</li>
 * <li>34. 东莞</li>
 * <li>35. 福州</li>
 * <li>36. 昆明</li>
 * <li>37. 贵阳</li>
 * <li>38. 太原</li>
 * <li>39. 沈阳</li>
 * <li>40. 昆山</li>
 * <li>41. 南昌</li>
 * <li>42. 珠海</li>
 * <li>43. 常州</li>
 * <li>44. 中山</li>
 * <li>45. 嘉兴</li>
 * <li>46. 厦门</li>
 * <li>47. 烟台</li>
 * <li>48. 哈尔滨</li>
 * <li>49. 海口</li>
 * <li>50. 长春</li>
 * <li>51. 三亚</li>
 * <li>52. 惠州</li>
 * <li>53. 保定</li>
 * <li>54. 桂林</li>
 * <li>55. 邯郸</li>
 * <li>56. 呼和浩特</li>
 * <li>57. 吉林</li>
 * <li>58. 兰州</li>
 * <li>59. 廊坊</li>
 * <li>60. 洛阳</li>
 * <li>61. 绵阳</li>
 * <li>62. 南宁</li>
 * <li>63. 南通</li>
 * <li>64. 秦皇岛</li>
 * <li>65. 泉州</li>
 * <li>66. 绍兴</li>
 * <li>67. 泰州</li>
 * <li>68. 唐山</li>
 * <li>69. 威海</li>
 * <li>70. 潍坊</li>
 * <li>71. 徐州</li>
 * <li>72. 扬州</li>
 * <li>73. 宜昌</li>
 * <li>74. 银川</li>
 * <li>75. 镇江</li>
 * <li>76. 淄博</li>
 * <li>77. 柳州</li>
 * <li>78. 江门</li>
 * 
 * </ul>
 * </p>
 */

public enum City {

    Shanghai(Ids.SHANGHAI, "上海"),
    Guangzhou(Ids.GUANGZHOU, "广州"),
    Shenzhen(Ids.SHENZHEN, "深圳"),
    Beijing(Ids.BEIJING, "北京"),
    Chengdu(Ids.CHENGDU, "成都"),
    Nanjing(Ids.NANJING, "南京"),
    Tianjin(Ids.TIANJIN, "天津"),
    Hangzhou(Ids.HANGZHOU, "杭州"),
    Suzhou(Ids.SUZHOU, "苏州"),
    Chongqing(Ids.CHONGQING, "重庆"),
    Dalian(Ids.DALIAN, "大连"),
    Wuhan(Ids.WUHAN, "武汉"),
    Jinan(Ids.JINAN, "济南"),
    Foshan(Ids.FOSHAN, "佛山"),
    Wuxi(Ids.WUXI, "无锡"),
    Zhengzhou(Ids.ZHENGZHOU, "郑州"),
    Changsha(Ids.CHANGSHA, "长沙"),
    SJZ(Ids.SJZ, "石家庄"),
    HongKong(Ids.HONGKONG, "香港"),
    Qingdao(Ids.QINGDAO, "青岛"),
    Xian(Ids.XIAN, "西安"),
    Ningbo(Ids.NINGBO, "宁波"),
    Hefei(Ids.HEFEI, "合肥"),
    Dongguan(Ids.DONGGUAN, "东莞"),
    Fuzhou(Ids.FUZHOU, "福州"),
    Kunming(Ids.KUNMING, "昆明"),
    Guiyang(Ids.GUIYANG, "贵阳"),
    Taiyuan(Ids.TAIYUAN, "太原"),
    Shenyang(Ids.SHENYANG, "沈阳"),
    KUNSHAN(Ids.KUNSHAN, "昆山"),
    NANCHANG(Ids.NANCHANG, "南昌"),
    ZHUHAI(Ids.ZHUHAI, "珠海"),
    CHANGZHOU(Ids.CHANGZHOU, "常州"),
    ZHONGSHAN(Ids.ZHONGSHAN, "中山"),
    JIAXING(Ids.JIAXING, "嘉兴"),
    XIAMEN(Ids.XIAMEN, "厦门"),
    YANTAI(Ids.YANTAI, "烟台"),
    HAERBIN(Ids.HAERBIN, "哈尔滨"),
    HAIKOU(Ids.HAIKOU, "海口"),
    CHANGCHUN(Ids.CHANGCHUN, "长春"),
    sanya(Ids.SANYA, "三亚"),
    huizhou(Ids.HUIZHOU, "惠州"),
    baoding(Ids.BAODING, "保定"),
    guilin(Ids.GUILIN, "桂林"),
    handan(Ids.HANDAN, "邯郸"),
    huhehaote(Ids.HUHEHAOTE, "呼和浩特"),
    jilin(Ids.JILIN, "吉林"),
    lanzhou(Ids.LANZHOU, "兰州"),
    langfang(Ids.LANGFANG, "廊坊"),
    luoyang(Ids.LUOYANG, "洛阳"),
    mianyang(Ids.MIANYANG, "绵阳"),
    nanning(Ids.NANNING, "南宁"),
    nantong(Ids.NANTONG, "南通"),
    qinhuangdao(Ids.QINHUANGDAO, "秦皇岛"),
    quanzhou(Ids.QUANZHOU, "泉州"),
    shaoxing(Ids.SHAOXING, "绍兴"),
    taizhou(Ids.TAIZHOU, "泰州"),
    tangshan(Ids.TANGSHAN, "唐山"),
    weihai(Ids.WEIHAI, "威海"),
    weifang(Ids.WEIFANG, "潍坊"),
    xuzhou(Ids.XUZHOU, "徐州"),
    yangzhou(Ids.YANGZHOU, "扬州"),
    yichang(Ids.YICHANG, "宜昌"),
    yinchuan(Ids.YINCHUAN, "银川"),
    zhenjiang(Ids.ZHENJIANG, "镇江"),
    zibo(Ids.ZIBO, "淄博"),
    liuzhou(Ids.LIUZHOU, "柳州"),
    jiangmen(Ids.JIANGMEN, "江门"),

    Other(Ids.OTHER, "其它", false);

    private static final City[] ENABLED_CITIES;

    private Integer id;

    private String name;

    private String pinyin;

    private boolean enabled;

    private City(int id, String title) {
        this(id, title, true);
    }

    private City(int id, String name, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
        this.name = name;
        this.pinyin = name().toLowerCase();
    }

    /**
     * 返回城市的ID号.
     * 
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 返回城市中文名称.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 返回城市拼音.
     * 
     * @return
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 返回城市是否开通.
     * 
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    //

    /**
     * 通过ID号返回城市的枚举类型.
     * 
     * @param id
     * @return 城市
     */
    public static City valueOf(Integer id) {

        City[] citys = getEnabledCities();
        int size = citys == null ? 0 : citys.length;
        for (int i = 0; i < size; i++) {
            if (citys[i].id == id) {
                return citys[i];
            }
        }
        return Other;
    }

    /**
     * 如果城市名字name包含，则返回被包含的城市
     * 
     * @param name
     * @return
     */
    public static City getCity(String name) {
        City[] citys = getEnabledCities();
        int size = citys == null ? 0 : citys.length;
        for (int i = 0; i < size; i++) {
            if (name != null && name.indexOf(citys[i].name) != -1) {
                return citys[i];
            }
        }
        return Other;
    }

    /**
     * 返回开通的城市列表.
     * 
     * @return
     */
    public static City[] getEnabledCities() {
        return ENABLED_CITIES;
    }

    static {
        List<City> list = new LinkedList<City>();
        City[] citys = City.values();
        for (City city : citys) {
            if (city.isEnabled()) {
                list.add(city);
            }
        }
        ENABLED_CITIES = list.toArray(new City[] {});
    }

    public static class Ids {
        public static final int SHANGHAI = 11;
        public static final int GUANGZHOU = 12;
        public static final int SHENZHEN = 13;
        public static final int BEIJING = 14;
        public static final int CHENGDU = 15;
        public static final int NANJING = 16;
        public static final int TIANJIN = 17;
        public static final int HANGZHOU = 18;
        public static final int SUZHOU = 19;
        public static final int CHONGQING = 20;
        public static final int DALIAN = 21;
        public static final int WUHAN = 22;
        public static final int JINAN = 23;
        public static final int FOSHAN = 24;
        public static final int WUXI = 25;
        public static final int ZHENGZHOU = 26;
        public static final int CHANGSHA = 27;
        public static final int SJZ = 28;
        public static final int HONGKONG = 29;
        public static final int QINGDAO = 30;
        public static final int XIAN = 31;
        public static final int NINGBO = 32;
        public static final int HEFEI = 33;
        public static final int DONGGUAN = 34;
        public static final int FUZHOU = 35;
        public static final int KUNMING = 36;
        public static final int GUIYANG = 37;
        public static final int TAIYUAN = 38;
        public static final int SHENYANG = 39;
        public static final int KUNSHAN = 40;
        public static final int NANCHANG = 41;
        public static final int ZHUHAI = 42;
        public static final int CHANGZHOU = 43;
        public static final int ZHONGSHAN = 44;
        public static final int JIAXING = 45;
        public static final int XIAMEN = 46;
        public static final int YANTAI = 47;
        public static final int HAERBIN = 48;
        public static final int HAIKOU = 49;
        public static final int CHANGCHUN = 50;
        public static final int SANYA = 51;
        public static final int HUIZHOU = 52;
        public static final int BAODING = 53;
        public static final int GUILIN = 54;
        public static final int HANDAN = 55;
        public static final int HUHEHAOTE = 56;
        public static final int JILIN = 57;
        public static final int LANZHOU = 58;
        public static final int LANGFANG = 59;
        public static final int LUOYANG = 60;
        public static final int MIANYANG = 61;
        public static final int NANNING = 62;
        public static final int NANTONG = 63;
        public static final int QINHUANGDAO = 64;
        public static final int QUANZHOU = 65;
        public static final int SHAOXING = 66;
        public static final int TAIZHOU = 67;
        public static final int TANGSHAN = 68;
        public static final int WEIHAI = 69;
        public static final int WEIFANG = 70;
        public static final int XUZHOU = 71;
        public static final int YANGZHOU = 72;
        public static final int YICHANG = 73;
        public static final int YINCHUAN = 74;
        public static final int ZHENJIANG = 75;
        public static final int ZIBO = 76;
        public static final int LIUZHOU = 77;
        public static final int JIANGMEN = 78;

        public static final int OTHER = 0;
    }
}
