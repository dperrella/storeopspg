package pkg.counterAS;

public class PivotData {

    private final String data;
    private final String store;
    private final int value;
    

    public PivotData(String data, String store, int value) {
        this.data = data;
        this.store = store;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public String getStore() {
        return store;
    }
    
    public int getValue() {
        return value;
    }
    
    public int compareTo(PivotData pd) { 
        if (pd.getData() == null) { 
          return 0; 
        } 
        return getData().compareTo(pd.getData());
      } 
    
}