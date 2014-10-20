Get started with MyFirstWeb
-------------------------------------
This is a starter application for Java with Cloudant NoSQL DB service.

The sample is a Favorites Organizer application, that allows users to organize and manage their favorites and supports different types in each category. This sample demonstrates how to access the Cloudant NoSQL DB service that binds to the application, using Ektorp Java APIs.

1. [Install the cf command-line tool](https://www.stage1.ng.bluemix.net/docs/#starters/BuildingWeb.html#install_cf).
2. [Download the starter application package](https://ace.stage1.ng.bluemix.net:443/rest/../rest/apps/fdf88e83-9711-4fcd-a4e5-f8964076e568/starter-download).
3. Extract the package and 'cd' to it.
4. Connect to Bluemix:

		cf api https://api.stage1.ng.bluemix.net

5. Log into Bluemix:

		cf login -u wangpbj@cn.ibm.com
		cf target -o wangpbj@cn.ibm.com -s dev
		
6. Compile the JAVA code and generate the war package using ant.
7. Deploy your app:

		cf push MyFirstWeb -p JavaCloudantDB.war -m 512M

8. Access your app: [MyFirstWeb.stage1.mybluemix.net](http://MyFirstWeb.stage1.mybluemix.net)
