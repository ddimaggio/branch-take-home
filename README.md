# Read Me

This is David DiMaggio's submission for Branch's take-home assessment.

It was completed using Java/Spring Boot with Lombok as the only additional dependency.

# Getting Started

* First, pull this repo and `mvn clean install` in the repo to pull the necessary dependencies
* Open the repo from IntelliJ IDEA
* To run the repo, you can either start the BranchTakeHomeApplication from the dropdown/play button in the top right, or by going to that class and running there.
* Once started, you can open a browser, and hit `http://localhost:8080/user/{username}` and view the data requested in JSON format.
  * The Github username requirements are enforced, not following them will result in a 400.
    * The username must be less than 40 characters and may only contain alphanumerical characters or hyphens.
    * Hyphens may not be at the beginning or end of the username, and may not be used consecutively.
  * If you are seeing a 404, the username you tried to lookup does not exist.

# Design Choices

I decided to treat this project as I would a story on my current team. I treated the GitHubService as if it were another team's service with DTOs I had to consume and map to my own DTOs in order to merge the two calls then return the data in the format provided by the requirements. For the sake of simplicity and saving time, I did not set up the GitHubUser/GitHubRepo DTOs in their entirety, just with the data I would be using.

I chose to enforce GitHub's username requirements and return a 400 if the username wasn't in the correct format before even attempting to move beyond the controller. The only exception I was able to reproduce was for non-existent users, so I made the choice to catch that exception and return 404 to the user to avoid the default exception page and provide users with more relevant info than just the generic 500 page. This is done before the call to retrieve the list of repos, as we know that will fail if a user doesn't exist, so the call to retrieve the repos does not return a ResponseEntity, just the list of repos.




# Tests

I've provided some unit test cases for the controllers and services with 100% line coverage in the classes I worked in. I used Mockito for a couple of the classes in order to avoid GitHub locking me out for too many runs, however the Controller's tests go through to the GitHub APIs and use real data in an end-to-end scenario. The mapper is not tested directly, as the `map()` method is covered by other tests.

These can be run from IntelliJ IDEA by right-clicking the java folder under the test folder and selecting Run All Tests.