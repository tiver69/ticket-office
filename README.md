# Ticket Office

## Online system for booking train tickets

	System supports searching for trains in requested directions, choosing suitable coach
	and free place in requested train. Displaying all active and old-booked tickets (for all authorized users).
	System supports managing registered users information: updating, removing, promoting (for administrators only).

### Installation

    Specify values for "login"(username), "pass"(password) and port number in url in '../src/main/resources/dbconnection.properties',
    Execute script '../src/main/resources/railway_ticket_office.sql' to create db,
    (Optional) Set up auto launch properties for 'app-start.bat' (no changes required for chrome browser).

### Running

    Navigate to root project folder and execute command > mvn clean verify,
    Application will be available by URL http://localhost:8888/ticket-office/
    	Try login "tiver69" & password "pass17" to login as administrator,
    	Try login "protasov1" & password "pass11" to login as user,
    	Or registrate your own user,
    Stop server running 'shutdown' command.
