package sample.Bean.HXYYBean;

import java.util.ArrayList;

public class SearchRetBean {
    public boolean result;
    public SearchRetBeanData data;

    public static class SearchRetBeanData {
        public ArrayList<SearchRetBeanDataSectItems> sectItems;
        public ArrayList<SearchRetBeanDataDocItems> docItems;
        public ArrayList<SearchRetBeanDataSectItems> deptItems;
        public ArrayList<SearchRetBeanDataSectItems> subItems;
    }
}
