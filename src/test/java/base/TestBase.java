package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class TestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected static boolean isHeadless;

    @BeforeAll
    public static void setUpAll() {
        isHeadless = Boolean.parseBoolean(System.getProperty("PLAYWRIGHT_HEADLESS", "true"));
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
    }

    @AfterAll
    public static void tearDownAll() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    public void setUp() {
        context = browser.newContext();
        page = context.newPage();
        // Timeout por defecto para esperas de elementos/navegación
        page.setDefaultTimeout(60000);
        page.setDefaultNavigationTimeout(60000);
    }

    @AfterEach
    public void tearDown() {
        if (context != null) context.close();
    }
}
