package com.uits.baseproject.service.core;

import android.text.TextUtils;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Helps to convert another type to Double.
 *
 * @author QuyDp
 */
public class DoubleAdapter extends TypeAdapter<Double> {
    @Override
    public void write(JsonWriter out, Double value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public Double read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NULL:
                in.nextNull();
                return null;

            case NUMBER:
                return in.nextDouble();

            case BOOLEAN:
                return in.nextBoolean() ? 1D : 0;

            case STRING:
                try {
                    String value = in.nextString();
                    return Double.parseDouble(TextUtils.isEmpty(value) ? "0" : value);
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }
}
