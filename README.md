# Smooks UN/EDIFACT Bindings

Here are some slides about the [philosophy behind this project][slideshare].

> __THIS PROJECT IS NO LONGER UNDER ACTIVE DEVELOPMENT__.

> Please contact the [Mailing list](http://www.smooks.org/community) if you wish to adopt the project and continue it's evolution.

## Building

### Pre-requisites

1. JDK 1.5
1. Apache Maven 3 (v3.0.5 recommended)

### Maven

For the moment, if you try to build all modules in the same reactor you
will most likely run into an out-of-memory error (`Permgen space` to be
precise). If you still want to build all modules in a single step, `cd`
to the parent folder and run

    > mvn clean install

If you want to build just a part or you have tried the method above and
failed, `cd` to the parent folder and

    cd ../d96a
    mvn clean install

### build.sh

To build all packages without deploying them to Nexus (Maven Central),
simply run `./build.sh` with no parameters (equivalent to running
`mvn clean install`).  To build a specific set of packages, supply the
package name(s) as a space separated list of parameters e.g.
`./build.sh d00a d00b`.

To build all packages and deploy to the Nexus repository (Maven Central)
([read more here](RELEASE.md)).

- - -

## Release strategy

A new version of this project is published to Maven Central along with
every new version of
[Smooks Core](https://www.github.com/smooks/smooks). This version has
the same version number as the Smooks Core version number, suffixed with
`.0`. For example, when Smooks Core version `1.7.1` was released, a
corresponding version `1.7.1.0` of this project was also released.
Releases made alongside Smooks Core releases contain all EDIFACT
releases available up to that point.

Additional releases made in between Smooks Core releases due to
addition of new EDIFACT releases increment the most minor version
number in sequential order (`n.n.n.1`, `n.n.n.2`, `n.n.n.3`, and so on).

## Adding a new UN/EDIFACT specification

Find your specification on the [UNECE site][unece] then create a
directory named after the specification (you can copy-paste an older
one) where you will download the ZIP file for the desired specification.

- - -

[unece]: http://www.unece.org/tradewelcome/areas-of-work/un-centre-for-trade-facilitation-and-e-business-uncefact/outputs/standards/unedifact/directories/download.html "UNCE"
[slideshare]: http://www.slideshare.net/tfennelly/unedifact-interchange-processing-with-smooks-v14-5104071 "EDI"
