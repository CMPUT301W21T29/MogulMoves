package com.example.mogulmoves;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SerializerTest {

    @Test
    public void experimentSerializer() {

        ExperimentSerializer serializer = new ExperimentSerializer();
        HashMap<String, Object> map = new HashMap();

        assertThrows(java.lang.NullPointerException.class, () -> serializer.fromData(map));

        map.put("description", "testStr");
        map.put("region", "region");
        map.put("minTrials", (long) 100);
        map.put("locationRequired", true);
        map.put("visible", false);
        map.put("active", false);
        map.put("owner", (long) 3);
        map.put("trials", new ArrayList());
        map.put("messages", new ArrayList());
        map.put("id", (long) 420);
        map.put("type", (long) 0);

        Experiment experiment = serializer.fromData(map);

        assertEquals("testStr", experiment.getDescription());
        assertEquals("region", experiment.getRegion());
        assertEquals(100, experiment.getMinTrials());
        assertEquals(true, experiment.getLocationRequired());
        assertEquals(false, experiment.getVisible());
        assertEquals(false, experiment.getActive());
        assertEquals(3, experiment.getOwner());
        assertEquals(420, experiment.getId());

        assert experiment instanceof BinomialExperiment;

        map.put("type", (long) 1);
        experiment = serializer.fromData(map);
        assert experiment instanceof NonNegativeCountExperiment;

        map.put("type", (long) 2);
        experiment = serializer.fromData(map);
        assert experiment instanceof IntegerCountExperiment;

        map.put("type", (long) 3);
        experiment = serializer.fromData(map);
        assert experiment instanceof MeasureExperiment;

    }

    @Test
    public void messageSerializer() {

        MessageSerializer serializer = new MessageSerializer();
        HashMap<String, Object> map = new HashMap();

        assertThrows(java.lang.NullPointerException.class, () -> serializer.fromData(map));

        map.put("text", "testStr");
        map.put("user", (long) 120);
        map.put("id", (long) 99);

        Message message = serializer.fromData(map);

        assertEquals("testStr", message.getText());
        assertEquals(120, message.getUser());
        assertEquals(99, message.getId());

    }

    @Test
    public void trialSerializer() {

        TrialSerializer serializer = new TrialSerializer();
        HashMap<String, Object> map = new HashMap();

        assertThrows(java.lang.NullPointerException.class, () -> serializer.fromData(map));

        map.put("owner", (long) -40);
        map.put("id", (long) 1398);
        map.put("type", (long) 0);
        map.put("isSuccess", true);
        map.put("timestamp", (long) 5);
        map.put("locationLat", (double) 1);
        map.put("locationLong", (double) 2);

        Trial trial = serializer.fromData(map);

        assertEquals(1398, trial.getId());
        assertEquals(-40, trial.getExperimenter());
        assertEquals(true, ((BinomialTrial) trial).getIsSuccess());

        map.put("type", (long) 1);
        map.put("count", (long) 134);
        trial = serializer.fromData(map);
        assertEquals(134, ((NonNegativeCountTrial) trial).getCount());

        map.put("type", (long) 2);
        trial = serializer.fromData(map);
        assert trial instanceof IntegerCountTrial;

        map.put("type", (long) 3);
        map.put("measurement", (double) 3.14);
        trial = serializer.fromData(map);
        assertEquals(3.14, ((MeasureTrial) trial).getMeasurement(),0.001);

    }

    @Test
    public void userSerializer() {

        UserSerializer serializer = new UserSerializer();
        HashMap<String, Object> map = new HashMap();

        assertThrows(java.lang.NullPointerException.class, () -> serializer.fromData(map));

        map.put("username", "testUsr");
        map.put("email", "test@test");
        map.put("phone", "1231");
        map.put("id", (long) 1999);
        map.put("subscribed", new ArrayList());
        map.put("ignored", new ArrayList());
        map.put("installationId", "q");

        User user = serializer.fromData(map);

        assertEquals("testUsr", user.getUsername());
        assertEquals("test@test", user.getEmail());
        assertEquals("1231", user.getPhone());
        assertEquals(1999, user.getId());
        assertEquals("q", user.getInstallationId());

    }

}
