package tests;

import base.TestBase;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.DashboardPage;
import pages.PIMPage;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PIMTests extends TestBase {
    private static final String URL = "https://opensource-demo.orangehrmlive.com/";
    private static final String USER = "Admin";
    private static final String PASS = "admin123";
    private static String createdEmployeeName;

    @Test
    @Order(1)
    public void testLoginAndDashboard() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLogin(URL);
        loginPage.login(USER, PASS);
        DashboardPage dashboardPage = new DashboardPage(page);
    assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded after login");
    }

    @Test
    @Order(2)
    public void testCreateEmployee() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLogin(URL);
        loginPage.login(USER, PASS);
        DashboardPage dashboardPage = new DashboardPage(page);
        dashboardPage.goToPIM();
        PIMPage pimPage = new PIMPage(page);
        pimPage.clickAddEmployee();
        String firstName = "Test" + UUID.randomUUID().toString().substring(0, 5);
        String lastName = "User";
        createdEmployeeName = firstName + " " + lastName;
    pimPage.createEmployee(firstName, lastName);
    // Verificar que estamos en la página de detalles personales tras el guardado
    assertTrue(page.url().contains("/pim/viewPersonalDetails/"), "Employee should be created and personal details page should be visible");
    }

    @Test
    @Order(3)
    public void testSearchEmployee() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLogin(URL);
        loginPage.login(USER, PASS);
        DashboardPage dashboardPage = new DashboardPage(page);
        dashboardPage.goToPIM();
        PIMPage pimPage = new PIMPage(page);
        String nameToSearch = createdEmployeeName != null ? createdEmployeeName : "Test";
        pimPage.searchEmployee(nameToSearch);
        assertTrue(pimPage.isEmployeeInResults(nameToSearch), "Employee should be found in search results");
    }
}
