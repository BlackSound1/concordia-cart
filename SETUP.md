# Setup

This document details how to set up this project for local development.

## Software And Tools Required
- Git [https://www.youtube.com/watch?v=gv7VPQ4LZ7g]
- Java JDK 8+ [https://www.youtube.com/watch?v=O9PWH9SeTTE]
- Eclipse EE (Enterprise Edition) [https://www.youtube.com/watch?v=8aDsEV7txXE]
- Apache Maven [https://www.youtube.com/watch?v=jd2zx3dLjuw]
- Tomcat v8.0+ [https://youtu.be/mLFPodZO8Iw?t=903]
- MySQL Server [https://www.youtube.com/watch?v=Ydh5jYA6Frs]
- MySQL Workbench [https://www.youtube.com/watch?v=t79oCeTXHwg]

## Setup

### Dummy Database Initialization
STEP 1: Open MySQL Command Prompt or MySQL Workbench.

STEP 2: Login to the administrator user of MySql and enter the password if asked:

```sh
$ mysql -u <username> -p
```

STEP 3: Copy paste and execute the MySQL Query from the following file: [databases/mysql_query.sql](./databases/mysql_query.sql).

### Generating Gmail App Password (For Mailing Functionality)
**Step 1**: Create a Gmail account or log in to existing account in any browser.

**Step 2**: Go to [https://myaccount.google.com/security](https://myaccount.google.com/security) and check if 2 step verification is enabled or not.
Enable it if not enabled.

**Step 3**: Go to [https://myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords) and enter password if asked.

**Step 4**: In the "Select an App" section, select "Other (custom name)" => enter "Concordia Cart" => Generate.

**Step 5**: After that, it will generate a 16 digit app password which you need to copy and save for future configuration. Do not share this password!

### Importing and Running The Project Through Eclipse EE
**Step 1**: Open Eclipse Enterprise Edition.

**Step 2**: Click on File > Import > Git > Projects From Git > Clone Uri > Paste The Repository Url as: `https://github.com/shashirajraja/shopping-cart.git` > Select master Branch > Next > Next > Finish.

**Step 3**: In `Java Resources/src/application.properties`, update the values as such:
- a) Update value for `db.username` and `db.password` according to your installed MySQL credentials.
- b) Update value for `mailer.email` and `mailer.password` with the same email and app password that you generated earlier in above section.

> [!WARNING]
> A normal Gmail password will not work. You must use a Gmail app password as discussed earlier.

**Step 4**: Right click on Project > Run as > Maven Build > In the "Goals" field, enter "clean install" > apply > run.

**Step 5**: Right click on Project > Build Path > Configure Build Path > Libraries. Remove or update any libraries if red marks exist. Then click Finish.

**Step 6**: Right click on Project > Maven > update project > select force update > apply > close.

**Step 7**: Tomcat Configurations:
- If Tomcat Server is not configured in Eclipse :
	-  Right click on project > Run As > Run On Server > Manually Define a new server > Select server type > select Tomcat v8.0+ > (Select Tomcat V8.0+ Installation Location If Asked) > Next > Add the current project > Finish.

- If Tomcat Server is already configured in Eclipse:
	- Right click on Project > Run As > Run On Server > Select Tomcat Version > Next > Add the project > Finish.
		<p align='center'>or</p>
	- You can directly goto server tab, select the tomcat server and use the debug or run button to start the previously ran project

**Step 8**: Check Running The Site At [http://localhost:8080/shopping-cart/](http://localhost:8080/shopping-cart/)

**Step 9**: If getting an error like 'port already in use', change the port. Open the Server Tab > double click on Tomcat Server > Ports > change The port number for Http/1.1 to 8083 > Close And Save. Now Start and you can access the project on [http://localhost:8083/shopping-cart/](http://localhost:8083/shopping-cart/)

**Step 10**: Log in. For the admin, the username is "admin@gmail.com" and password is "admin". For the user, the username is "guest@gmail.com" and the password is "guest".

# FAQ
**Q**: Unable to Connect to Database?

**A**: Please check you have installed MySQL correctly and have updated the correct db details in the `application.properties` file.
Also, you can try using Maven to clean install, force updating the project, and restarting.
