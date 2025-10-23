package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final String usernameSelector = "input[name='username']";
    private final String passwordSelector = "input[name='password']";
    private final String loginButtonSelector = "button[type='submit']";
    private final String errorMessageSelector = ".oxd-alert-content-text";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToLogin(String url) {
        page.navigate(url);
    }

    public void login(String username, String password) {
        page.fill(usernameSelector, username);
        page.fill(passwordSelector, password);
        page.click(loginButtonSelector);
        // Esperar a que el dashboard cargue tras login
        page.waitForURL("**/dashboard/**", new Page.WaitForURLOptions().setTimeout(15000));
    }

    public boolean isErrorVisible() {
        return page.isVisible(errorMessageSelector);
    }

    public String getErrorMessage() {
        if (isErrorVisible()) {
            return page.textContent(errorMessageSelector);
        }
        return null;
    }
}
