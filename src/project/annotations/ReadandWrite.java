@ConceptualAPI
public interface ReadandWrite {
  ComputeResult getStatus();
  public static enum ComputeResult {
  @ConceptualAPIPrototype
  SUCCESS,
  FAILURE;
  }
}

