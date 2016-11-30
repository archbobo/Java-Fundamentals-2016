package com.zeroturnaround.concurrency.data;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zeroturnaround.concurrency.util.BramHash;

/**
 * Interface that stores the calculated hashes.
 *
 * For this exercise, these hashes are (obviously) a public given.
 * They were calculated using BramHash, with the input the concatenation of salt+password.
 * To avoid any possible confusion, if my salt is "hello" and password is "world",
 * then the input of the hash-function would be "helloworld".
 * To further avoid any discussions, the code I used to generate the hashes is also included below.
 */
public interface Hash {

  List<String> hashes = Arrays.asList(
      "7bb9",
      "d459",
      "860a",
      "d77c",
      "d38e",
      "d941",
      "b08c",
      "ac15",
      "770b",
      "13fb"
  );


  static void calculateHashes() {
    Logger log = LoggerFactory.getLogger(Hash.class);
    for (int i = 0; i < Password.passwords.size(); i++) {
      String salt = Salt.salts.get(i);
      String password = Password.passwords.get(i);
      String hash = BramHash.hash(salt + password);

      log.info("Salt = \"{}\", Password = \"{}\", BramHash = \"{}\"", salt, password, hash);
    }
  }
}
