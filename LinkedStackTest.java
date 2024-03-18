/**
   This class implements the algorithm convertToPostfix.
   It also tests the implementation in LinkedStack using 
   the infix expression "a*b/(c-a)+d*e".
   @author Fahed Alkhiami
   @author Marie Philavong
   @author Jocelyn Trejo
   @assignment Project 2: Task Calc-o-Stack
   @class CS 2400-02
**/

class LinkedStackTest
{
	public static void main(String[] args) throws Exception
	{
		String infix = "a*b/(c-a)+d*e";

		// call the convertToPostfix method
		String postfix = convertToPostfix(infix);
		System.out.println ("Postfix expression: " + postfix);
	}

	/**
     * Method to get the priority of an operator.
     * @param c (the operator)
     * @return the priority of the operator
    */
	private static int getPriority(char c)
	{
		switch(c)
		{
			// lowest priority
			case '(':
				return 0;
			// lower priority for addition and subtraction
			case '+':
			case '-':
				return 1;
			// higher priority for division and multiplication				
			case '/':
			case '*':
				return 2;
			default:
				return 999;
		}
	}

	/**
     * Method to convert infix expression to postfix expression.
     * @param infix (the infix expression)
     * @return postfix expression
     * @throws Exception if an error occurs during conversion
    */
	public static String convertToPostfix(String infix) throws Exception
	{
		infix = infix + ")";

		// create a stack to hold operators
		LinkedStack<Character> stk = new LinkedStack<Character>();

		// push '(' onto the stack to mark the beginning of the expression
		stk.push('(');

		String postfix = "";

		// convert expression from infix to postfix
		for(int i = 0; i < infix.length(); i++)
		{
			char ch = infix.charAt(i);

			// check for operands
			if(Character.isLetter(ch))
			{
				postfix = postfix + ch + " ";
			}
			// check for left parenthesis
			else if(ch=='(')
			{
				stk.push(ch);
			}
			// check for right parenthesis
			else if(ch==')')
			{
	            // pop operators from the stack and append to postfix until '(' is encountered
				while(stk.peek()!='(')
				{
					postfix = postfix + stk.peek() + " ";
					stk.pop();
				}
				// pop '(' from the stack
				stk.pop();
			}
			else
			{
	            // get priority of current operator and operator at the top of the stack
				int operator1 = getPriority(ch);
				int operator2 = getPriority(stk.peek());
				
	            // pop operators from the stack and append to postfix if operator1 <= operator2
				while(operator1 <= operator2)
				{
					postfix = postfix + stk.peek() + " ";
					stk.pop();

					// update operator2 as priority of the new top of the stack
					operator2 = getPriority(stk.peek());
				}
	            // push current operator onto the stack
				stk.push(ch);
			}
		}

		// print error message if stack is not empty
		if(!stk.isEmpty())
		{
			System.out.println("Invalid Expression!");
		}
		
		// return postfix expression
		return postfix;
	}
}