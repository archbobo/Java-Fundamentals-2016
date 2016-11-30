package com.zeroturnaround.concurrency.data;

import java.util.Arrays;
import java.util.List;

/**
 * Interface that stores the original passwords.
 *
 * This data is supposed to be totally unknown.
 * The only reason this is here, is to allow you to validate my own hashes and results.
 * Please do not in any way use the info in this class in your own solution.
 */
public interface Password {

  List<String> passwords = Arrays.asList(
      "java",
      "Bram",
      "java",
      "ZeroTurnaround",
      "Test",
      "hunter2", // Read bash.org, but only after finishing your homework
      "password",
      "GOD", // Watch "Hackers" from 1995 if you do not get the reference
      "santa",
      "FireflyIsTheBestSeriesEverAndIWillFightYouOverYourOwnDifferingOpinions"
  );

}
