package com.zhangwei.example.springbootdemo80.newelasticsearch.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.elasticsearch.common.geo.GeoPoint;

import java.io.IOException;

public class GeoPointSerializer extends StdSerializer<GeoPoint> {
    private static final long serialVersionUID = -1262685290185555664L;

    GeoPointSerializer() {
        this(null);
    }

    GeoPointSerializer(Class<GeoPoint> t) {
        super(t);
    }

    @Override
    public void serialize(GeoPoint value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("lat", value.getLat());
        gen.writeNumberField("lon", value.getLon());
        gen.writeEndObject();
    }
}



