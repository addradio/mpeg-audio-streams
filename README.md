# mpeg-audio-streams
[![Build Status](https://travis-ci.org/addradio/mpeg-audio-streams.svg?branch=master)](https://travis-ci.org/addradio/mpeg-audio-streams)
[![Coverage Status](https://coveralls.io/repos/github/addradio/mpeg-audio-streams/badge.svg?branch=master)](https://coveralls.io/github/addradio/mpeg-audio-streams?branch=master)

Decodes MPEG audio byte streams such as mp3 into Java code readable frames. Encodes these frames back to byte streams also. Provides InputStream and OutputStream interfaces. Rudimentary ID3v1, ID3v2 tag support is implemented as well.

## Maven Integration

In order to use `mpeg-audio-stream` in your project, you need to add the following `repository` to the `repositories` section of your project's pom:
```xml
    ...
    <repositories>
        ...
        <repository>
            <id>addradio-public-mvn-repo</id>
            <name>AddRadio Public Maven Repository</name>
            <url>http://mvn-repo.dev.addradio.net/mvn-repo/releases</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
        ...
    </repositories>
    ...
```

Then you also need to add the following dependency:
```xml
        <dependency>
            <groupId>net.addradio.codec</groupId>
            <artifactId>mpeg-audio-streams</artifactId>
            <version>1.0.1-SNAPSHOT</version>
        </dependency>
```

## Copyright
Copyright (c) 2017-2021 AddRadio - a division of nacamar GmbH, Germany. See [GNU Affero General Public License v3.0](LICENSE) for details.
