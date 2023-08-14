import com.applitools.eyes.*;
import com.applitools.eyes.selenium.*;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestCase {
    static ChromeDriver driver;
    static BatchInfo myBatch;
    static Configuration suiteConfiguration;
    static EyesRunner eyesRunner;
    Eyes eyes;
    @BeforeAll
    public static void beforeAll(){
        myBatch = new BatchInfo("My First Test");
        myBatch.setSequenceName("Advanced visual testing");
        myBatch.setNotifyOnCompletion(true);
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        driver = new ChromeDriver(options);
        suiteConfiguration = new Configuration();
        suiteConfiguration.setApiKey("gs7u1018MIbLDJ4ENFd9d101NdIeKZ0f6rsUaYNcSWQbNIQ110");
        suiteConfiguration.setBatch(myBatch);
//        suiteConfiguration.setMatchLevel(MatchLevel.LAYOUT);
        suiteConfiguration.addBrowser(1000, 600, BrowserType.CHROME);
//        suiteConfiguration.setStitchMode(StitchMode.CSS);
        eyesRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
//        eyesRunner = new ClassicRunner();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
//        System.setProperty("webdriver.chrome.driver", "/Users/sviatoslavpaseka/Downloads/chromedriver-115/chromedriver");
        eyes = new Eyes(eyesRunner);
        eyes.setConfiguration(suiteConfiguration);
        eyes.setBatch(myBatch);
        eyes.setApiKey("gs7u1018MIbLDJ4ENFd9d101NdIeKZ0f6rsUaYNcSWQbNIQ110");
//        driver.manage().window().maximize();
        eyes.open(driver,
                "First test case",
                testInfo.getTestMethod().get().getName(),
                new RectangleSize(1510, 754));
    }
    @Test
    public void myTestCase() {
        driver.get("https://applitools.com/helloworld/?diff1");
        eyes.check(Target.window());
    }


    @Test
    public void applitoolsTutorialPageViewPort() {
        driver.get("https://applitools.com/tutorials/quickstart/web/selenium/java/junit");
        eyes.check(Target.region(By.cssSelector(".navbar")).fully());
//        eyes.check(Target.window().fully(false));
    }

    @Test
    public void matchLevelTest() {
        driver.get("https://applitools.com/helloworld/?diff1");
        eyes.check(Target.window()
                .content(By.cssSelector("div.section:nth-child(1)"))
                .layout(By.cssSelector("div.section:nth-child(2) > p:nth-child(1)")));
    }

    @Test
    public void exampleTestCase() {
        driver.get("https://example.com");
        eyes.check(Target.window());
    }

    @AfterEach
    public void afterEach() {
        eyes.close();
        driver.close();
        driver.quit();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        TestResultsSummary testResultContainers = eyesRunner.getAllTestResults();
        System.out.println(testResultContainers);
    }
}
