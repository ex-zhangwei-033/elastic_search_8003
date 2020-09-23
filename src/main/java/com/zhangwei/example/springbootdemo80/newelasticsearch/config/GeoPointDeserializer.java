package com.zhangwei.example.springbootdemo80.newelasticsearch.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.elasticsearch.common.geo.GeoPoint;

import java.io.IOException;

public class GeoPointDeserializer extends StdDeserializer<GeoPoint> {
    private static final long serialVersionUID = -5510642578760781580L;

    GeoPointDeserializer() {
        this(null);
    }

    GeoPointDeserializer(Class<GeoPoint> t) {
        super(t);
    }

    @Override
    public GeoPoint deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        JsonNode latNode = node.get("lat");
        double lat = latNode.asDouble();

        JsonNode lonNode = node.get("lon");
        double lon = lonNode.asDouble();
        return new GeoPoint(lat, lon);
    }
}