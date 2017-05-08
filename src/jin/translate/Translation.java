package jin.translate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jin on 2017/5/8 0008.
 */
public class Translation {


    /**
     * translation : ["你好"]
     * basic : {"us-phonetic":"həˈlo","phonetic":"həˈləʊ","uk-phonetic":"həˈləʊ","explains":["n. 表示问候， 惊奇或唤起注意时的用语","int. 喂；哈罗","n. (Hello)人名；(法)埃洛"]}
     * query : hello
     * errorCode : 0
     * web : [{"value":["你好","您好","hello"],"key":"Hello"},{"value":["凯蒂猫","昵称","匿称"],"key":"Hello Kitty"},{"value":["哈乐哈乐","乐扣乐扣"],"key":"Hello Bebe"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        /**
         * us-phonetic : həˈlo
         * phonetic : həˈləʊ
         * uk-phonetic : həˈləʊ
         * explains : ["n. 表示问候， 惊奇或唤起注意时的用语","int. 喂；哈罗","n. (Hello)人名；(法)埃洛"]
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        private List<String> explains;

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["你好","您好","hello"]
         * key : Hello
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
