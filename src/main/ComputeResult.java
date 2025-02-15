public interface ComputeResult { 
  static ComputeResult SUCCESS = () -> ComputeResultStatus.SUCCESS; 
  static ComputeResult FAILURE = () -> ComputeResultStatus.FAILURE; 
  ComputeResultStatus getStatus();
}
