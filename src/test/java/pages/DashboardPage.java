package pages;

import com.microsoft.playwright.Page;

public class DashboardPage {
    private final Page page;
    private final String dashboardHeaderSelector = "h6.oxd-text--h6.oxd-topbar-header-breadcrumb-module";
    private final String pimMenuSelector = "a[href*='pim/viewPimModule']";

    public DashboardPage(Page page) {
        this.page = page;
    }

    public boolean isDashboardLoaded() {
        page.waitForSelector(dashboardHeaderSelector);
        String text = page.textContent(dashboardHeaderSelector);
        return text != null && text.contains("Dashboard");
    }

    public void goToPIM() {
        page.click(pimMenuSelector);
        page.waitForURL("**/pim/viewEmployeeList**");
    }
}
