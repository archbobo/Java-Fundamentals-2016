package com.zeroturnaround.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import com.zeroturnaround.concurrency.data.Hash;
import com.zeroturnaround.concurrency.data.Salt;
import com.zeroturnaround.concurrency.util.BramHash;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Test to validate your solution.
 * Feel free to add your own tests, testing your added functionality / code, if you so desire.
 */


public class CollisionFinderTest {
    /**
     * Verifies that your 10 collision-inducing passwords indeed generate the same salted hashes as the originals.
     * Make sure the order they appear in matches the ones in "Hash" and "Salt".
     */
    @Test
    public void testCollisionSearch() throws InterruptedException {
        List<String> crackedPasswords = new CollisionFinder().findCollidingPasswords();
        for (int i = 0; i < Hash.hashes.size(); i++) {
            String crackedPasswordHash = BramHash.hash(Salt.salts.get(i) + crackedPasswords.get(i));
            String originalHash = Hash.hashes.get(i);

            assertEquals("Hashes do not match, no collision found! ", originalHash, crackedPasswordHash);
        }
    }

    /**
     * Print the hashes using the original passwords.
     */
/*  @Test @Ignore
  public void logHashes() {
    Hash.calculateHashes();
  }*/
}
