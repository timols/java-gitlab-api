# How to contribute


* The integration tests need a running Docker infrastructure.
* To execute these, run `mvn -Pdocker-gitlab clean verify`. This will spawn
  a new gitlab instance (`pull` needs some time and starting `gitlab-ce` may take up to 3 minutes).
* To run integration tests in your IDE, you need to execute `mvn -Pdocker-gitlab,docker-ide docker:start`.
* Once you are done, you may stop gitlab by executing `mvn -Pdocker-gitlab,docker-ide docker:stop`.
* For more information about the API, take a look at the [documentation](https://gitlab.com/help/api/README.md).