package com.example.mogulmoves;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HistogramFragmentTest {

    private IntegerCountExperiment mockIntExperiment(User user) {
        int testCountValue = 5;
        IntegerCountTrial mockIntTrial = new IntegerCountTrial(user.getId(), testCountValue);

        int testExperimentID = 3;
        int testMinTrials = 1;
        IntegerCountExperiment mockIntExperiment = new IntegerCountExperiment(testExperimentID, "Test description", "Test region",
                testMinTrials, false, true);
        mockIntExperiment.addTrial(mockIntTrial.getId());
        return mockIntExperiment;
    }

    private User mockUser() {
        User user = new User("Test ID", "Test name", "test@email", "780-587-1234");
        return user;
    }






    @Test
    public void histogramTest() {

        User user = mockUser();
        IntegerCountExperiment integerCountExperiment = mockIntExperiment(user);

        HistogramFragment mockHistogramFragment = new HistogramFragment(integerCountExperiment);

        assertEquals(0, mockHistogramFragment.getExperimentType());

    }
}
