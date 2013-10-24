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
Find your specification [on UNECE site][unece] then create a directory named after the specification (you can copy paste
an older one) where you will download the desired zip.


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

[unece]: http://www.unece.org/tradewelcome/areas-of-work/un-centre-for-trade-facilitation-and-e-business-uncefact/outputs/standards/unedifact/directories/download.html "UNCE"