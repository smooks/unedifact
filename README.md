# Smooks UN/EDIFACT Artifact Sources
- - -

## Building

### Maven

For the moment if you try to build all modules in the same reactor you will gel out of memory exception
if you still want to build all in a single step cd to root folder and run 

	> mvn clean install

If you want to build just a part or you tried the method above and failed do these steps:
cd to root folder and :

	cd parent
	mvn clean install
	cd ../d96a
	mvn clean install

- - -

### Gradle

To build all specification run

    > gradle
    
You may also change directories to a specific specification and build from there. You can build from the binding directory
directly and it will build the mapping project automatically. Likewise if the specification directory contains a test directory
you can build that without have to first build the binding/mapping projects.

- - -

## Adding a new specification
Find your specification [on UNECE site][unece] then create a directory named after the specification (you can copy paste
an older one) where you will download the desired zip.

- - -

### Maven 

Read build steps above

- - -

### Gradle
Run 'gradle projects' to see that you newly added project was added:

    > gradle projects
    
    ------------------------------------------------------------
    Root Project - Smooks Unedifact project
    ------------------------------------------------------------

    Root project 'unedifact' - Smooks Unedifact project
    +--- Project ':d00a/binding'
    +--- Project ':d00a/mapping'
    +--- Project ':d00b/binding'
    ...
    
- - -

## Uploading artifacts

### Maven 

	mvn clean install deploy

- - -

### Gradle

To deploy artifacts first create a gradle.properties in the root project directory:

    milynUser=<username>
    milynPassword=<password>

Then run the deploy task:
    > gradle uploadArchives
    
- - -

[unece]: http://www.unece.org/tradewelcome/areas-of-work/un-centre-for-trade-facilitation-and-e-business-uncefact/outputs/standards/unedifact/directories/download.html "UNCE"
