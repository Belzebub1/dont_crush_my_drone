package practice.com.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Channel implements JSONPopulator {

    private Item item;
    private Units units;
    private Wind wind;
    private Location location;
    private Atmosphere atmosphere;

    @Override
    public void populate(JSONObject data) {
        units =new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));

        location = new Location();
        location.populate(data.optJSONObject("location"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));
    }

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    public Wind getWind() {
        return wind;
    }

    public Location getLocation() {
        return location;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }
}
