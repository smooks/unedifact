# Smooks UN/EDIFACT Artifact Sources
- - -

## Building
To build all specification run

    > gradle
    
You may also change directories to a specific specification and build from there. You can build from the binding directory
directly and it will build the mapping project automatically. Likewise if the specification directory contains a test directory
you can build that without have to first build the binding/mapping projects.

- - -

## Adding a new specification
To do this need to create a directory named after the specification. Then create the mapping directory and put 
the [specification](http://www.unece.org/trade/untdid/down_index.htm) zip file in the root of that directory. 


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
To deploy artifacts first create a gradle.properties in the root project directory:

    milynUser=<username>
    milynPassword=<password>

Then run the deploy task:
    > gradle uploadArchives
    
- - -
    