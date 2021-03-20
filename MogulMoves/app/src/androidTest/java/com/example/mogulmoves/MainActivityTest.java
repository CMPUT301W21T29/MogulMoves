package com.example.mogulmoves;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MainActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
    }

    //Run tests
    @Test
    public void runTests() throws Exception {
        start();
        addExp();
        subscribe();
        addTrials();
        checkStats();
        askQuestion();
        postReply();
        viewPosts();
        editContact();
        viewProfile();
        specifyGeo();
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    //Add an experiment (01.01.01)
    public void addExp(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View view = solo.getView(R.id.add_exp_button);
        solo.clickOnView(view);

        solo.clearEditText((EditText) solo.getView(R.id.experiment_description));
        solo.enterText((EditText) solo.getView(R.id.experiment_description), "Robotium test");
        solo.clearEditText((EditText) solo.getView(R.id.experiment_region));
        solo.enterText((EditText) solo.getView(R.id.experiment_region), "Test location");
        solo.enterText((EditText) solo.getView(R.id.minimum_trials), "5");
        solo.clickOnButton("PUBLISH");

        assertTrue(solo.waitForText("Robotium test", 1, 2000));
        assertTrue(solo.waitForText("Test location", 1, 2000));
        assertTrue(solo.waitForText("5", 1, 2000));
    }

    //Subscribe to an experiment (01.04.01)
    public void subscribe() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Robotium test");

        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.clickOnButton("SUBSCRIBE");
        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Robotium test");

        assertTrue(solo.waitForText("SUBSCRIBED", 1, 2000));
    }

    //Add trials to an experiment (01.05.01)
    public void addTrials() {
        solo.clickOnText("Robotium test");

        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.clickOnButton(1); //add trial button
        solo.clickOnScreen(900, 1250); //submit button

        assertTrue(solo.waitForText("Trials: 1/", 1, 2000));
    }

    //Check statistics of experiment (01.09.01)
    public void checkStats() {
        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.clickOnButton("INCREMENT");
        solo.clickOnButton("BACK");

        assertTrue(solo.waitForText("1", 4, 2000));
        assertTrue(solo.waitForText("0", 1, 2000));
    }

    //Ask question about experiment (02.01.01)
    public void askQuestion() {
        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.scrollToBottom();
        solo.clearEditText((EditText) solo.getView(R.id.new_post_content));
        solo.enterText((EditText) solo.getView(R.id.new_post_content), "Did red vent?");
        solo.clickOnButton("POST");

        assertTrue(solo.waitForText("Did red vent?", 1, 2000));
    }

    //Reply to question about experiment (02.02.01)
    public void postReply() {
        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.scrollToBottom();
        solo.clearEditText((EditText) solo.getView(R.id.new_post_content));
        solo.enterText((EditText) solo.getView(R.id.new_post_content), "Yes. Giga sus");
        solo.clickOnButton("POST");

        assertTrue(solo.waitForText("Yes. Giga sus", 1, 2000));
    }

    //See previous questions and answers of experiment (02.03.01)
    public void viewPosts() {
        solo.assertCurrentActivity("Wrong Activity", ViewExperimentActivity.class);
        solo.scrollToBottom();

        assertTrue(solo.waitForText("Did red vent?", 1, 2000));
        assertTrue(solo.waitForText("Yes. Giga sus", 1, 2000));
    }

    //Edit contact info (04.02.01)
    public void editContact() {
        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View view = solo.getView(R.id.user_button);
        solo.clickOnView(view);

        solo.assertCurrentActivity("Wrong Activity", UserProfilePage.class);
        solo.clickOnText("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", EditProfilePage.class);
        solo.clearEditText((EditText)solo.getView(R.id.edit_name_field));
        solo.enterText((EditText)solo.getView(R.id.edit_name_field), "Test username");
        solo.clearEditText((EditText)solo.getView(R.id.edit_email_field));
        solo.enterText((EditText)solo.getView(R.id.edit_email_field), "test@email.com");
        solo.clickOnButton(0);

        solo.assertCurrentActivity("Wrong Activity", UserProfilePage.class);
        assertTrue(solo.waitForText("Test username", 1, 2000));
        assertTrue(solo.waitForText("test@email.com", 1, 2000));
    }

   //View profile info (04.01.01)
    public void viewProfile() {
        solo.assertCurrentActivity("Wrong Activity", UserProfilePage.class);
        View view = solo.getView(R.id.back_arrow);
        solo.clickOnView(view);

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View view2 = solo.getView(R.id.user_button);
        solo.clickOnView(view2);

        solo.assertCurrentActivity("Wrong Activity", UserProfilePage.class);
        assertTrue(solo.waitForText("Test username", 1, 2000));
        assertTrue(solo.waitForText("test@email.com", 1, 2000));
    }

    //Specify if geolocation is required (06.01.01)
    public void specifyGeo() {
        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        View view = solo.getView(R.id.add_exp_button);
        solo.clickOnView(view);

        assertTrue(solo.waitForText("Trial locations required?", 1, 2000));
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}