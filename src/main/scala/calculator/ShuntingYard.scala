package calculator

import scala.collection.mutable.Stack

// Class for creating postfix notion from infix notation
// Uses own variation from Shunting yard algorithm
class ShuntingYard() {
	
  var result = new Stack[String]

  def toPostfix(input: String):Stack[String] =
  {
  	var stack = new Stack[String]
    var isNumber : Boolean = false
    for(char <- input)
    {
      if(char.isDigit)
      {
      	// If this and last token was number, merges the last char and this to new number.
        if(isNumber)
        {
          val temp = result.pop + char
          result.push(temp)
        }
        // Else number is complete and pushes it to result stack.
        else
        {
          result.push(char.toString)
          isNumber = true
        }
      }
      else
      {
        char match {
          case '+' => precedence(1, "+", stack)
          case '-' => precedence(1, "-", stack)
          case '*' => precedence(2, "*", stack)
          case '/' => precedence(2, "/", stack)
          case '(' => stack.push(char.toString)
          case ')' => precedence(3, ")", stack)
          case _ => throw new Exception("Unknown operator: " + char)
        } 
        isNumber = false
      }
    }
    while(!stack.isEmpty) { result.push(stack.pop)}
    // Turns result stack around to right order
    while(!result.isEmpty) {stack.push(result.pop)}
    println(stack)
    return stack
  }

  // Checks given operator's precedence and sorts the post fix and operator stack
  private def precedence(priority: Int, operator:String, stack:Stack[String]): Boolean = {
  	try { 
  	if(!stack.isEmpty)
  	{
	  	while(priority == 1 && !stack.isEmpty && (stack.top == "+" || stack.top == "-" || stack.top == "*" || stack.top == "/"))
	  	{
	  		result.push(stack.pop)
	  	}
	  	while(priority == 2 && !stack.isEmpty && (stack.top == "*" || stack.top == "/"))
	  	{
	  		result.push(stack.pop)
	  	}
	  	while(priority == 3 && !stack.isEmpty && stack.top != "(")
	  	{
	  		result.push(stack.pop)
	  	}
	  	// Removes closing pharentehis from stack
	  	if(priority == 3)
	  	{ 
	  		stack.pop 
	  		return true
	  	}
  	}
  	stack.push(operator)
  	return true
  	} catch {
  	  case e: Exception => {
  	  	throw new Exception("Precedence error: " + e.getMessage())
  		}
  	}
  }


}