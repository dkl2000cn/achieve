package com.easygoal.achieve;

public class InputValueSet {

		 private int No;
	        private String Value;
	       
	        public InputValueSet(int No, String value) {
	            this.No = No;
	            this.Value = value;
	           
	        }

	        public int getNo() {
	            return No;
	        }

	        public String getValue() {
	            return Value + "";
	        }

	        @Override
	        public String toString() {
	            return " " + No + " "+ Value;
	        }
}
