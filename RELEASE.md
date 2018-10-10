# Smooks UN/EDIFACT Bindings

This document describes the process to be followed by Smooks committers
for publishing Maven artifacts generated from this repository to
[Maven Central](https://search.maven.org/).

## I. Overview

Maven artifacts are published to Maven Central using the Sonatype
[open-source Nexus repository](https://oss.sonatype.org/). The process
of publishing artifacts involves the following activities.

### *One-time activities*

* Get committer access to this Github repository.
* Get access to the Nexus repository.
* Get access to publish to Maven Central.
* Generate a PGP key to sign Maven artifacts to be uploaded.

### *Per-release activities*

* Prepare the artifacts for publication.
* Upload the artifacts to the Nexus repository for staging.
* Review the staged artifacts using the Nexus web interface.
* Promote the staged artifacts to Maven Central.
* Tag the repository.
* Prepare for the next development iteration.

## II. Get committer access to this Github repository

If you wish to be a long-term committer on this repository, raise a
Github issue within the issue tracker for this repository, tagging it
with the label `other` and requesting committer access on the
repository.

Make your Github profile public (if not already) so that your name
appears on the Smooks Team page on Github. This will be required later.

## III. Get access to the Sonatype open-source Nexus repository

Follow the steps described on the Sonatype Nexus repository
[help page](https://central.sonatype.org/pages/ossrh-guide.html) to
create a user account on the [Sonatype JIRA](issues.sonatype.org)
issue-tracking system. The Sonatype Nexus repository uses the same
username and password as the Sonatype JIRA, so make sure to keep the
username and password registered on the Sonatype JIRA handy.

Once the registration steps have been completed, make sure to login to
JIRA and the Nexus repository once to ensure that the username and
password are working correctly.

Add the Sonatype JIRA credentials (username and password) to the local
Maven configuration (found in `$MAVEN_HOME/conf/settings.xml`, where
`$MAVEN_HOME` refers to the base directory to which Maven has been
installed).

    <settings>
      ...
      <servers>
        ...
        <server>
          <id>ossrh</id>
          <username>your-jira-id</username>
          <password>your-jira-password</password>
        </server>
      </servers>
      ...
    </settings>

**Note**: Make sure to give the identifier `ossrh` to the server
setting as the project configuration uses this identifier.

## IV. Get access to publish to Maven Central

Raise a ticket in the Sonatype JIRA, requesting them to grant permission
to your JIRA username to publish to the `org.milyn` Maven group ID. This
is the parent Maven group ID under which Smooks UN/EDIFACT bindings are
published, so having access to this group ID is mandatory to publish
Maven artifacts from this repository.

When raising a ticket, include a link to the Smooks Team page on Github
that shows your user profile as one of the committers. Without this
information, the Sonatype team will be unable to grant access as they
need verification that users should be allowed to publish to the
requested group ID.

## V. Generate a PGP key to sign Maven artifacts to be uploaded

Make sure that you have a PGP client installed locally and that it is
available on the normal execution path. On Windows machines, installing
Git Bash should suffice as it includes a PGP client. If unsure, try
running `pgp` or `gpg` from a command promot to see if you have a PGP
client installed.

Generate a new PGP digital signature key using the command given below,
choosing RSA+RSA as the key type, 2048 bits as the key length and
providing a strong passphrase when asked for.

    gpg --gen-key

This will generate a public-private RSA key-pair. The generated keys can
be verified by issuing the command given below (includes a sample
output).

    gpg --list-keys
    ----------------------------------------
    pub   2048R/DEA94886 2011-01-01
    uid                  Firstname Lastname <email@domain.com>
    sub   2048R/AB98C664 2011-01-01

The public part of the key (called `DEA94886` in the sample above) has
the prefix `pub`, while the other one (called `AB98C664` above) is the
private part of that key.

The public key needs to be made available publicly so that others, most
notably Nexus, can verify artifacts signed with the key. Issue the
command given below to publish the public key.

    gpg --keyserver hkp://pool.sks-keyservers.net --send-keys [PGP key name]

making sure to substitute `[PGP key name]` with the name of the public
key (`DEA94886` above).

**Note**: Do make sure to remember the passphrase provided as it will
be required every time artifacts have to be released to Maven Central.

## VI. Prepare the artifacts for publication

1. Make sure that all bindings can be generated correctly.
`mvn clean package` is recommended on machines with 8GB+ of RAM since
building all the bindings requires a large amount of memory. If this is
not possible, build a few bindings at a time, until all bindings are
verified to be generating correctly.
1. If the repository artifacts currently have a version ending in
`-SNAPSHOT`, the artifact versions have to be upgraded to release
versions (no `-SNAPSHOT`). This can be achieved by issuing the command
`mvn versions:set -DnewVersion=[new version number]`. For example, if
the current artifact version is `1.4-SNAPSHOT`, issuing the command
`mvn versions:set -DnewVersion=1.4` will upgrade the version for all
artifacts to `1.4`.
1. If the Maven artifact versions were upgraded, issue the command
`mvn validate` to ensure that all the Maven artifacts are still valid.
1. If the versions were upgraded and all artifacts were validated
successfully, issue `mvn versions:commit` to commit the change of
versions. If something did not work, issue `mvn versions:revert` to
revert the change.
1. Commit the changes to the repository using a Git client.

## VII. Upload the artifacts to the Nexus repository for staging

Once the artifacts have been assigned a release version (not ending in
`-SNAPSHOT`), they are ready to be uploaded to the Nexus repository
from where they will then be published to Maven Central.

The Sonatype Nexus repository requires all Maven artifacts to be
published to Maven Central to meet the following quality standards:

1. All Java artifacts (`.jar`, `.war`, `.sar`, `.ear`, etc.) must be
accompanied with Javadocs.
1. All Java artifacts must be accompanied with their source code.
1. All artifacts (including `.pom` files) must be signed.

To ensure that these requirements are always met when uploading
artifacts to the Nexus repository, a dedicated Maven profile named
`release` is available for the project. This profile contains necessary
Maven plugins to ensure that the Nexus repository requirements are
met upon upload.

Issue the command given below to start the upload process.

    mvn deploy -Prelease -Dgpg.keyname="[PGP key name]" -Dgpg.passphrase="[PGP passphrase]"

making sure to substitute `[PGP key name]` with the name of the PGP
private key (**not the public key**) generated before (`AB98C664` above),
and `[PGP passphrase]` with the passphrase provided at the time of
generating the key.

The upload progress creates a temporary Maven repository, known as a
staging repository, on the Nexus server. After the upload has completed
successfully, the name of the staging repository will be printed on the
command line. This is usually something like `orgmilyn-[number]`, where
`[number]` is a sequential number. Note the repository name as it will
be required for the next activity.

**Note**: Given the number of EDIFACT specifications and the large size
of the bindings for each specification, the upload may take a very long
time, especially on slow networks.

## VIII. Review the staged artifacts using the Nexus web interface

1. Login to the Nexus repository.
1. Find and click the link titled `Staging Repositories` (usually on
the left hand side of the page). This will show a list of staging
repositories (usually on the right hand side of the page).
1. Find and click the staging repository with the name provided at the
end of the upload process earlier. This will select the repository and
show its details. The repository will show up in `Open` state.
1. Review the details and ensure that all bindings got uploaded
successfully.
1. Once ready, find and click the button titled `Close` at the top of
the list of staging repositories. This will start the Nexus verification
process. Since the build process takes care of meeting the verification
requirements, hopefully this step will complete without any errors.
Given the number of artifacts involved, this step may take a
considerable amount of time. The operation progress can be monitored
along side the repository details. All actions for the staging
repository will be unavailable while the verification process completes.
1. If the verification process fails, review the errors shown. These
will need to be fixed before the artifacts can be promoted.

## IX. Promote the staged artifacts to Maven Central

Once the verification process completes, the `Release` button will
become available at the top of the list of staging repositories. Click
this button to start the process of promoting the artifacts to Maven
Central.

Again, given the number of artifacts involved, the promotion process
may take a considerable amount of time. Keep monitoring the process
progress from the web interface.

## X. Tag the repository

Tag the Github repository.

    git tag -s vx.x.x -m "Tagging vx.x.x published to Maven Central."

Push the tag.

    git push upstream vx.x.x

## XI. Prepare for the next development iteration.

Update the versions in the project POM files for the next release.

    mvn versions:set -DnewVersion=[new version number]
