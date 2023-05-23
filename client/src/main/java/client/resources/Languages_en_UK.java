package client.resources;

import java.util.ListResourceBundle;

public class Languages_en_UK extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"auth.authorizationHeaderLabel", "Authorization"},
                {"lang.denmarkLanguageMenuItem", "Danish"},
                {"lang.englishLanguageMenuItem", "English"},
                {"lang.languageMenu", "Language"},
                {"auth.loginTextField", "Enter your username..."},
                {"auth.passwordPasswordField", "Enter the password..."},
                {"auth.portBindingButton", "Connect"},
                {"lang.russianLanguageMenuItem", "Russian"},
                {"lang.turkishLanguageMenuItem", "Turkish"},
                {"auth.authButton", "Login"},
                {"main.menu.addNewTicket", "Add new ticket"},
                {"main.menu.clearCollection", "Clear Collection"},
                {"main.menu.countTicketsThat", "Count tickets with a discount less than..."},
                {"main.menu.printDiscount", "Print all discount values"},
                {"main.menu.uniquePrice", "Unique price values"},
                {"main.menu.infoAboutCollection", "Collection Information"},
                {"main.commandMenu", "Commands"},
                {"main.helpMenu", "Help"},
                {"main.help.help", "Print command list"},
                {"main.filterByColumnLabel", "Filter by:"},
                {"main.refreshTableButton", "Refresh"},
                {"main.currentUserLabel", "Current user:"},
                {"edit.header", "Ticket editing"},
                {"edit.ticketName", "Ticket name:"},
                {"edit.ticketX", "The X coordinate"},
                {"edit.ticketY", "The Y coordinate"},
                {"edit.ticketPrice", "Price"},
                {"edit.ticketDiscount", "Discount"},
                {"edit.ticketType", "ticket type"},
                {"edit.eventName", "Event name"},
                {"edit.eventDate", "Event date"},
                {"edit.eventMinAge", "Minimum age"},
                {"edit.eventDescription", "Description of the event"},
                {"edit.eventType", "Event type"},
                {"edit.saveTicketIfMinButton", "Save if ticket is minimal"},
                {"edit.saveTicketButton", "Save ticket"},
                {"edit.alerts.blankFieldError", "It looks like you didn't complete all the form fields. Try again."},
                {"edit.ticketComment", "A comment"},
                {"auth.alerts.authError", "Authorisation Error"},
                {"main.alerts.error", "An error has occurred "},
                {"main.alerts.success", "Successfully!"},
                {"main.collectionType", "Collection type"},
                {"main.initializationDate", "Initialization date: "},
                {"main.itemsQuantity", "Amount of elements:"},
                {"main.alerts.discountQuantity", "Enter discount value: "},
                {"main.alerts.notNumber", "The value is not a number!"},
                {"main.alerts.discountError", "The value does not fit within the discount range"},
                {"main.ticketEditing", "Ticket change"},
                {"main.alerts.fieldsError", "Some form fields are filled incorrectly."},
                {"main.mainWindow", "Main window"},
                {"auth.alerts.serverConnectionError", "An error occurred connecting to the server "},
                {"auth.alerts.serverConnectionSuccess", "The connection to the server was successful!"},
                {"auth.serverConnection", "Server connection"},
                {"drawing.windowTitle", "Visualization of the current state"},
                {"main.menu.changeUserColors", "Update table colors"},
                {"edit.deleteTicketButton", "Delete"}
        };
    }
}
