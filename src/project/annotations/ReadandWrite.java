public interface ReadandWrite {
  @ConceptualAPI
  ComputeResult getStatus();
  public static enum ComputeResult {
  @ConceptualAPIPrototype
  SUCCESS,
  FAILURE;
  }
}

