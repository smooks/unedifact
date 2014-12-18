# Smooks UN/EDIFACT Artifact Sources

Here are some [slideshare slides about this project philosiphy][slideshare]

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

### build.sh

To build all packages without deploying them to Nexus, simply run `./build.sh` with no parameters (equivalent to running `mvn clean install`).  To build a specific set of packages, supply the package names as a space separated list of parameters e.g. `./build.sh d00a d00b`.

To build all packages and deploy to the Codehaus Nexus repository ([read more here](https://github.com/smooks/smooks/blob/master/RELEASE.md)), supply the following parameters:

* __-d__: Deploy to Nexus.
* __-u__: Codehaus username (same as Xircles).
* __-p__: Codehaus password (same as Xircles).
* __-g__: GPG key passphrase. See [Smooks RELEASE.md](https://github.com/smooks/smooks/blob/master/RELEASE.md#generate-a-keypair-for-signing-artifacts).

e.g.:

```
./build.sh -d yes -u tomfennelly -p "xxxxxxxxxxxx" -g "yyyyyyyyyyyyy"
```

### threaded.sh

__Note that this script is very experimental. To have more predictable behavior, use the `./build.sh` script (see above).__

`./threaded.sh` spins off multiple builds in parallel, greatly reducing the amount of time to build all packages.  You need to supply the script with a number indicating how many parallel builds it can run.  This number should be based on the number of cores the host has (max) e.g. if the host has 8 cores then something like `./threaded.sh -j 6` would probably work perfectly well i.e. 6 parallel package builds.

### Docker

You can also build from the [docker](https://www.docker.io) image:

1. [Install docker](https://www.docker.io/gettingstarted/).
2. Run `sudo docker build -t smooks-unedifact github.com/smooks/unedifact`.  This will create a docker image named "smooks-unedifact" that contains the correct build environment and a cloned copy of this git repo.
3. Run `sudo docker run -i smooks-unedifact <command>` to execute the desired build (see above).

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
[slideshare]: http://www.slideshare.net/tfennelly/unedifact-interchange-processing-with-smooks-v14-5104071 "EDI"
