# Hogrider User Guide

## Hogrider Task Manager

Hogrider is a **command-line task manager chatbot** that helps users keep track of tasks such as todos, deadlines, and events.

Users interact with Hogrider by typing commands in the terminal. Hogrider automatically saves tasks so that they are available the next time the program starts.

---

## Getting Started

1. Download the `.jar` file from the GitHub release.
2. Place the `.jar` file in an empty folder.
3. Open a terminal in that folder.
4. Run the program: java -jar Hogrider.jar

You should see a greeting message from Hogrider.

---

# Features

## Adding a Todo

Adds a task without any associated date or time.

**Command format**

`todo DESCRIPTION`

Example:

`todo read book`

Expected output: 
```
Got it. I've added this task: 
[T][ ] read book
Now you have 1 tasks in the list.
```

---

## Adding a Deadline

Adds a task that must be completed before a specific time.

**Command format**

`deadline DESCRIPTION /by DATE`

Example:

`deadline return book /by Sunday`

Expected output:
```
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
```

---

## Adding an Event

Adds a task that occurs during a specific time period.

**Command format**

`event DESCRIPTION /from START /to END`

Example:

`event project meeting /from Mon 2pm /to 4pm`

Expected output:
```
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
```

---

## Listing Tasks

Displays all tasks currently stored in the task list.

**Command**

`list`

Example output:
```
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

---

## Marking a Task as Done

Marks a task as completed.

**Command format**

`mark TASK_NUMBER`

Example:

`mark 1`

Expected output:
```
Nice! I've marked this task as done:
[T][X] read book
```

---

## Unmarking a Task

Marks a completed task as not done.

**Command format**

`unmark TASK_NUMBER`

Example:

`unmark 1`

Expected output:
```
OK, I've marked this task as not done yet:
[T][ ] read book
```

---

## Deleting a Task

Removes a task from the task list.

**Command format**

`delete TASK_NUMBER`

Example:

`delete 2`

Expected output:
```
Noted. I've removed this task:
[D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
```

---

## Finding Tasks

Searches for tasks that contain a specific keyword.

**Command format**

`find KEYWORD`

Example:

`find book`

Expected output:
```
Here are the matching tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
```

---

## Exiting the Program

Closes the chatbot.

**Command**

`bye`

Expected output:
```
Bye GOAT!
```

---

# Data Storage

Hogrider automatically saves tasks to the following file:
data/hogrider.txt

Tasks are loaded automatically when the program starts.

---

# Command Summary

| Command | Description |
|--------|-------------|
| `todo DESCRIPTION` | Adds a todo task |
| `deadline DESCRIPTION /by DATE` | Adds a deadline task |
| `event DESCRIPTION /from START /to END` | Adds an event |
| `list` | Shows all tasks |
| `mark NUMBER` | Marks a task as done |
| `unmark NUMBER` | Marks a task as not done |
| `delete NUMBER` | Deletes a task |
| `find KEYWORD` | Searches tasks |
| `bye` | Exits the chatbot |

---











