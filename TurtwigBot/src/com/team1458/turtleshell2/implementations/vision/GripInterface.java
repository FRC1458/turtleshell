package com.team1458.turtleshell2.implementations.vision;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.team1458.turtleshell2.util.HttpRequest;

import java.io.IOException;

/**
 * Interface with GRIP over HTTP
 *
 * @author asinghani
 */
public class GripInterface {
    public static Contour[] getContours(String url) throws IOException {
        String data = HttpRequest.get(url);
        JsonValue json = Json.parse(data);

        JsonObject contours = json.asObject().get("contours").asObject();

        JsonArray area = contours.get("area").asArray();
        JsonArray centerX = contours.get("centerX").asArray();
        JsonArray centerY = contours.get("centerY").asArray();
        JsonArray width = contours.get("width").asArray();
        JsonArray height = contours.get("height").asArray();
        JsonArray solidity = contours.get("solidity").asArray();

        Contour[] contourArray = new Contour[area.size()];

        for(int i = 0; i < area.size(); i++) {
            contourArray[i] = new Contour(area.get(0).asDouble(), centerX.get(0).asDouble(),
                    centerY.get(0).asDouble(), width.get(0).asDouble(),
                    height.get(0).asDouble(), solidity.get(0).asDouble());
        }

        return contourArray;
    }
}
