package okhttp;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by dzc on 15/12/10.
 */
public abstract class ObjectCallback<T> implements BaseCallback {
    public abstract void onFailure(Call call, Exception e);
    public abstract void onResponse(Call call,T object) throws IOException;

    @Override
    public void onStart() {
    }

    @Override
    public void onFinish() {
    }

    Type getType(){
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if(type instanceof Class){
            return type;
        }else{
            return new TypeToken<T>(){}.getType();
        }
    }
}
