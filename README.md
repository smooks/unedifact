# Smooks UN/EDIFACT Artifact Sources

Here are some [slideshare slides about the philosophy behind this project][slideshare].

## Building

### Pre-requisites

1. JDK 1.5
1. Apache Maven 3 (v3.0.5 recommended)

### Maven

For the moment, if you try to build all modules in the same reactor you
will get out-of-memory exception (`Permgen space` to be precise). If
you still want to build all modules in a single step, `cd` to the root
folder and run

    > mvn clean install

If you want to build just a part or you have tried the method above and
failed, `cd` to the parent folder and

    mvn clean install
    cd ../d96a
    mvn clean install

### build.sh

To build all packages without deploying them to Nexus (Maven Central),
simply run `./build.sh` with no parameters (equivalent to running
`mvn clean install`).  To build a specific set of packages, supply the
package name(s) as a space separated list of parameters e.g.
`./build.sh d00a d00b`.

To build all packages and deploy to the Nexus repository (Maven Central)
([read more here](https://github.com/smooks/smooks/blob/master/RELEASE.md)),
supply the following parameters:

* __-d__: Deploy to Nexus
* __-u__: [Sonatype JIRA](https://issues.sonatype.org/secure/Dashboard.jspa) username
* __-p__: [Sonatype JIRA](https://issues.sonatype.org/secure/Dashboard.jspa) password
* __-g__: GPG key passphrase. See [Smooks RELEASE.md](https://github.com/smooks/smooks/blob/master/RELEASE.md#generate-a-keypair-for-signing-artifacts).

e.g.:

```
./build.sh -d yes -u tomfennelly -p "xxxxxxxxxxxx" -g "yyyyyyyyyyyyy"
```

### threaded.sh

__Note that this script is very experimental. To have more predictable
behavior, use the `./build.sh` script (see above).__

`./threaded.sh` spins off multiple builds in parallel, greatly reducing
the amount of time taken to build all packages. You need to supply the
script with a number indicating how many parallel builds it can run.
This number should be based on the number of cores the host has (max)
e.g. if the host has 8 cores then something like `./threaded.sh -j 6`
would probably work perfectly well i.e. 6 parallel package builds.

- - -

## Adding a new specification
Find your specification on the [UNECE site][unece] then create a
directory named after the specification (you can copy paste an older
one) where you will download the desired zip.

- - -

## Uploading artifacts to Maven Central

    mvn clean install deploy

- - -

[unece]: http://www.unece.org/tradewelcome/areas-of-work/un-centre-for-trade-facilitation-and-e-business-uncefact/outputs/standards/unedifact/directories/download.html "UNCE"
[slideshare]: http://www.slideshare.net/tfennelly/unedifact-interchange-processing-with-smooks-v14-5104071 "EDI"
