Copyright (c) 2023, The Code Barista LLC and its affiliates.

The Global Consulting Scheduler is a GUI-based application to schedule appointments and store customer information.

Author: Kerry J. Hamilton, The Code Barista LLC.
Student ID: 000888046
Contact Information: khami67@wgu.edu
Application Version: Version 1.0, Feb. 25, 2023

Application Specifications:
	IDE- IntelliJ IDEA 2021.3.1 (Community Edition)
	Java SE JDK 17.0.1
	JavaFX-SDK-17.0.1

Database Specifications:
	MySQL Connector driver version- mysql-connector-java-8.0.25

Application Directions:

	Enter the application by providing valid user credentials at the login screen.
	User greeting alert informs users whether there is an upcoming meeting within the next 15 minutes.

	All users can navigate between three tabs (Appointments, Customers, Reports). 'Admin' designated users will have two additional tabs (Users and Contacts)-
		Appointments- tab includes three radio buttons:
			All- list all appointments in a table view; this is the default view.
			Monthly- List all current month appointments in a table view.
			Weekly- List all current week appointments in a table view.

		Customers- List all customers in a table view.

		    Create new appointments and customers using the 'New' button on the Button menu-bar.

		    Update and delete appointment and customer data by selecting the row item from the corresponding table view, then utilizing the 'Update,' or 'Delete' buttons on the Button menu-bar, respectively.

		Reports- Contains a dropdown menu to select between three query panes:
			Total Customer Appointments by Month/Type- Users can choose a combination of months and appointment types from the respective dropdowns; if the user makes no selections, the report returns counts for all months and types. Clicking the 'Search' button returns the results; clicking the 'Clear' button clears any current dropdown selections.

			Contact Schedules- Users select a contact from the dropdown. The default report queries the current week. Alternatively, choosing the 'Month' radio button shows the contact's current month's appointments.

			Total Appointment Duration Average- Menu selection of this report immediately returns the query results.

            Aged Logins- Shows User logins greater than 90 days.

        Users- List all 'active' users in a table view.

        Contacts- List all 'active' contacts in a table view.

            Create new users and contacts using the 'New' button on the Button menu-bar.

            Update user and contact data by selecting the row item from the corresponding table view, then utilizing the 'Update' button in the Button menu-bar.

		    Deactivate a user or contact by selecting the row item from the corresponding table view and then utilizing the 'Deactivate' button on the Button menu-bar.
		    User confirmation is need to complete.
		    Users must enter the 'Update' forms to reactivate a user or contact.

            Users and Contacts tabs include a Search Filter. Selecting the 'Show Search Filter' button on the Button menu-bar reveals the fields available for filtering:
                Users Search Filter Pane-

                    User Name- Filter by username, full or partial.

                    Admin?- Select 'Yes' or 'No.' Leaving empty will show both users of both access levels;

                    Active?- Leave empty for all users; select 'Active' or Inactive' for specific active status.

                Contacts Search Filter Pane-

                    Contact Name- Filter by contact name, full or partial.

                    Email-  Wildcard search on all characters entered.

                    Active?- Leave empty for all users; select 'Active' or Inactive' for specific active status.

            Send the filtered search by pressing the 'Search' button within the Search Filter pane.

            Clear the search fields using the 'Clear Search' button within the Search Filter pane.
            *** Leaving all fields blank and performing the search will return all users or contacts.

            Close the Search Filter pane by selecting the 'Hide Search Filter' button on the Button menu-bar.

   Inactive users will not be allowed to log in. An alert will prompt them to contact the system administrator.

   Non-admin users may update their passwords by clicking on their username in the top-right of the main application page.

   Legacy users- admin and test, cannot be modified. User will receive an alert when trying to update.

   Deleting a customer with appointments will notify the user of the deletion of all existing appointments.
   A bulk deletion of appointments occurs on user confirmation of the message.

   The logout link will return the user to the Login screen.

   The cancel button on the Login screen will close the application.

About Additional Report:
	The 'Total Appointment Duration Average' report averages the length of all scheduled appointments and reports it as a Long.
	The business could use this report to leverage staffing needs, appointment management, and business hours adjustments.