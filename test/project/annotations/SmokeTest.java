import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
public class SmokeTest{
  @Test
  public void testNetwork(){
     ComputationHandler x=Mockito.mock(x.class);
    InputConfig input=5;
    OutputConfig output="5,25,29,85,89,145,42, 20,4,16,37,58,89";
    ComputeEngineRequest request=new ComputeEnginRequest(input, output);
    x.compute(request);
    Assert.assertionEquals(x.compute.getstatus(SUCCESS),x.compute(request));
    
  }
  public void testProcess(){
    DataStore x=Mockito.mock(x.class);
    InputConfig input=5;
    OutputConfig output="5,25,29,85,89,145,42, 20,4,16,37,58,89";
    x.read(input);
    x.appendResult(output, input);
    Assert.assertionEquals(x.appendResult.getStatus(SUCCESS),  x.appendResult(output, input););
    
  }
  public void testConceptual(){
    ComputeEngine x=Mockito.mock(x.class);
    int y=5;
    x.compute(y);
    String s="five"
    Assert.assertionEquals(s, x.compute(y));
   
  }
}
