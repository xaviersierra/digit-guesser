package xsierra.digitguesser.trainer;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, Long> hParamaters = new HashMap<>();

    public void putHyperParameter(HyperParameter parameter, Long value){
        hParamaters.put(parameter.name().toLowerCase(), value);
    }

    public Long getHyperParameter(String parameter){
        return hParamaters.get(parameter);
    }

    public Long getHyperParameter(HyperParameter parameter){
        return hParamaters.get(parameter.name().toLowerCase());
    }
}
