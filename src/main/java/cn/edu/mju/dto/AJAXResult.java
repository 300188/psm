package cn.edu.mju.dto;

/**
 * AJAX请求返回对象
 */
public class AJAXResult {

	private boolean success;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public AJAXResult(boolean success) {
		super();
		this.success = success;
	}

	public AJAXResult() {
		super();
	}

	public AJAXResult(Object data,boolean success){
		this.success =success;
		this.data = data;
	}
	
	
}
