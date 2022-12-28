// Aislinn O'Connell
// CS 143
// HW Core Topics: HW4: HTML Checker
//
// This program will read in an html file and will go through and check
// that everything is working correctly and will fix any mistakes with 
// html. It goes through the html address step-by-step checking that each 
// piece has its corresponding match or is self closing. It utilizes both 
// queues and stacks to get the job done. Once the html has been fixed, 
// then it will print out the corrected html. 

import java.util.*;

public class HTMLManager {
   private Queue<HTMLTag> tags;
   
   // Takes the html address and passes it into a queue
   public HTMLManager(Queue<HTMLTag> html) {
      if(html.size() < 0) {
         throw new IllegalArgumentException();
      } else {
         tags = html;  
      } 
   }
   
   // Returns  a queue with html values passed in
   public Queue<HTMLTag> getTags() { 
      return tags;
   }
   
   // Fixes any mistakes with the html address 
   public void fixHTML() {
      Stack<HTMLTag> htmlStack = new Stack<>();
      int initialQueueSize = tags.size(); 
      for(int i = 0; i < initialQueueSize; i++) {
         if (tags.peek().isSelfClosing()) {
            tags.add(tags.remove());
         } else if (tags.peek().isOpening()) {
            htmlStack.push(tags.peek());
            tags.add(tags.remove());
         } else if (tags.peek().isClosing()) {
            if (htmlStack.isEmpty()) { 
               tags.remove();           
            } else if(!tags.peek().matches(htmlStack.peek())) {
               tags.add(htmlStack.pop().getMatching()); 
               tags.remove(); 
            } else if (tags.peek().matches(htmlStack.peek())) { 
               htmlStack.pop(); 
               tags.add(tags.remove());             
            }
         }        
      }  
      if(!htmlStack.isEmpty()) {
         while(!htmlStack.isEmpty()) { 
            tags.add(htmlStack.pop().getMatching());
         }     
      }   
   }

   // Reads through queue with fixed html address and prints it to a String
   public String toString() {
      String queueTags = "";
      Queue<HTMLTag> holder = new LinkedList<>();
      holder.addAll(tags);
      while(!holder.isEmpty()) {
         String str = holder.remove().toString();
         queueTags += str.trim();
      }
      return queueTags; 
   } 

}

/* 
Output: 
  ----jGRASP exec: java -ea HTMLChecker
 ===============================
 Processing tests/test1.html...
 ===============================
 HTML: <b><i><br /></b></i>
 Checking HTML for errors...
 HTML after fix: <b><i><br /></i></b>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test2.html...
 ===============================
 HTML: <a><a><a></a>
 Checking HTML for errors...
 HTML after fix: <a><a><a></a></a></a>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test3.html...
 ===============================
 HTML: <br /></p></p>
 Checking HTML for errors...
 HTML after fix: <br />
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test4.html...
 ===============================
 HTML: <div><div><ul><li></li><li></li><li></ul></div>
 Checking HTML for errors...
 HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
 ----> Result matches Expected Output!
 
 ===============================
 Processing tests/test5.html...
 ===============================
 HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
 Checking HTML for errors...
 HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
 ----> Result matches Expected Output!
 
 ===============================
         All tests passed!
 ===============================
   
*/
