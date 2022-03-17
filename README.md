# Lambdatest Jenkins Plugin
[![Build Status](https://ml-poc-jenkins.lambdatest.io/buildStatus/icon?job=shiv_POC)](https://ml-poc-jenkins.lambdatest.io/job/shiv_POC/)

#### This Plugin is used to run automated selenium tests on LambdaTest Cloud.

### Base Configuration
https://github.com/jenkinsci/gradle-jpi-plugin

### Installation

##### via the interface
1. Go to your installation's management screen.
2. Click **Manage Plugins**.
3. Click the **Available** tab.
4. Find the **LambdaTest Automation Plugin** and select the checkbox.
5. then click either **Install without restart** or **Download now and install after restart**.

### Building the plugin for testing/development

<pre>gradle clean</pre>

###### This command will run the gradle Task :build
<pre>gradle build</pre>
or
<pre>./gradlew build</pre>

###### This command will run the gradle Task :server
<pre>gradle server</pre>

###### Build the Jenkins plugin file (will currently end in ".hpi".)
```bash
$ gradle jpi
```

###### This command will run the gradle JVM with specified httpPort.
<pre>gradle server -Djenkins.httpPort=8082</pre>

## Release Instructions

For new `releases`, bump `version` field in `build.gradle`, then execute the following command

```bash
$ gradle release -Prelease.useAutomaticVersion=true
```

**Note : Required Java 8 version** 
```bash
$ export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
```

**Required Gradle version 4.7**
```bash
$ ./gradlew wrapper --gradle-version 4.7
```
## Manual install

1. Open Jenkins in a browser; e.g. http://jenkins_host:8080
2. Browse to 'Manage Jenkins' --> 'Manage Plugins’ and select the **Advanced** tab.
3. Click the **Browse** button in the 'Upload Plugin' section. 
4. Find the location of the 'lambdatest-automation.hpi' file and click the **Open** button. 
5. Click the **Download now and install after restart** button (this might be different on the newer version). 

## License

This program is free software and is distributed under an [MPL-2.0 License](LICENSE).
