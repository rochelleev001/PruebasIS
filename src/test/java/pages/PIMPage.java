package pages;

import com.microsoft.playwright.Page;

public class PIMPage {
    private final Page page;
    private final String addButtonSelector = "button:has-text('Add')";
    private final String firstNameSelector = "input[name='firstName']";
    private final String lastNameSelector = "input[name='lastName']";
    private final String saveButtonSelector = "button:has-text('Save')";
    private final String employeeNameInputSelector = "input[placeholder='Type for hints...']";
    private final String searchButtonSelector = "button:has-text('Search')";
    private final String employeeTableSelector = ".oxd-table-body";

    public PIMPage(Page page) {
        this.page = page;
    }

    public void clickAddEmployee() {
        page.click(addButtonSelector);
        page.waitForURL("**/pim/addEmployee**");
    }

    public void createEmployee(String firstName, String lastName) {
        page.fill(firstNameSelector, firstName);
        page.fill(lastNameSelector, lastName);
        page.click(saveButtonSelector);
        // Confirmar guardado navegando a la página de detalles personales
        page.waitForURL("**/pim/viewPersonalDetails/**");
    }

    public void searchEmployee(String name) {
        page.fill(employeeNameInputSelector, name);
        // Esperar el dropdown de autocompletado y seleccionar primera opción
        page.waitForSelector(".oxd-autocomplete-dropdown");
        page.keyboard().press("ArrowDown");
        page.keyboard().press("Enter");
        page.click(searchButtonSelector);
        page.waitForSelector(employeeTableSelector);
    }

    public boolean isEmployeeInResults(String name) {
        return page.isVisible(employeeTableSelector) &&
               page.textContent(employeeTableSelector).contains(name);
    }
}
