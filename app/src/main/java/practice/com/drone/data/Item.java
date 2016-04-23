package practice.com.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Item implements JSONPopulator {

    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }

    public Condition getCondition() {
        return condition;
    }
}
