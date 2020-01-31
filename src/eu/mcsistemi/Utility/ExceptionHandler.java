package eu.mcsistemi.Utility;

//public class ExceptionHandler {
//	private static ExceptionHandler exceptionHandler = new ExceptionHandler();
//	private ExceptionHandler(){}
//	public static ExceptionHandler getInstance(){
//		return exceptionHandler;
//	}
//	public int getErrorCode(String e){
//		if(e.contains("duplicate key value violates unique constraint"))
//			return 1;
//		else if(e.contains("Null pointer exception"))
//			return 2;
//		return -1;
//	}
public class ExceptionHandler{
	private static ExceptionHandler exceptionHandler = new ExceptionHandler();
	private ExceptionHandler(){}
	public static ExceptionHandler getInstance(){
		return exceptionHandler;
	}
	public Errors getErrorCode(String e){
		if(e.contains("duplicate key value violates unique constraint"))
			return Errors.duplicateKey;
		else if(e.contains("Null pointer exception"))
			return Errors.NullPointerExc;
		return Errors.unknownError;
	}
	public enum Errors{
		duplicateKey{
			public String printCode(){
				return "1";
			}
			public String toString(){
				return "duplicate key exception";
			}
		}
		,NullPointerExc{
			public String printCode(){
				return "2";
			}
			public String toString(){
				return "null pointer exception";
			}
		}
		,wrongUserOrPassword{
			public String printCode(){
				return "3";
			}
			public String toString(){
				return "wrong user or password exception";
			}
		}
		,unknownError{
			public String printCode(){
				return "4";
			}
			public String toString(){
				return "unknown exception";
			}
		};
		public abstract String printCode();
		
		
	}
}