package ee.ut.jf2016.homework7;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult2;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@Description("Describe your test here") // change the description

// the outcome specifies what values are observed in the fields of the IntResult2 after the test is run
//@Outcome(id = "[0, 1]", expect = ACCEPTABLE,  desc = "Change the outcome interpretation")
//@Outcome(id = "[1, 0]", expect = ACCEPTABLE,  desc = "Why and when this output happens")
//@Outcome(id = "[1, 1]", expect = ACCEPTABLE,  desc = "Use FORBIDDEN is the outcome is forbidden under the JMM?")

//Since we don't do anything in the test yet, the only observable result is [0, 0].
// for the real tests, when you fill the @Actor methods, uncomment and change the lines above.
@Outcome(id = "[0, 0]", expect = ACCEPTABLE_INTERESTING,  desc = "Use ACCEPTABLE_INTERESTING to show interesting cases")
@State
public class JFHW7E1 {

  // the fields of the @State object are the shared memory, all @Actors have access to these
  // use them to model the shared memory for the expriment
  int a;
  int b;

  // @Actor marks an actor in the test, actors mutate the state
  // Use these method to describe your experiment
  //
  @Actor
  public void actor1(IntResult2 r) {
    // operate on the state in the object marked as @State
    // there will be a new instance of JFHW7E1 for every iteration of the test
  }

  @Actor
  public void actor2(IntResult2 r) {
    // write the results of the actions into the fields of r parameter.
    r.r1 = 0; // use the state if you need to, you have access to a and b here.
    
    // don't forget to copy the results from the results/.*JFHW7E1.html
    // to the comment in this file and describe in more detail why the results are possible under the JMM
    // which results are interesting and when can they occur, the reloading, lack of those, etc.
    // in which order the actions happened and why it was possible.

  }

//  If you need to observe and transform the state after all the actors have run, use an @Arbiter method

//  @Arbiter
//  public void arbiter(BooleanResult2 r) {
//    r.r1 = true; // observe the state rather than assign a constant
//    r.r2 = a == 0; // observe the state rather than assign a constant
//  }


}

