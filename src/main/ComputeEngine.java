@ConceptualAPI
public interface ComputeEngine { 
  String compute(int value);                              
} 
public class ComputeEnginePrototype { 
  @ConceptualAPIPrototype
  public void prototype(ComputeEngine engine) { 
    String result = engine.compute(1); 
  } 
} 
