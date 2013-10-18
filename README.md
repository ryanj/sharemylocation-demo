ShareMyLocation
==========

Simple location aware app written using Java EE 6 and MongoDB

1. Sign up for an [OpenShift Account](https://openshift.redhat.com/app/account/new). It is free and instant. Red Hat gives every user three free Gears on which to run your applications. At the time of this writing, the combined resources allocated for each user is 1.5 GB of memory and 3 GB of disk space.

2. Install OpenShift Client Tooos https://www.openshift.com/developers/rhc-client-tools-install

3. Create an application
```
$ rhc create-app sharemylocation jbosseap mongodb-2
```

4. Pull the source code from github
```
$ git remote add upstream -m master https://github.com/shekhargulati/sharemylocation.git
$ git pull -s recursive -X theirs upstream master
```

5. Push the changes to OpenShift application gear
```
$ git push
```
