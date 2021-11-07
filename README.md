# Assignment for Avery Dennison done by Stefan Popovic
The project was done using Java (Spring Boot) for the back end and Angular (latest version at this time) for the front end. Due to the limited time, I decided not to use .NET and use something I'm more familiar with instead.

Some things are also incomplete due to lack of time, especially the front end part, but the main funcionality that was required is there.

# Running the app
The app is split into 2 directories, BE and FE. The back end can be started from an IDE (such as IntelliJ IDEA), and the front end can be started using `ng serve` after `npm install` (you need to have npm installed and also use it to install @angular/cli globally).
The back end part uses an in memory database by default (H2), but also supports MySQL and SQLServer - the `application.properties` file already contains the required configuration properties for these databases, they just need to be uncommented and the proper credentials need to be added instead of the default ones.

For easier runnin without polluting the system, I've included a Dockerfile for both the front end and the back end. It can be used in the following way:

* Back end
    * run `docker build -t stefan-popovic-be` in the ad-assignment-be directory (might take a while)
    * run `docker run --rm -p 8080:8080 stefan-popovic-be:latest`
* Front end
    * run `docker build -t stefan-popovic-fe` in the ad-assignment-fe directory (might take a while)
    * run `docker run --rm -p 4200:4200 stefan-popovic-fe:latest`
    * when trying to close it, Control + C might not work right away, so `docker ps` and `docker kill <id>` might be needed
    
# Missing features/Improvements needed
As mentioned, some things had to be cut from the app. Here's a rough list of improvements that would be implementet if time allowed it.

## Back end
* Better logging and error handling
* More test coverage (currently only some crutial parts are covered)
* Integration tests that test the whole application

## Front end
* Better validation (currently done only in some places)
* Better error handling (currently done only in some places)
* Better UI (the current one is quite basic)
* Test coverage
* Types
* Responsive design
