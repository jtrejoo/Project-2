/**
   This class implements the algorithm convertToPostfix. It also 
   presents an implementation of the ADT stack using a linked chain.
   @author Fahed Alkhiami
   @author Marie Philavong
   @author Jocelyn Trejo
   @assignment Project 2: Task Calc-o-Stack
   @class CS 2400-02
**/

import java.util.Scanner;

// Stack interface
interface Stack<T>
{
	/**
     * Adds an element to the top of the stack.
     * @param element (the element to be added)
    */
	public void push(T element);
	
	/**
     * Removes the element from the top of the stack.
     * @throws Exception if the stack is empty
    */
	public void pop() throws Exception;

	/**
     * Returns the top element of the stack without removing it.
     * @return top element of the stack
     * @throws Exception if the stack is empty
    */
	public T peek()throws Exception;

	/**
     * Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise
    */
	public boolean isEmpty();

    /**
     * Checks if the stack is full.
     * @return always returns false, as a linked stack is never full
    */
	public boolean isFull();
}

// LinkedStack class implementing the Stack interface
class LinkedStack<T> implements Stack<T>
{
	// inner class representing a node in the linked list
	class LLNode<T>
	{
		// data stored in the node
		T data;
		// reference to the next node
		LLNode<T> link;
		
		// constructor to create node
		public LLNode(T data)
		{
			this.data = data;
			link = null;
		}
		
		// set link to next node
		public void setLink(LLNode<T> link)
		{
			this.link = link;
		}
		
		// get link to next node
		public LLNode<T> getLink()
		{
			return link;
		}
		
		// return data stored in node
		public T getData()
		{
			return data;
		}
	}
	// reference to the top of the stack
	private LLNode<T> top;

	// constructor to create empty stack
	public LinkedStack()
	{
		top = null;
	}

	// adds element to the top of the stack
	public void push(T element)
	{
		// create a new node with the given element
		LLNode<T> newNode = new LLNode<>(element);
		// set the link of new node to current top node
		newNode.setLink(top);
		// update top reference to new node
		top = newNode;
	}

	// throws exception if stack is empty
	// otherwise removes element from top of stack
	public void pop() throws Exception
	{
		if(isEmpty())
		{
			throw new Exception("Pop attempted on an empty stack.");
		}
		else
		{
			// move top reference to next node to remove top element
			top = top.getLink();
		}
	}

	// throws exception if stack is empty
	// otherwise returns top element of stack
	public T peek() throws Exception
	{
		if (isEmpty())
		{
			throw new Exception("Top attempted on an empty stack.");
		}
		else
		{
			// return the data stored in the top node
			return top.getData();
		}
	}

	// check if stack is empty
	public boolean isEmpty()
	{
		return (top == null);
	}

	// check if stack is full
	// always returns false: a linked stack is never full
	public boolean isFull()
	{
		return false;
	}
}

// InfixToPostfix class
class InfixToPostfix
{
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