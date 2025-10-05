/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.components;


/**
 * Exception class for the framework
 * @author Qualitest
 */
@SuppressWarnings("serial")
public class FrameworkException extends RuntimeException {
	/**
	 * The step name to be specified for the exception
	 */
	public String errorName = "Error";
	
	
	/**
	 * Constructor to initialize the exception from the framework
	 * @param errorDescription The Exception message to be thrown
	 */
	public FrameworkException(String errorDescription) {
		super(errorDescription);
	}
	
	/**
	 * Constructor to initialize the exception from the framework
	 * @param errorName The step name for the error
	 * @param errorDescription The Exception message to be thrown
	 */
	public FrameworkException(String errorName, String errorDescription) {
		super(errorDescription);
		this.errorName = errorName;
	}
	
	/**
	 * Function to  get the step name for the error to be specified for the exception
	 * 
	 * @return The step name for the error
	 */
	public String getErrorName() {
		return errorName;
	}
}